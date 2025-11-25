package clients;

import constants.ApiEndpoints;
import io.restassured.response.Response;
import models.ExchangeInfo;
import models.Ticker;

/**
 * Client for interacting with Binance REST API
 * Provides methods to access market data and exchange information
 *
 * @author Your Name
 * @version 1.0
 * @since 2025
 */
public class BinanceClient extends BaseApiClient {

    /**
     * Constructs Binance API client with Binance base URL
     */
    public BinanceClient() {
        super(ApiEndpoints.BINANCE_BASE_URL);  // 👈 Теперь константы используются!
    }

    /**
     * Checks if Binance API is available
     *
     * @return true if API responds successfully, false otherwise
     */
    public boolean ping() {
        Response response = get(ApiEndpoints.BINANCE_PING);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }

    /**
     * Retrieves current server time from Binance
     *
     * @return server time in milliseconds
     */
    public long getServerTime() {
        Response response = get(ApiEndpoints.BINANCE_SERVER_TIME);
        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.jsonPath().getLong("serverTime");
    }

    /**
     * Retrieves exchange information including trading rules and symbols
     *
     * @return ExchangeInfo object containing exchange data
     */
    public ExchangeInfo getExchangeInfo() {
        Response response = get(ApiEndpoints.BINANCE_EXCHANGE_INFO);
        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.as(ExchangeInfo.class);
    }

    /**
     * Retrieves current price for a trading pair
     *
     * @param symbol the trading pair symbol (e.g., "BTCUSDT")
     * @return Ticker object with price information
     */
    public Ticker getPrice(String symbol) {
        Response response = getWithParam(ApiEndpoints.BINANCE_TICKER_PRICE,
                ApiEndpoints.PARAM_SYMBOL, symbol);
        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.as(Ticker.class);
    }

    /**
     * Retrieves 24-hour ticker price change statistics
     *
     * @param symbol the trading pair symbol (e.g., "BTCUSDT")
     * @return Ticker object with 24hr statistics
     */
    public Ticker get24hrTicker(String symbol) {
        Response response = getWithParam(ApiEndpoints.BINANCE_TICKER_24HR,
                ApiEndpoints.PARAM_SYMBOL, symbol);
        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.as(Ticker.class);
    }

    /**
     * Validates that API response time is within acceptable limits
     *
     * @param maxResponseTime maximum allowed response time in milliseconds
     * @return true if ping response time is within limits
     */
    public boolean checkResponseTime(long maxResponseTime) {
        Response response = get(ApiEndpoints.BINANCE_PING);
        validateResponseTime(response, maxResponseTime);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }
}
