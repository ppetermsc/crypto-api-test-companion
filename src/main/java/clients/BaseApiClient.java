package clients;

import constants.ApiEndpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base client class providing common functionality for all API clients
 * Contains shared methods for HTTP requests, error handling, and configuration
 *
 * @author Your Name
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
     * Constructs base API client with specified base URL
     *
     * @param baseUrl the base URL for API requests
     */
    public BaseApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.request = RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json");
    }

    /**
     * Performs GET request to specified endpoint
     *
     * @param endpoint the API endpoint to call
     * @return Response object from the API
     */
    protected Response get(String endpoint) {
        return request.get(endpoint);
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
        return request.param(paramName, paramValue).get(endpoint);
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
            throw new AssertionError(
                    String.format("Expected status code %d but got %d. Response: %s",
                            expectedStatusCode, actualStatusCode, response.getBody().asString())
            );
        }
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
            throw new AssertionError(
                    String.format("Response time %dms exceeds maximum %dms",
                            actualResponseTime, maxResponseTime)
            );
        }
    }
}
