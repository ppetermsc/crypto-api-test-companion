package clients;

import constants.ApiEndpoints;
import io.restassured.response.Response;
import models.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Client for interacting with CoinGecko REST API
 * Provides methods to access cryptocurrency market data and prices
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class CoinGeckoClient extends BaseApiClient {
    
    private static final Logger logger = LoggerFactory.getLogger(CoinGeckoClient.class);

    /**
     * Constructs CoinGecko API client with CoinGecko base URL
     * Uses longer delay (30 seconds) for CoinGecko API rate limits
     */
    public CoinGeckoClient() {
        super(ApiEndpoints.COINGECKO_BASE_URL, 30000L); // 30 seconds for CoinGecko
        logger.info("CoinGecko client initialized with {}ms delay", getRequestDelayMs());
    }

    /**
     * Constructs CoinGecko API client with custom delay
     * @param requestDelayMs custom delay in milliseconds
     */
    public CoinGeckoClient(long requestDelayMs) {
        super(ApiEndpoints.COINGECKO_BASE_URL, requestDelayMs);
        logger.info("CoinGecko client initialized with custom delay: {}ms", requestDelayMs);
    }

    /**
     * Checks if CoinGecko API is available
     *
     * @return true if API responds successfully, false otherwise
     */
    public boolean ping() {
        sleepBetweenRequests(); // Rate limit protection
        Response response = get(ApiEndpoints.COINGECKO_PING);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }

    /**
     * Retrieves simple price for cryptocurrencies
     *
     * @param ids cryptocurrency IDs (e.g., "bitcoin,ethereum")
     * @param vsCurrencies vs currencies (e.g., "usd,eur")
     * @return Map of cryptocurrency prices with Object values to handle both Integer and Double
     */
    public Map<String, Map<String, Object>> getSimplePrice(String ids, String vsCurrencies) {
        sleepBetweenRequests(); // Rate limit protection
        Response response = request
                .param(ApiEndpoints.PARAM_IDS, ids)
                .param(ApiEndpoints.PARAM_VS_CURRENCIES, vsCurrencies)
                .get(ApiEndpoints.COINGECKO_SIMPLE_PRICE);

        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.jsonPath().getMap("$");
    }

    /**
     * Retrieves the current Bitcoin price in USD.
     * Fetches data from the CoinGecko API and extracts the USD price for Bitcoin.
     *
     * @return Bitcoin price in USD as a double value
     * @throws RuntimeException if the API response is empty, does not contain Bitcoin data,
     *                          or if the Bitcoin USD price is missing, null, or of an unexpected type
     */
    public double getBitcoinPrice() {
        // Fetch price data from the CoinGecko API
        Map<String, Map<String, Object>> prices = getSimplePrice(
                ApiEndpoints.DEFAULT_CRYPTOCURRENCY,
                ApiEndpoints.DEFAULT_CURRENCY
        );

        // 1. Validate that the API response is not null or empty
        if (prices == null || prices.isEmpty()) {
            throw new RuntimeException("Empty or null response received from CoinGecko API.");
        }

        // 2. Ensure the response contains the expected 'bitcoin' key
        if (!prices.containsKey(ApiEndpoints.DEFAULT_CRYPTOCURRENCY)) {
            throw new RuntimeException(
                    "Bitcoin data not found in API response. Available keys: " + prices.keySet()
            );
        }

        // 3. Retrieve the Bitcoin data map and validate it
        Map<String, Object> bitcoinData = prices.get(ApiEndpoints.DEFAULT_CRYPTOCURRENCY);
        if (bitcoinData == null) {
            throw new RuntimeException("The data map for Bitcoin is null.");
        }

        // 4. Check that the USD price key exists within the Bitcoin data
        if (!bitcoinData.containsKey(ApiEndpoints.DEFAULT_CURRENCY)) {
            throw new RuntimeException(
                    "USD price for Bitcoin not found. Available currency keys: " + bitcoinData.keySet()
            );
        }

        // 5. Extract the price object
        Object price = bitcoinData.get(ApiEndpoints.DEFAULT_CURRENCY);

        // 6. Final null check for the price value itself
        if (price == null) {
            throw new RuntimeException("The USD price value for Bitcoin is null.");
        }

        // 7. Handle different numeric types that the API might return
        if (price instanceof Integer) {
            return ((Integer) price).doubleValue();
        } else if (price instanceof Double) {
            return (Double) price;
        } else if (price instanceof Long) {
            return ((Long) price).doubleValue();
        } else if (price instanceof Float) {
            return ((Float) price).doubleValue();
        } else {
            // Throw an informative error for any non-numeric type
            throw new RuntimeException(
                    "Unsupported data type for price: " + price.getClass().getName() + ". Value: '" + price + "'"
            );
        }
    }

    /**
     * Retrieves market data for top cryptocurrencies
     *
     * @param vsCurrency the currency to display values in (e.g., "usd")
     * @param perPage number of results per page
     * @return List of Currency objects with market data
     */
    public List<Currency> getCoinsMarkets(String vsCurrency, int perPage) {
        sleepBetweenRequests(); // Rate limit protection
        Response response = request
                .param(ApiEndpoints.PARAM_VS_CURRENCY, vsCurrency)
                .param(ApiEndpoints.PARAM_PER_PAGE, perPage)
                .get(ApiEndpoints.COINGECKO_COINS_MARKETS);

        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.jsonPath().getList(".", Currency.class);
    }

    /**
     * Retrieves top 10 cryptocurrencies by market cap
     *
     * @return List of top 10 cryptocurrencies
     */
    public List<Currency> getTopCryptocurrencies() {
        return getCoinsMarkets(
                ApiEndpoints.DEFAULT_CURRENCY,
                ApiEndpoints.DEFAULT_PER_PAGE
        );
    }

    /**
     * Validates that API response time is within acceptable limits
     *
     * @param maxResponseTime maximum allowed response time in milliseconds
     * @return true if ping response time is within limits
     */
    public boolean checkResponseTime(long maxResponseTime) {
        sleepBetweenRequests(); // Rate limit protection
        Response response = get(ApiEndpoints.COINGECKO_PING);
        validateResponseTime(response, maxResponseTime);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }
}
