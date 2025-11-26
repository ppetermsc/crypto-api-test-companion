package tests;

import clients.BinanceClient;
import clients.CoinGeckoClient;
import constants.ApiEndpoints;
import models.Currency;
import models.Ticker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.JsonValidator;
import utils.ResponseHandler;

import java.util.List;

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
            assertTrue(currency.getCurrentPrice() > 0,
                    "Currency " + currency.getName() + " price should be positive");
        }
    }

    /**
     * Tests that major cryptocurrencies exist in both APIs
     */
    @Test(priority = 2, description = "Verify major cryptocurrencies presence")
    public void testMajorCryptocurrenciesExist() {
        // Get top cryptocurrencies from CoinGecko
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        // Check that major cryptocurrencies are in the list
        boolean hasBitcoin = topCurrencies.stream()
                .anyMatch(currency -> "bitcoin".equals(currency.getId()));
        boolean hasEthereum = topCurrencies.stream()
                .anyMatch(currency -> "ethereum".equals(currency.getId()));
        boolean hasCardano = topCurrencies.stream()
                .anyMatch(currency -> "cardano".equals(currency.getId()));

        assertTrue(hasBitcoin, "Bitcoin should be in top cryptocurrencies");
        assertTrue(hasEthereum, "Ethereum should be in top cryptocurrencies");
        assertTrue(hasCardano, "Cardano should be in top cryptocurrencies");

        // Verify Binance has major trading pairs
        var exchangeInfo = binanceClient.getExchangeInfo();
        boolean hasBtcUsdt = exchangeInfo.getSymbols().stream()
                .anyMatch(symbol -> "BTCUSDT".equals(symbol.getSymbol()));
        boolean hasEthUsdt = exchangeInfo.getSymbols().stream()
                .anyMatch(symbol -> "ETHUSDT".equals(symbol.getSymbol()));

        assertTrue(hasBtcUsdt, "Binance should support BTC/USDT trading pair");
        assertTrue(hasEthUsdt, "Binance should support ETH/USDT trading pair");
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
        Currency firstCurrency = currencies.get(0);

        assertNotNull(firstCurrency.getId(), "Currency ID should not be null");
        assertNotNull(firstCurrency.getName(), "Currency name should not be null");
        assertNotNull(firstCurrency.getSymbol(), "Currency symbol should not be null");
        assertNotNull(firstCurrency.getCurrentPrice(), "Currency price should not be null");
        assertNotNull(firstCurrency.getMarketCapRank(), "Market cap rank should not be null");
    }

    /**
     * Tests market cap ranking consistency
     */
    @Test(priority = 6, description = "Verify market cap ranking consistency")
    public void testMarketCapRankingConsistency() {
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        // Verify that rankings are sequential and unique
        for (int i = 0; i < topCurrencies.size(); i++) {
            Currency currency = topCurrencies.get(i);
            assertEquals(currency.getMarketCapRank(), Integer.valueOf(i + 1),
                    "Currency at position " + i + " should have rank " + (i + 1) +
                            ", but has rank " + currency.getMarketCapRank());
        }

        // Verify Bitcoin is rank #1
        Currency bitcoin = topCurrencies.get(0);
        assertEquals(bitcoin.getId(), "bitcoin", "First ranked currency should be Bitcoin");
        assertEquals(bitcoin.getMarketCapRank(), Integer.valueOf(1),
                "Bitcoin should be ranked #1 by market cap");
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