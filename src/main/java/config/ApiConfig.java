package config;

/**
 * Configuration class for API settings and environment variables
 * Centralizes all API-related configuration parameters
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class ApiConfig {

    /**
     * Base URL for Binance API
     */
    public static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3";

    /**
     * Base URL for CoinGecko API
     */
    public static final String COINGECKO_BASE_URL = "https://api.coingecko.com/api/v3";

    /**
     * Default timeout for API requests in milliseconds
     */
    public static final long DEFAULT_TIMEOUT = 10000L;

    /**
     * Maximum acceptable response time for normal operations in milliseconds
     */
    public static final long MAX_RESPONSE_TIME_NORMAL = 2000L;

    /**
     * Maximum acceptable response time for slow operations in milliseconds
     */
    public static final long MAX_RESPONSE_TIME_SLOW = 5000L;

    /**
     * Default number of retries for failed API requests
     */
    public static final int DEFAULT_RETRY_COUNT = 3;

    /**
     * Delay between retries in milliseconds
     */
    public static final long RETRY_DELAY_MS = 1000L;

    /**
     * Default cryptocurrency for testing
     */
    public static final String DEFAULT_CRYPTOCURRENCY = "bitcoin";

    /**
     * Default fiat currency for price conversion
     */
    public static final String DEFAULT_FIAT_CURRENCY = "usd";

    /**
     * Default number of items per page for paginated responses
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Enable or disable detailed request/response logging
     */
    public static final boolean ENABLE_DETAILED_LOGGING = true;

    /**
     * Enable or disable SSL certificate verification
     */
    public static final boolean ENABLE_SSL_VERIFICATION = true;

    /**
     * User-Agent string for API requests
     */
    public static final String USER_AGENT = "CryptoAPITestCompanion/1.0";

    /**
     * Default trading pair for Binance API tests
     */
    public static final String DEFAULT_TRADING_PAIR = "BTCUSDT";

    /**
     * Supported trading pairs for validation
     */
    public static final String[] SUPPORTED_TRADING_PAIRS = {
            "BTCUSDT", "ETHUSDT", "ADAUSDT", "DOTUSDT", "XRPUSDT"
    };

    /**
     * Supported vs currencies for CoinGecko API
     */
    public static final String[] SUPPORTED_VS_CURRENCIES = {
            "usd", "eur", "gbp", "jpy", "cad"
    };

    /**
     * Gets the complete URL for Binance API endpoint
     *
     * @param endpoint the API endpoint
     * @return complete URL string
     */
    public static String getBinanceUrl(String endpoint) {
        return BINANCE_BASE_URL + endpoint;
    }

    /**
     * Gets the complete URL for CoinGecko API endpoint
     *
     * @param endpoint the API endpoint
     * @return complete URL string
     */
    public static String getCoinGeckoUrl(String endpoint) {
        return COINGECKO_BASE_URL + endpoint;
    }

    /**
     * Checks if response time is acceptable for normal operations
     *
     * @param responseTime the response time in milliseconds
     * @return true if response time is acceptable
     */
    public static boolean isResponseTimeAcceptable(long responseTime) {
        return responseTime <= MAX_RESPONSE_TIME_NORMAL;
    }

    /**
     * Checks if response time is acceptable for slow operations
     *
     * @param responseTime the response time in milliseconds
     * @return true if response time is acceptable for slow operations
     */
    public static boolean isResponseTimeAcceptableForSlowOps(long responseTime) {
        return responseTime <= MAX_RESPONSE_TIME_SLOW;
    }
}
