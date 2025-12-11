package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Represents price ticker data for trading pairs
 * Used for Binance API responses and real-time price information
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {
    /**
     * Trading pair symbol (e.g., "BTCUSDT", "ETHUSDT")
     */
    private String symbol;

    /**
     * Current price of the trading pair
     */
    private Double price;

    /**
     * Last executed trade price
     */
    private Double lastPrice;

    /**
     * Price change in the last 24 hours
     */
    private Double priceChange;

    /**
     * Price change percentage in the last 24 hours
     */
    private Double priceChangePercent;

    /**
     * Weighted average price in the last 24 hours
     */
    private Double weightedAvgPrice;

    /**
     * Previous close price
     */
    private Double prevClosePrice;

    /**
     * Last traded quantity
     */
    private Double lastQty;

    /**
     * Best bid price
     */
    private Double bidPrice;

    /**
     * Best ask price
     */
    private Double askPrice;

    /**
     * Open price 24 hours ago
     */
    private Double openPrice;

    /**
     * Highest price in the last 24 hours
     */
    private Double highPrice;

    /**
     * Lowest price in the last 24 hours
     */
    private Double lowPrice;

    /**
     * Total volume in the last 24 hours
     */
    private Double volume;

    /**
     * Total quote volume in the last 24 hours
     */
    private Double quoteVolume;

    /**
     * Timestamp when the data was generated
     */
    private Long openTime;

    /**
     * Timestamp when the data was last updated
     */
    private Long closeTime;

    /**
     * First trade ID in the last 24 hours
     */
    private Long firstId;

    /**
     * Last trade ID in the last 24 hours
     */
    private Long lastId;

    /**
     * Total number of trades in the last 24 hours
     */
    private Long count;
}