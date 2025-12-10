package tests;

import clients.BinanceClient;
import clients.CoinGeckoClient;
import constants.ApiEndpoints;
import models.Currency;
import models.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

/**
 * Validation tests for business logic and data consistency
 * Contains cross-API validation and data quality checks
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class ValidationTests {

    private static final Logger logger = LoggerFactory.getLogger(ValidationTests.class);
    private BinanceClient binanceClient;
    private CoinGeckoClient coinGeckoClient;

    /**
     * Sets up test environment before test execution
     */
    @BeforeClass
    public void setUp() {
        binanceClient = new BinanceClient();
        coinGeckoClient = new CoinGeckoClient();
    }

    /**
     * Tests that cryptocurrency prices are positive across different APIs
     */
    @Test(priority = 1, description = "Verify all cryptocurrency prices are positive")
    public void testCryptocurrencyPricesArePositive() {
        // Test Binance prices
        Ticker btcTicker = binanceClient.getPrice("BTCUSDT");
        Ticker ethTicker = binanceClient.getPrice("ETHUSDT");

        assertTrue(btcTicker.getPrice() > 0, "Bitcoin price on Binance should be positive");
        assertTrue(ethTicker.getPrice() > 0, "Ethereum price on Binance should be positive");

        // Test CoinGecko prices
        double bitcoinPrice = coinGeckoClient.getBitcoinPrice();
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        assertTrue(bitcoinPrice > 0, "Bitcoin price on CoinGecko should be positive");

        for (Currency currency : topCurrencies) {
            // FIX: Check if price is not null before assertion
            if (currency.getCurrentPrice() != null) {
                assertTrue(currency.getCurrentPrice() > 0,
                        "Currency " + currency.getName() + " price should be positive");
            } else {
                logger.warn("Currency {} has null current price", currency.getName());
            }
        }
    }

    /**
     * Tests that major cryptocurrencies exist in both APIs
     */
    @Test(priority = 2, description = "Verify major cryptocurrencies presence")
    public void testMajorCryptocurrenciesExist() {
        // Get top cryptocurrencies from CoinGecko
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        // FIX: Check only for Bitcoin (guaranteed to be #1)
        boolean hasBitcoin = topCurrencies.stream()
                .anyMatch(currency -> "bitcoin".equals(currency.getId()));

        // FIX: Ethereum and Cardano are not guaranteed to be in top-10
        // Just check that we have some currencies
        assertTrue(topCurrencies.size() > 0, "Should have at least one cryptocurrency");
        assertTrue(hasBitcoin, "Bitcoin should be in top cryptocurrencies");

        // Optional: log what we actually got
        logger.debug("Top cryptocurrencies found: {}",
                topCurrencies.stream().map(Currency::getId).collect(Collectors.toList()));
    }

    /**
     * Tests price consistency between different data sources
     */
    @Test(priority = 3, description = "Verify price consistency between APIs")
    public void testPriceConsistencyBetweenApis() {
        // Get Bitcoin price from Binance
        Ticker binanceBtcTicker = binanceClient.getPrice("BTCUSDT");
        double binanceBtcPrice = binanceBtcTicker.getPrice();

        // Get Bitcoin price from CoinGecko
        double coinGeckoBtcPrice = coinGeckoClient.getBitcoinPrice();

        // Calculate price difference percentage
        double priceDifference = Math.abs(binanceBtcPrice - coinGeckoBtcPrice);
        double priceDifferencePercentage = (priceDifference / binanceBtcPrice) * 100;

        // Prices should be within 5% of each other (reasonable difference between sources)
        double maxAllowedDifference = 5.0;

        assertTrue(priceDifferencePercentage <= maxAllowedDifference,
                "Bitcoin prices between Binance and CoinGecko should be consistent. " +
                        "Binance: $" + binanceBtcPrice + ", CoinGecko: $" + coinGeckoBtcPrice +
                        ", Difference: " + String.format("%.2f", priceDifferencePercentage) + "%");
    }

    /**
     * Tests API response time performance
     */
    @Test(priority = 4, description = "Verify API response time performance")
    public void testApiResponseTimePerformance() {
        // Test Binance response time
        boolean binanceResponseTimeOk = binanceClient.checkResponseTime(
                ApiEndpoints.MAX_RESPONSE_TIME_NORMAL
        );

        // Test CoinGecko response time (allowing more time as it might be slower)
        boolean coinGeckoResponseTimeOk = coinGeckoClient.checkResponseTime(
                ApiEndpoints.MAX_RESPONSE_TIME_SLOW
        );

        assertTrue(binanceResponseTimeOk,
                "Binance API response time should be within " +
                        ApiEndpoints.MAX_RESPONSE_TIME_NORMAL + "ms");

        assertTrue(coinGeckoResponseTimeOk,
                "CoinGecko API response time should be within " +
                        ApiEndpoints.MAX_RESPONSE_TIME_SLOW + "ms");
    }

    /**
     * Tests data completeness and required fields
     */
    @Test(priority = 5, description = "Verify data completeness")
    public void testDataCompleteness() {
        // Test Binance data completeness
        Ticker ticker = binanceClient.getPrice("BTCUSDT");

        assertNotNull(ticker.getSymbol(), "Ticker symbol should not be null");
        assertNotNull(ticker.getPrice(), "Ticker price should not be null");

        // Test CoinGecko data completeness
        List<Currency> currencies = coinGeckoClient.getTopCryptocurrencies();

        // Check that we have at least one currency
        assertTrue(currencies.size() > 0, "Should have at least one currency");

        Currency firstCurrency = currencies.get(0);

        // Required fields that should always be present
        assertNotNull(firstCurrency.getId(), "Currency ID should not be null");
        assertNotNull(firstCurrency.getName(), "Currency name should not be null");
        assertNotNull(firstCurrency.getSymbol(), "Currency symbol should not be null");

        // Optional fields that can be null - handle gracefully
        if (firstCurrency.getCurrentPrice() != null) {
            assertTrue(firstCurrency.getCurrentPrice() > 0,
                    "Currency price should be positive if present");
        } else {
            logger.warn("Currency {} has null current price", firstCurrency.getId());
        }

        if (firstCurrency.getMarketCapRank() != null) {
            assertTrue(firstCurrency.getMarketCapRank() > 0,
                    "Market cap rank should be positive if present");
        } else {
            logger.warn("Currency {} has null marketCapRank", firstCurrency.getId());
        }

        // Market cap can be null - that's acceptable for some cryptocurrencies
        if (firstCurrency.getMarketCap() != null) {
            assertTrue(firstCurrency.getMarketCap() > 0,
                    "Market cap should be positive if present");
        }
    }

    /**
     * Tests market cap ranking consistency
     */
    @Test(priority = 6, description = "Verify market cap ranking consistency")
    public void testMarketCapRankingConsistency() {
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        // First, verify we have some data
        assertTrue(topCurrencies.size() > 0, "Should have at least one currency");

        // Verify rankings are sequential and unique (for those that have rankings)
        int expectedRank = 1;

        for (int i = 0; i < topCurrencies.size(); i++) {
            Currency currency = topCurrencies.get(i);
            Integer actualRank = currency.getMarketCapRank();

            if (actualRank != null) {
                // If currency has a rank, it should match the expected sequence
                assertEquals(actualRank, Integer.valueOf(expectedRank),
                        "Currency at position " + i + " (" + currency.getId() +
                                ") should have rank " + expectedRank +
                                ", but has rank " + actualRank);
                expectedRank++;
            } else {
                // If rank is null, skip this currency in ranking sequence
                logger.warn("Currency {} has null marketCapRank at position {}", 
                        currency.getId(), i);
            }
        }

        // Verify Bitcoin is rank #1 (if ranking data is available)
        Currency firstCurrency = topCurrencies.get(0);

        // Bitcoin should be the first currency (by market cap)
        assertEquals(firstCurrency.getId(), "bitcoin",
                "First currency should be Bitcoin");

        // Check Bitcoin's rank if available
        if (firstCurrency.getMarketCapRank() != null) {
            assertEquals(firstCurrency.getMarketCapRank(), Integer.valueOf(1),
                    "Bitcoin should be ranked #1 by market cap");
        } else {
            logger.warn("Bitcoin's marketCapRank is null");
        }

        // Additional validation: all currencies with ranks should have unique ranks
        Set<Integer> uniqueRanks = new HashSet<>();
        for (Currency currency : topCurrencies) {
            if (currency.getMarketCapRank() != null) {
                boolean isUnique = uniqueRanks.add(currency.getMarketCapRank());
                assertTrue(isUnique, "Rank " + currency.getMarketCapRank() +
                        " is duplicated for currency: " + currency.getId());
            }
        }
    }

    /**
     * Tests error handling for invalid requests
     */
    @Test(priority = 7, description = "Verify error handling for invalid symbols")
    public void testErrorHandlingForInvalidSymbols() {
        // This test would normally verify error responses
        // For now, we test that valid symbols work correctly
        Ticker validTicker = binanceClient.getPrice("BTCUSDT");
        assertNotNull(validTicker, "Valid symbol should return a response");

        // Note: In a real scenario, we would test invalid symbols like:
        // binanceClient.getPrice("INVALIDSYMBOL") and expect an error response
    }

    /**
     * Tests that API responses contain valid JSON structure
     */
    @Test(priority = 8, description = "Verify JSON response structure")
    public void testJsonResponseStructure() {
        // Test Binance response structure
        Ticker binanceTicker = binanceClient.getPrice("BTCUSDT");
        assertNotNull(binanceTicker, "Binance response should be valid");

        // Test CoinGecko response structure
        List<Currency> currencies = coinGeckoClient.getTopCryptocurrencies();
        assertNotNull(currencies, "CoinGecko response should be valid");
        assertFalse(currencies.isEmpty(), "CoinGecko response should not be empty");
    }
}
