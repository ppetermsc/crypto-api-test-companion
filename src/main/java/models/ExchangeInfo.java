package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Represents exchange information including trading rules, rate limits, and symbol data.
 * This model corresponds to the Binance API /api/v3/exchangeInfo endpoint response.
 * Used for validating exchange capabilities and available trading pairs.
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeInfo {

    /**
     * Timezone of the exchange server (e.g., "UTC")
     */
    private String timezone;

    /**
     * Current server time in milliseconds since Unix epoch
     * Used for timestamp validation and synchronization
     */
    private Long serverTime;

    /**
     * List of rate limit rules applied by the exchange
     * Defines request limits for different types of API calls
     */
    private List<RateLimit> rateLimits;

    /**
     * List of exchange-level filters and restrictions
     * Applies globally to all trading pairs on the exchange
     */
    private List<ExchangeFilter> exchangeFilters;

    /**
     * List of available trading symbols/pairs on the exchange
     * Contains detailed information for each trading pair
     */
    private List<Symbol> symbols;

    /**
     * Represents a rate limit rule defined by the exchange.
     * Rate limits control the frequency of API requests to prevent abuse.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RateLimit {

        /**
         * Type of rate limit (e.g., "REQUEST_WEIGHT", "ORDERS", "RAW_REQUESTS")
         */
        private String rateLimitType;

        /**
         * Time interval unit (e.g., "MINUTE", "SECOND", "DAY")
         */
        private String interval;

        /**
         * Number of intervals (e.g., 1 for "1 MINUTE", 60 for "60 SECOND")
         */
        private Integer intervalNum;

        /**
         * Maximum number of requests allowed within the interval
         */
        private Integer limit;
    }

    /**
     * Represents an exchange-level filter that applies to all trading pairs.
     * These filters define global trading restrictions and capabilities.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExchangeFilter {

        /**
         * Type of exchange filter (e.g., "EXCHANGE_MAX_NUM_ORDERS")
         */
        private String filterType;

        /**
         * Maximum number of orders an account can have open simultaneously
         */
        private Integer maxNumOrders;

        /**
         * Maximum number of algorithmic orders an account can have open simultaneously
         */
        private Integer maxNumAlgoOrders;
    }

    /**
     * Represents a trading symbol (pair) available on the exchange.
     * Contains detailed specifications for trading a specific asset pair.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Symbol {

        /**
         * Trading pair symbol (e.g., "BTCUSDT", "ETHUSDT")
         */
        private String symbol;

        /**
         * Current status of the symbol (e.g., "TRADING", "BREAK", "HALT")
         */
        private String status;

        /**
         * Base asset being traded (e.g., "BTC" in BTCUSDT pair)
         */
        private String baseAsset;

        /**
         * Precision (number of decimal places) for the base asset
         */
        private Integer baseAssetPrecision;

        /**
         * Quote asset used for pricing (e.g., "USDT" in BTCUSDT pair)
         */
        private String quoteAsset;

        /**
         * Precision (number of decimal places) for the quote asset
         */
        private Integer quoteAssetPrecision;

        /**
         * List of order types supported for this symbol (e.g., "LIMIT", "MARKET")
         */
        private List<String> orderTypes;

        /**
         * Indicates whether iceberg orders are allowed for this symbol
         */
        private Boolean icebergAllowed;

        /**
         * Indicates whether OCO (One-Cancels-Other) orders are allowed
         */
        private Boolean ocoAllowed;

        /**
         * Indicates whether spot trading is allowed for this symbol
         */
        private Boolean spotTradingAllowed;

        /**
         * Indicates whether margin trading is allowed for this symbol
         */
        private Boolean marginTradingAllowed;

        /**
         * List of symbol-specific filters (e.g., price filter, lot size filter)
         */
        private List<SymbolFilter> filters;
    }

    /**
     * Represents a symbol-level filter that applies to specific trading rules.
     * These filters define trading constraints for individual symbols.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SymbolFilter {

        /**
         * Type of symbol filter (e.g., "PRICE_FILTER", "LOT_SIZE", "MIN_NOTIONAL")
         */
        private String filterType;

        /**
         * Minimum price allowed for orders on this symbol
         */
        private Double minPrice;

        /**
         * Maximum price allowed for orders on this symbol
         */
        private Double maxPrice;

        /**
         * Tick size - minimum price increment for orders
         */
        private Double tickSize;

        /**
         * Minimum quantity (base asset) allowed per order
         */
        private Double minQty;

        /**
         * Maximum quantity (base asset) allowed per order
         */
        private Double maxQty;

        /**
         * Step size - minimum quantity increment for orders
         */
        private Double stepSize;
    }
}
