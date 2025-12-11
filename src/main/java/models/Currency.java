package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Represents a cryptocurrency with market data
 * Used for API responses from CoinGecko and other cryptocurrency data providers
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignores unknown fields from API
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ignores null fields when deserializing
public class Currency {
    /**
     * Unique cryptocurrency identifier (e.g., "bitcoin")
     */
    private String id;

    /**
     * Cryptocurrency symbol (e.g., "btc", "eth")
     */
    private String symbol;

    /**
     * Full cryptocurrency name (e.g., "Bitcoin", "Ethereum")
     */
    private String name;

    /**
     * URL to cryptocurrency image/logo
     */
    private String image;

    /**
     * Current price in USD
     */
    private Double currentPrice;

    /**
     * Market capitalization in USD
     */
    private Long marketCap;

    /**
     * Market cap rank among all cryptocurrencies
     */
    private Integer marketCapRank;

    /**
     * 24-hour price change in USD
     */
    private Double priceChange24h;

    /**
     * 24-hour price change percentage
     */
    private Double priceChangePercentage24h;
}
