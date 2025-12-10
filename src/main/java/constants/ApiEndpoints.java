package constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains constant API endpoints for cryptocurrency exchanges.
 * Centralized configuration for all API URLs, parameters, and default values used in the project.
 * This class serves as a single source of truth for all external API configurations.
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiEndpoints {

    // ============================================
    // Binance API Configuration
    // ============================================

    /**
     * Base URL for Binance REST API (version 3)
     */
    public static final String BINANCE_BASE_URL = "https://api.binance.com/api/v3";

    /**
     * Ping endpoint to test connectivity with Binance API
     * Returns empty JSON object {} if API is accessible
     */
    public static final String BINANCE_PING = "/ping";

    /**
     * Server time endpoint to get current server timestamp in milliseconds
     */
    public static final String BINANCE_SERVER_TIME = "/time";

    /**
     * Exchange information endpoint containing trading rules and symbol data
     */
    public static final String BINANCE_EXCHANGE_INFO = "/exchangeInfo";

    /**
     * Current price endpoint for specific trading pairs
     * Returns symbol and price as string
     */
    public static final String BINANCE_TICKER_PRICE = "/ticker/price";

    /**
     * 24-hour ticker price change statistics endpoint
     * Includes price change, volume, high/low prices, etc.
     */
    public static final String BINANCE_TICKER_24HR = "/ticker/24hr";

    // ============================================
    // CoinGecko API Configuration
    // ============================================

    /**
     * Base URL for CoinGecko REST API (version 3)
     */
    public static final String COINGECKO_BASE_URL = "https://api.coingecko.com/api/v3";

    /**
     * Ping endpoint to test connectivity with CoinGecko API
     * Returns {"gecko_says": "(V3) To the Moon!"} if API is accessible
     */
    public static final String COINGECKO_PING = "/ping";

    /**
     * Simple price endpoint for current cryptocurrency prices
     * Supports multiple cryptocurrencies and currencies in a single request
     */
    public static final String COINGECKO_SIMPLE_PRICE = "/simple/price";

    /**
     * Coins markets data endpoint with pagination support
     * Returns detailed market data including prices, market cap, and volume
     */
    public static final String COINGECKO_COINS_MARKETS = "/coins/markets";

    // ============================================
    // HTTP Status Codes
    // ============================================

    /**
     * HTTP status code 200: OK - Request succeeded
     */
    public static final int HTTP_OK = 200;

    // ============================================
    // API Parameter Names
    // ============================================

    /**
     * Parameter name for trading symbol (Binance API)
     * Example: "symbol=BTCUSDT"
     */
    public static final String PARAM_SYMBOL = "symbol";

    /**
     * Parameter name for cryptocurrency IDs (CoinGecko API)
     * Example: "ids=bitcoin,ethereum"
     */
    public static final String PARAM_IDS = "ids";

    /**
     * Parameter name for vs currencies (CoinGecko API - simple/price)
     * Example: "vs_currencies=usd,eur"
     */
    public static final String PARAM_VS_CURRENCIES = "vs_currencies";

    /**
     * Parameter name for vs currency (CoinGecko API - coins/markets)
     * Example: "vs_currency=usd"
     */
    public static final String PARAM_VS_CURRENCY = "vs_currency";

    /**
     * Parameter name for items per page (CoinGecko API)
     * Example: "per_page=10"
     */
    public static final String PARAM_PER_PAGE = "per_page";

    // ============================================
    // Default Values for Testing
    // ============================================

    /**
     * Default cryptocurrency ID used in tests (Bitcoin)
     */
    public static final String DEFAULT_CRYPTOCURRENCY = "bitcoin";

    /**
     * Default fiat currency used in tests (US Dollar)
     */
    public static final String DEFAULT_CURRENCY = "usd";

    /**
     * Default number of items per page for paginated responses
     */
    public static final int DEFAULT_PER_PAGE = 10;

    // ============================================
    // Performance Thresholds (in milliseconds)
    // ============================================

    /**
     * Maximum acceptable response time for normal API operations
     * Used for Binance API and fast CoinGecko endpoints
     */
    public static final long MAX_RESPONSE_TIME_NORMAL = 2000L;

    /**
     * Maximum acceptable response time for slower operations
     * Used for CoinGecko API endpoints that might be slower
     */
    public static final long MAX_RESPONSE_TIME_SLOW = 5000L;
}
