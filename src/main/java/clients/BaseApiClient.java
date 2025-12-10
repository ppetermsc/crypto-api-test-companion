package clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base client class providing common functionality for all API clients
 * Contains shared methods for HTTP requests, error handling, and configuration
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class BaseApiClient {

    /**
     * Base REST Assured request specification with common settings
     */
    protected RequestSpecification request;

    /**
     * Base URL for the API client
     */
    protected String baseUrl;
    
    /**
     * Delay between requests to respect API rate limits (milliseconds)
     */
    protected long requestDelayMs;
    
    /**
     * Logger for API client operations
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Constructs base API client with specified base URL
     *
     * @param baseUrl the base URL for API requests
     */
    public BaseApiClient(String baseUrl) {
        this(baseUrl, 8000); // Default 8 second delay
    }
    
    /**
     * Constructs base API client with specified base URL and delay
     *
     * @param baseUrl the base URL for API requests
     * @param requestDelayMs delay between requests in milliseconds
     */
    public BaseApiClient(String baseUrl, long requestDelayMs) {
        this.baseUrl = baseUrl;
        this.requestDelayMs = requestDelayMs;
        this.request = RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json");
        logger.debug("Initialized API client for base URL: {} (delay: {}ms)", baseUrl, requestDelayMs);
    }

    /**
     * Performs GET request to specified endpoint
     *
     * @param endpoint the API endpoint to call
     * @return Response object from the API
     */
    protected Response get(String endpoint) {
        logger.debug("GET {}{}", baseUrl, endpoint);
        Response response = request.get(endpoint);
        logger.debug("Response: {} ({} ms)", response.getStatusCode(), response.getTime());
        sleepBetweenRequests();
        return response;
    }

    /**
     * Performs GET request with query parameters
     *
     * @param endpoint the API endpoint to call
     * @param paramName the query parameter name
     * @param paramValue the query parameter value
     * @return Response object from the API
     */
    protected Response getWithParam(String endpoint, String paramName, String paramValue) {
        logger.debug("GET {}{}?{}={}", baseUrl, endpoint, paramName, paramValue);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .param(paramName, paramValue)
                .get(endpoint);
        logger.debug("Response: {} ({} ms)", response.getStatusCode(), response.getTime());
        sleepBetweenRequests();
        return response;
    }

    /**
     * Adds delay between API requests to avoid rate limiting
     */
    protected void sleepBetweenRequests() {
        try {
            if (requestDelayMs > 0) {
                logger.debug("Sleeping for {}ms to respect API rate limits", requestDelayMs);
                Thread.sleep(requestDelayMs);
            }
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Validates that response status code matches expected value
     *
     * @param response the API response to validate
     * @param expectedStatusCode the expected HTTP status code
     * @throws AssertionError if status code doesn't match expected
     */
    protected void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != expectedStatusCode) {
            String errorMessage = String.format("Expected status code %d but got %d. Response: %s",
                    expectedStatusCode, actualStatusCode, response.getBody().asString());
            logger.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
        logger.debug("Status code validation passed: {}", actualStatusCode);
    }

    /**
     * Validates that response time is within acceptable limits
     *
     * @param response the API response to validate
     * @param maxResponseTime maximum acceptable response time in milliseconds
     * @throws AssertionError if response time exceeds maximum
     */
    protected void validateResponseTime(Response response, long maxResponseTime) {
        long actualResponseTime = response.getTime();
        if (actualResponseTime > maxResponseTime) {
            String errorMessage = String.format("Response time %dms exceeds maximum %dms",
                    actualResponseTime, maxResponseTime);
            logger.warn(errorMessage);
            throw new AssertionError(errorMessage);
        }
        logger.debug("Response time validation passed: {}ms <= {}ms", 
                actualResponseTime, maxResponseTime);
    }
    
    /**
     * Gets the current request delay
     * @return delay in milliseconds
     */
    public long getRequestDelayMs() {
        return requestDelayMs;
    }
    
    /**
     * Sets the request delay
     * @param requestDelayMs delay in milliseconds
     */
    public void setRequestDelayMs(long requestDelayMs) {
        this.requestDelayMs = requestDelayMs;
        logger.debug("Request delay set to {}ms", requestDelayMs);
    }
}
