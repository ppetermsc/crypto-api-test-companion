package utils;

import constants.ApiEndpoints;
import io.restassured.response.Response;

/**
 * Utility class for handling and processing API responses
 * Provides methods to extract data and validate response properties
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class ResponseHandler {

    /**
     * Extracts response time from API response
     *
     * @param response the API response
     * @return response time in milliseconds
     */
    public static long getResponseTime(Response response) {
        return response.getTime();
    }

    /**
     * Validates if response time is within acceptable limits
     *
     * @param response the API response to validate
     * @param maxResponseTime maximum allowed response time in milliseconds
     * @return true if response time is within limits
     */
    public static boolean isResponseTimeAcceptable(Response response, long maxResponseTime) {
        return getResponseTime(response) <= maxResponseTime;
    }

    /**
     * Validates if response has successful status code
     *
     * @param response the API response to validate
     * @return true if status code is 200 (OK)
     */
    public static boolean isSuccess(Response response) {
        return response.getStatusCode() == ApiEndpoints.HTTP_OK;
    }

    /**
     * Validates if response indicates client error (4xx)
     *
     * @param response the API response to validate
     * @return true if status code is in 4xx range
     */
    public static boolean isClientError(Response response) {
        int statusCode = response.getStatusCode();
        return statusCode >= 400 && statusCode < 500;
    }

    /**
     * Validates if response indicates server error (5xx)
     *
     * @param response the API response to validate
     * @return true if status code is in 5xx range
     */
    public static boolean isServerError(Response response) {
        int statusCode = response.getStatusCode();
        return statusCode >= 500 && statusCode < 600;
    }

    /**
     * Extracts specific field value from JSON response as String
     *
     * @param response the API response
     * @param jsonPath the JSON path to the field
     * @return field value as string, or null if not found
     */
    public static String extractString(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    /**
     * Extracts specific field value from JSON response as Double
     *
     * @param response the API response
     * @param jsonPath the JSON path to the field
     * @return field value as double, or null if not found
     */
    public static Double extractDouble(Response response, String jsonPath) {
        return response.jsonPath().getDouble(jsonPath);
    }

    /**
     * Extracts specific field value from JSON response as Integer
     *
     * @param response the API response
     * @param jsonPath the JSON path to the field
     * @return field value as integer, or null if not found
     */
    public static Integer extractInteger(Response response, String jsonPath) {
        return response.jsonPath().getInt(jsonPath);
    }

    /**
     * Extracts specific field value from JSON response as Long
     *
     * @param response the API response
     * @param jsonPath the JSON path to the field
     * @return field value as long, or null if not found
     */
    public static Long extractLong(Response response, String jsonPath) {
        return response.jsonPath().getLong(jsonPath);
    }

    /**
     * Validates that response content type is JSON
     *
     * @param response the API response to validate
     * @return true if content type is application/json
     */
    public static boolean isJsonContentType(Response response) {
        return response.getContentType().contains("application/json");
    }

    /**
     * Prints response details for debugging purposes
     *
     * @param response the API response to log
     */
    public static void printResponseDetails(Response response) {
        System.out.println("=== Response Details ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Time: " + getResponseTime(response) + "ms");
        System.out.println("Content Type: " + response.getContentType());
        System.out.println("Body: " + response.getBody().asString());
        System.out.println("=========================");
    }

    /**
     * Validates that response body is not empty
     *
     * @param response the API response to validate
     * @return true if response body is not null and not empty
     */
    public static boolean isBodyNotEmpty(Response response) {
        String body = response.getBody().asString();
        return body != null && !body.trim().isEmpty() && !body.equals("{}");
    }
}