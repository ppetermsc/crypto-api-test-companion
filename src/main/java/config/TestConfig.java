package config;

/**
 * Configuration class for test settings and execution parameters
 * Centralizes all test-related configuration parameters
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class TestConfig {

    /**
     * Default test timeout in milliseconds
     */
    public static final long TEST_TIMEOUT = 30000L;

    /**
     * Enable or disable parallel test execution
     */
    public static final boolean PARALLEL_EXECUTION = false;

    /**
     * Number of threads for parallel execution
     */
    public static final int THREAD_COUNT = 3;

    /**
     * Enable or disable test retry on failure
     */
    public static final boolean ENABLE_RETRY = true;

    /**
     * Maximum number of retry attempts for failed tests
     */
    public static final int MAX_RETRY_COUNT = 2;

    /**
     * Enable or disable screenshot on test failure
     */
    public static final boolean SCREENSHOT_ON_FAILURE = false;

    /**
     * Enable or disable video recording for tests
     */
    public static final boolean VIDEO_RECORDING = false;

    /**
     * Default browser for UI tests (if applicable)
     */
    public static final String DEFAULT_BROWSER = "chrome";

    /**
     * Base URL for the application under test
     */
    public static final String BASE_URL = "http://localhost:8080";

    /**
     * Enable or disable headless mode for browser tests
     */
    public static final boolean HEADLESS_MODE = true;

    /**
     * Default wait timeout in seconds
     */
    public static final int DEFAULT_WAIT_TIMEOUT = 10;

    /**
     * Enable or disable detailed test reporting
     */
    public static final boolean DETAILED_REPORTING = true;

    /**
     * Path to store test reports
     */
    public static final String REPORT_PATH = "target/allure-results";

    /**
     * Path to store test screenshots
     */
    public static final String SCREENSHOT_PATH = "target/screenshots";

    /**
     * Enable or disable database cleanup between tests
     */
    public static final boolean DATABASE_CLEANUP = true;

    /**
     * Test data file path
     */
    public static final String TEST_DATA_PATH = "src/test/resources/test-data/";

    /**
     * Environment name for test execution
     */
    public static final String ENVIRONMENT = "test";

    /**
     * Log level for test execution
     */
    public static final String LOG_LEVEL = "INFO";

    /**
     * Enable or disable API mocking
     */
    public static final boolean ENABLE_API_MOCKING = false;

    /**
     * Mock server port
     */
    public static final int MOCK_SERVER_PORT = 8081;

    /**
     * Checks if parallel execution is enabled
     *
     * @return true if parallel execution is enabled
     */
    public static boolean isParallelExecutionEnabled() {
        return PARALLEL_EXECUTION;
    }

    /**
     * Checks if test retry is enabled
     *
     * @return true if test retry is enabled
     */
    public static boolean isRetryEnabled() {
        return ENABLE_RETRY;
    }

    /**
     * Gets the complete report file path
     *
     * @param fileName the report file name
     * @return complete report file path
     */
    public static String getReportPath(String fileName) {
        return REPORT_PATH + "/" + fileName;
    }

    /**
     * Gets the complete screenshot file path
     *
     * @param fileName the screenshot file name
     * @return complete screenshot file path
     */
    public static String getScreenshotPath(String fileName) {
        return SCREENSHOT_PATH + "/" + fileName;
    }
}
