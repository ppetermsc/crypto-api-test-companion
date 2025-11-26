package constants;

/**
 * Contains constant API endpoints for cryptocurrency exchanges
 * Centralized configuration for all API URLs used in the project
 *
 * @author Petr Pestryakov
 * @version 1.0
 * @since 2025
 */
public class ApiEndpoints {

    /**
     * Binance API base URL
     */
    public static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3";

    /**
     * Binance API ping endpoint for connectivity check
     */
    public static final String BINANCE_PING = "/ping";

    /**
     * Binance API server time endpoint
     */
    public static final String BINANCE_SERVER_TIME = "/time";

    /**
     * Binance API exchange information endpoint
     */
    public static final String BINANCE_EXCHANGE_INFO = "/exchangeInfo";

    /**
     * Binance API current price endpoint for trading pairs
     */
    public static final String BINANCE_TICKER_PRICE = "/ticker/price";

    /**
     * Binance API 24-hour ticker statistics endpoint
     */
    public static final String BINANCE_TICKER_24HR = "/ticker/24hr";

    /**
     * Binance API order book depth endpoint
     */
    public static final String BINANCE_ORDER_BOOK = "/depth";

    /**
     * CoinGecko API base URL
     */
    public static final String COINGECKO_BASE_URL = "https://api.coingecko.com/api/v3";

    /**
     * CoinGecko API ping endpoint for connectivity check
     */
    public static final String COINGECKO_PING = "/ping";

    /**
     * CoinGecko API simple price endpoint
     */
    public static final String COINGECKO_SIMPLE_PRICE = "/simple/price";

    /**
     * CoinGecko API coins markets data endpoint
     */
    public static final String COINGECKO_COINS_MARKETS = "/coins/markets";

    /**
     * CoinGecko API coins list endpoint
     */
    public static final String COINGECKO_COINS_LIST = "/coins/list";

    /**
     * CoinGecko API exchanges list endpoint
     */
    public static final String COINGECKO_EXCHANGES = "/exchanges";

    /**
     * HTTP status code for successful request
     */
    public static final int HTTP_OK = 200;

    /**
     * HTTP status code for bad request
     */
    public static final int HTTP_BAD_REQUEST = 400;

    /**
     * HTTP status code for unauthorized access
     */
    public static final int HTTP_UNAUTHORIZED = 401;

    /**
     * HTTP status code for resource not found
     */
    public static final int HTTP_NOT_FOUND = 404;

    /**
     * HTTP status code for too many requests (rate limiting)
     */
    public static final int HTTP_TOO_MANY_REQUESTS = 429;

    /**
     * HTTP status code for internal server error
     */
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

    /**
     * Parameter name for trading symbol
     */
    public static final String PARAM_SYMBOL = "symbol";

    /**
     * Parameter name for cryptocurrency IDs
     */
    public static final String PARAM_IDS = "ids";

    /**
     * Parameter name for vs currencies
     */
    public static final String PARAM_VS_CURRENCIES = "vs_currencies";

    /**
     * Parameter name for vs currency
     */
    public static final String PARAM_VS_CURRENCY = "vs_currency";

    /**
     * Parameter name for items per page
     */
    public static final String PARAM_PER_PAGE = "per_page";

    /**
     * Default cryptocurrency ID for testing
     */
    public static final String DEFAULT_CRYPTOCURRENCY = "bitcoin";

    /**
     * Default currency for price conversion
     */
    public static final String DEFAULT_CURRENCY = "usd";

    /**
     * Default number of items per page
     */
    public static final int DEFAULT_PER_PAGE = 10;

    /**
     * Maximum acceptable response time for normal operations (milliseconds)
     */
    public static final long MAX_RESPONSE_TIME_NORMAL = 2000L;

    /**
     * Maximum acceptable response time for slow operations (milliseconds)
     */
    public static final long MAX_RESPONSE_TIME_SLOW = 5000L;
}