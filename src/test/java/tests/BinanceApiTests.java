package tests;

import clients.BinanceClient;
import constants.ApiEndpoints;
import models.Ticker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class for Binance API functionality
 * Contains tests for market data endpoints and API reliability
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class BinanceApiTests {

    private BinanceClient binanceClient;

    /**
     * Sets up test environment before test execution
     */
    @BeforeClass
    public void setUp() {
        binanceClient = new BinanceClient();
    }

    /**
     * Tests that Binance API is accessible and responds to ping requests
     */
    @Test(priority = 1, description = "Verify Binance API connectivity")
    public void testBinanceApiPing() {
        boolean isApiAvailable = binanceClient.ping();
        assertTrue(isApiAvailable, "Binance API should be available and respond to ping");
    }

    /**
     * Tests that server time endpoint returns valid timestamp
     */
    @Test(priority = 1, description = "Verify server time endpoint")
    public void testServerTime() {
        long serverTime = binanceClient.getServerTime();

        assertTrue(serverTime > 0, "Server time should be positive");

        long currentTime = System.currentTimeMillis();
        long timeDifference = Math.abs(serverTime - currentTime);

        assertTrue(timeDifference < 60000,
                "Server time should be within 60 seconds of current time. Difference: " + timeDifference + "ms");
    }

    /**
     * Tests that price for BTC/USDT trading pair is valid
     */
    @Test(priority = 2, description = "Verify BTC/USDT price validity")
    public void testBtcUsdtPrice() {
        Ticker ticker = binanceClient.getPrice("BTCUSDT");

        assertNotNull(ticker, "Ticker response should not be null");
        assertEquals(ticker.getSymbol(), "BTCUSDT", "Symbol should match requested trading pair");
        assertTrue(ticker.getPrice() > 0, "BTC price should be positive");
        assertTrue(ticker.getPrice() < 1000000, "BTC price should be realistic (less than 1M USD)");
    }

    /**
     * Tests that price for ETH/USDT trading pair is valid
     */
    @Test(priority = 2, description = "Verify ETH/USDT price validity")
    public void testEthUsdtPrice() {
        Ticker ticker = binanceClient.getPrice("ETHUSDT");

        assertNotNull(ticker, "Ticker response should not be null");
        assertEquals(ticker.getSymbol(), "ETHUSDT", "Symbol should match requested trading pair");
        assertTrue(ticker.getPrice() > 0, "ETH price should be positive");
        assertTrue(ticker.getPrice() < 10000, "ETH price should be realistic (less than 10K USD)");
    }

    /**
     * Tests API response time for normal operations
     */
    @Test(priority = 3, description = "Verify API response time performance")
    public void testApiResponseTime() {
        boolean isResponseTimeAcceptable = binanceClient.checkResponseTime(
                ApiEndpoints.MAX_RESPONSE_TIME_NORMAL
        );

        assertTrue(isResponseTimeAcceptable,
                "API response time should be within " + ApiEndpoints.MAX_RESPONSE_TIME_NORMAL + "ms");
    }

    /**
     * Tests 24-hour ticker statistics for BTC/USDT
     */
    @Test(priority = 4, description = "Verify 24-hour ticker statistics")
    public void test24hrTickerStatistics() {
        Ticker ticker = binanceClient.get24hrTicker("BTCUSDT");

        assertNotNull(ticker, "24hr ticker response should not be null");
        assertEquals(ticker.getSymbol(), "BTCUSDT", "Symbol should match requested trading pair");

        // Basic validation of 24hr statistics
        assertNotNull(ticker.getPriceChange(), "Price change should not be null");
        assertNotNull(ticker.getPriceChangePercent(), "Price change percent should not be null");
        assertNotNull(ticker.getVolume(), "Volume should not be null");

        // Volume should be positive
        assertTrue(ticker.getVolume() >= 0, "Volume should be non-negative");
    }

    /**
     * Tests exchange information endpoint
     */
    @Test(priority = 5, description = "Verify exchange information endpoint")
    public void testExchangeInfo() {
        var exchangeInfo = binanceClient.getExchangeInfo();

        assertNotNull(exchangeInfo, "Exchange info response should not be null");
        assertNotNull(exchangeInfo.getSymbols(), "Symbols list should not be null");
        assertFalse(exchangeInfo.getSymbols().isEmpty(), "Symbols list should not be empty");

        // Verify that major trading pairs are present
        boolean hasBtcUsdt = exchangeInfo.getSymbols().stream()
                .anyMatch(symbol -> "BTCUSDT".equals(symbol.getSymbol()));
        boolean hasEthUsdt = exchangeInfo.getSymbols().stream()
                .anyMatch(symbol -> "ETHUSDT".equals(symbol.getSymbol()));

        assertTrue(hasBtcUsdt, "Exchange should support BTCUSDT trading pair");
        assertTrue(hasEthUsdt, "Exchange should support ETHUSDT trading pair");
    }

    /**
     * Tests API response format and content type
     */
    @Test(priority = 6, description = "Verify API response format")
    public void testApiResponseFormat() {
        var response = binanceClient.getPrice("BTCUSDT");

        // This would be enhanced with actual response validation
        // For now, we're testing that we get a valid response object
        assertNotNull(response, "API should return a valid response");
    }
}
