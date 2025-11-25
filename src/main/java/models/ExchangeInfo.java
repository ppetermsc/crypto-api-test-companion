package models;

import lombok.Data;
import java.util.List;

/**
 * Represents exchange information including trading rules and symbol data
 * Used for Binance API /api/v3/exchangeInfo endpoint
 *
 * @author PETR PESTRIAKOV
 * @version 1.0
 * @since 2025
 */
@Data
public class ExchangeInfo {
    /**
     * Exchange timezone
     */
    private String timezone;

    /**
     * Current server time in milliseconds
     */
    private Long serverTime;

    /**
     * List of trading rules and symbol information
     */
    private List<RateLimit> rateLimits;

    /**
     * List of exchange filters
     */
    private List<ExchangeFilter> exchangeFilters;

    /**
     * List of available trading symbols
     */
    private List<Symbol> symbols;

    /**
     * Represents rate limit rules for the exchange
     */
    @Data
    public static class RateLimit {
        /**
         * Type of rate limit (e.g., "REQUEST_WEIGHT", "ORDERS")
         */
        private String rateLimitType;

        /**
         * Interval type (e.g., "MINUTE", "SECOND")
         */
        private String interval;

        /**
         * Interval number (e.g., 1)
         */
        private Integer intervalNum;

        /**
         * Limit value (e.g., 1200)
         */
        private Integer limit;
    }

    /**
     * Represents exchange-level filters
     */
    @Data
    public static class ExchangeFilter {
        /**
         * Filter type
         */
        private String filterType;

        /**
         * Maximum number of orders
         */
        private Integer maxNumOrders;

        /**
         * Maximum number of algorithmic orders
         */
        private Integer maxNumAlgoOrders;
    }

    /**
     * Represents a trading symbol/pair
     */
    @Data
    public static class Symbol {
        /**
         * Trading symbol (e.g., "BTCUSDT")
         */
        private String symbol;

        /**
         * Symbol status (e.g., "TRADING")
         */
        private String status;

        /**
         * Base asset (e.g., "BTC")
         */
        private String baseAsset;

        /**
         * Base asset precision
         */
        private Integer baseAssetPrecision;

        /**
         * Quote asset (e.g., "USDT")
         */
        private String quoteAsset;

        /**
         * Quote asset precision
         */
        private Integer quoteAssetPrecision;

        /**
         * List of order types supported (e.g., "LIMIT", "MARKET")
         */
        private List<String> orderTypes;

        /**
         * If iceberg orders are allowed
         */
        private Boolean icebergAllowed;

        /**
         * If OCO orders are allowed
         */
        private Boolean ocoAllowed;

        /**
         * If spot trading is allowed
         */
        private Boolean spotTradingAllowed;

        /**
         * If margin trading is allowed
         */
        private Boolean marginTradingAllowed;

        /**
         * List of symbol filters
         */
        private List<SymbolFilter> filters;
    }

    /**
     * Represents symbol-level filters
     */
    @Data
    public static class SymbolFilter {
        /**
         * Filter type (e.g., "PRICE_FILTER", "LOT_SIZE")
         */
        private String filterType;

        /**
         * Minimum price allowed
         */
        private Double minPrice;

        /**
         * Maximum price allowed
         */
        private Double maxPrice;

        /**
         * Tick size for price
         */
        private Double tickSize;

        /**
         * Minimum quantity allowed
         */
        private Double minQty;

        /**
         * Maximum quantity allowed
         */
        private Double maxQty;

        /**
         * Step size for quantity
         */
        private Double stepSize;
    }
}
