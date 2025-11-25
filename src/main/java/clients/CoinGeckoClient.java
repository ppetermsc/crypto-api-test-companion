package clients;

import constants.ApiEndpoints;
import io.restassured.response.Response;
import models.Currency;

import java.util.List;
import java.util.Map;

/**
 * Client for interacting with CoinGecko REST API
 * Provides methods to access cryptocurrency market data and prices
 *
 * @author Your Name
 * @version 1.0
 * @since 2025
 */
public class CoinGeckoClient extends BaseApiClient {

    /**
     * Constructs CoinGecko API client with CoinGecko base URL
     */
    public CoinGeckoClient() {
        super(ApiEndpoints.COINGECKO_BASE_URL);
    }

    /**
     * Checks if CoinGecko API is available
     *
     * @return true if API responds successfully, false otherwise
     */
    public boolean ping() {
        Response response = get(ApiEndpoints.COINGECKO_PING);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }

    /**
     * Retrieves simple price for cryptocurrencies
     *
     * @param ids cryptocurrency IDs (e.g., "bitcoin,ethereum")
     * @param vsCurrencies vs currencies (e.g., "usd,eur")
     * @return Map of cryptocurrency prices
     */
    public Map<String, Map<String, Double>> getSimplePrice(String ids, String vsCurrencies) {
        Response response = request
                .param(ApiEndpoints.PARAM_IDS, ids)
                .param(ApiEndpoints.PARAM_VS_CURRENCIES, vsCurrencies)
                .get(ApiEndpoints.COINGECKO_SIMPLE_PRICE);

        validateStatusCode(response, ApiEndpoints.HTTP_OK);
        return response.jsonPath().getMap("$");
    }

    /**
     * Retrieves current Bitcoin price in USD
     *
     * @return Bitcoin price in USD
     */
    public double getBitcoinPrice() {
        Map<String, Map<String, Double>> prices = getSimplePrice(
                ApiEndpoints.DEFAULT_CRYPTOCURRENCY,
                ApiEndpoints.DEFAULT_CURRENCY
        );

        return prices.get(ApiEndpoints.DEFAULT_CRYPTOCURRENCY)
                .get(ApiEndpoints.DEFAULT_CURRENCY);
    }

    /**
     * Retrieves market data for top cryptocurrencies
     *
     * @param vsCurrency the currency to display values in (e.g., "usd")
     * @param perPage number of results per page
     * @return List of Currency objects with market data
     */
    public List<Currency> getCoinsMarkets(String vsCurrency, int perPage) {
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
        Response response = get(ApiEndpoints.COINGECKO_PING);
        validateResponseTime(response, maxResponseTime);
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }
}