package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

/**
 * Utility class for JSON validation and schema verification
 * Provides methods to validate JSON structure and content
 *
 * @author Petr Pestryakov
 * @version 1.0
 * @since 2025
 */
public class JsonValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Validates that JSON response contains required fields
     *
     * @param response the API response to validate
     * @param requiredFields array of required field names
     * @return true if all required fields are present
     * @throws IOException if JSON parsing fails
     */
    public static boolean hasRequiredFields(Response response, String... requiredFields) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());

        for (String field : requiredFields) {
            if (!jsonNode.has(field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates that a field exists and is not null in JSON response
     *
     * @param response the API response to validate
     * @param fieldName the field name to check
     * @return true if field exists and is not null
     * @throws IOException if JSON parsing fails
     */
    public static boolean isFieldPresentAndNotNull(Response response, String fieldName) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull();
    }

    /**
     * Validates that a field is of expected type
     *
     * @param response the API response to validate
     * @param fieldName the field name to check
     * @param expectedType the expected JSON type
     * @return true if field exists and matches expected type
     * @throws IOException if JSON parsing fails
     */
    public static boolean isFieldOfType(Response response, String fieldName, Class<?> expectedType) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());

        if (!jsonNode.has(fieldName)) {
            return false;
        }

        JsonNode fieldNode = jsonNode.get(fieldName);
        return fieldNode.getClass().equals(expectedType);
    }

    /**
     * Validates that a numeric field is within specified range
     *
     * @param response the API response to validate
     * @param fieldName the field name to check
     * @param min minimum allowed value (inclusive)
     * @param max maximum allowed value (inclusive)
     * @return true if field is within specified range
     * @throws IOException if JSON parsing fails
     */
    public static boolean isNumericFieldInRange(Response response, String fieldName,
                                                double min, double max) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());

        if (!jsonNode.has(fieldName) || !jsonNode.get(fieldName).isNumber()) {
            return false;
        }

        double value = jsonNode.get(fieldName).asDouble();
        return value >= min && value <= max;
    }

    /**
     * Validates that a string field matches expected pattern
     *
     * @param response the API response to validate
     * @param fieldName the field name to check
     * @param pattern the regex pattern to match
     * @return true if field matches the pattern
     * @throws IOException if JSON parsing fails
     */
    public static boolean doesFieldMatchPattern(Response response, String fieldName,
                                                String pattern) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());

        if (!jsonNode.has(fieldName) || !jsonNode.get(fieldName).isTextual()) {
            return false;
        }

        String value = jsonNode.get(fieldName).asText();
        return value.matches(pattern);
    }

    /**
     * Extracts specific field value from JSON response
     *
     * @param response the API response
     * @param fieldName the field name to extract
     * @return field value as string
     * @throws IOException if JSON parsing fails
     */
    public static String extractFieldValue(Response response, String fieldName) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : null;
    }
}