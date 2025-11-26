package tests;

import clients.CoinGeckoClient;
import constants.ApiEndpoints;
import models.Currency;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ResponseHandler;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Test class for CoinGecko API functionality
 * Contains tests for cryptocurrency market data and price information
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class CoinGeckoApiTests {

    private CoinGeckoClient coinGeckoClient;

    /**
     * Sets up test environment before test execution
     */
    @BeforeClass
    public void setUp() {
        coinGeckoClient = new CoinGeckoClient();
    }

    /**
     * Tests that CoinGecko API is accessible and responds to ping requests
     */
    @Test(priority = 1, description = "Verify CoinGecko API connectivity")
    public void testCoinGeckoApiPing() {
        boolean isApiAvailable = coinGeckoClient.ping();
        assertTrue(isApiAvailable, "CoinGecko API should be available and respond to ping");
    }

    /**
     * Tests that Bitcoin price retrieval works correctly
     */
    @Test(priority = 2, description = "Verify Bitcoin price retrieval")
    public void testBitcoinPrice() {
        double bitcoinPrice = coinGeckoClient.getBitcoinPrice();

        assertTrue(bitcoinPrice > 0, "Bitcoin price should be positive");
        assertTrue(bitcoinPrice < 1000000, "Bitcoin price should be realistic (less than 1M USD)");
    }

    /**
     * Tests simple price endpoint with multiple cryptocurrencies
     */
    @Test(priority = 2, description = "Verify simple price endpoint with multiple coins")
    public void testSimplePriceWithMultipleCoins() {
        Map<String, Map<String, Double>> prices = coinGeckoClient.getSimplePrice(
                "bitcoin,ethereum,cardano",
                "usd,eur"
        );

        assertNotNull(prices, "Prices response should not be null");
        assertFalse(prices.isEmpty(), "Prices map should not be empty");

        // Verify Bitcoin price in USD
        assertTrue(prices.containsKey("bitcoin"), "Response should contain bitcoin data");
        assertTrue(prices.get("bitcoin").containsKey("usd"), "Bitcoin should have USD price");
        assertTrue(prices.get("bitcoin").get("usd") > 0, "Bitcoin USD price should be positive");

        // Verify Ethereum price in USD
        assertTrue(prices.containsKey("ethereum"), "Response should contain ethereum data");
        assertTrue(prices.get("ethereum").containsKey("usd"), "Ethereum should have USD price");
        assertTrue(prices.get("ethereum").get("usd") > 0, "Ethereum USD price should be positive");
    }

    /**
     * Tests retrieval of top cryptocurrencies by market cap
     */
    @Test(priority = 3, description = "Verify top cryptocurrencies retrieval")
    public void testTopCryptocurrencies() {
        List<Currency> topCurrencies = coinGeckoClient.getTopCryptocurrencies();

        assertNotNull(topCurrencies, "Top currencies list should not be null");
        assertEquals(topCurrencies.size(), ApiEndpoints.DEFAULT_PER_PAGE,
                "Should return exactly " + ApiEndpoints.DEFAULT_PER_PAGE + " currencies");

        // Verify first currency (should be Bitcoin)
        Currency firstCurrency = topCurrencies.get(0);
        assertNotNull(firstCurrency, "First currency should not be null");
        assertEquals(firstCurrency.getId(), "bitcoin", "First currency should be Bitcoin");
        assertTrue(firstCurrency.getCurrentPrice() > 0, "Bitcoin price should be positive");
        assertEquals(firstCurrency.getMarketCapRank(), Integer.valueOf(1),
                "Bitcoin should be ranked #1 by market cap");
    }

    /**
     * Tests market data for top cryptocurrencies
     */
    @Test(priority = 4, description = "Verify market data for top cryptocurrencies")
    public void testCoinsMarketsData() {
        List<Currency> currencies = coinGeckoClient.getCoinsMarkets("usd", 5);

        assertNotNull(currencies, "Currencies list should not be null");
        assertEquals(currencies.size(), 5, "Should return exactly 5 currencies");

        // Validate each currency in the list
        for (Currency currency : currencies) {
            assertNotNull(currency.getId(), "Currency ID should not be null");
            assertNotNull(currency.getSymbol(), "Currency symbol should not be null");
            assertNotNull(currency.getName(), "Currency name should not be null");
            assertTrue(currency.getCurrentPrice() > 0, "Currency price should be positive");
            assertTrue(currency.getMarketCapRank() > 0, "Market cap rank should be positive");

            // Market cap should be reasonable
            assertTrue(currency.getMarketCap() == null || currency.getMarketCap() > 0,
                    "Market cap should be positive or null");
        }
    }

    /**
     * Tests API response time for normal operations
     */
    @Test(priority = 5, description = "Verify API response time performance")
    public void testApiResponseTime() {
        boolean isResponseTimeAcceptable = coinGeckoClient.checkResponseTime(
                ApiEndpoints.MAX_RESPONSE_TIME_SLOW
        );

        assertTrue(isResponseTimeAcceptable,
                "API response time should be within " + ApiEndpoints.MAX_RESPONSE_TIME_SLOW + "ms");
    }

    /**
     * Tests data consistency between different endpoints
     */
    @Test(priority = 6, description = "Verify data consistency between endpoints")
    public void testDataConsistency() {
        // Get Bitcoin price from simple price endpoint
        double simplePrice = coinGeckoClient.getBitcoinPrice();

        // Get Bitcoin price from coins markets endpoint
        List<Currency> currencies = coinGeckoClient.getCoinsMarkets("usd", 1);
        double marketPrice = currencies.get(0).getCurrentPrice();

        // Prices should be relatively close (within 1% difference)
        double priceDifference = Math.abs(simplePrice - marketPrice);
        double maxAllowedDifference = simplePrice * 0.01; // 1% difference

        assertTrue(priceDifference <= maxAllowedDifference,
                "Bitcoin prices from different endpoints should be consistent. " +
                        "Simple price: " + simplePrice + ", Market price: " + marketPrice +
                        ", Difference: " + priceDifference);
    }

    /**
     * Tests that all required fields are present in currency data
     */
    @Test(priority = 7, description = "Verify required fields in currency data")
    public void testRequiredFields() {
        List<Currency> currencies = coinGeckoClient.getTopCryptocurrencies();
        Currency firstCurrency = currencies.get(0);

        assertNotNull(firstCurrency.getId(), "ID field should be present");
        assertNotNull(firstCurrency.getSymbol(), "Symbol field should be present");
        assertNotNull(firstCurrency.getName(), "Name field should be present");
        assertNotNull(firstCurrency.getCurrentPrice(), "Current price field should be present");
        assertNotNull(firstCurrency.getMarketCapRank(), "Market cap rank field should be present");
    }
}
