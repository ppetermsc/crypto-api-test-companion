package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

/**
 * TestNG listener for custom test execution handling
 * Provides additional logging and reporting capabilities
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class TestListener implements ITestListener {

    /**
     * Called when a test method starts execution
     *
     * @param result the test result object
     */
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🚀 Starting test: " + result.getName());
        System.out.println("   Description: " + result.getMethod().getDescription());
    }

    /**
     * Called when a test method completes successfully
     *
     * @param result the test result object
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test PASSED: " + result.getName());
        System.out.println("   Execution time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    /**
     * Called when a test method fails
     *
     * @param result the test result object
     */
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test FAILED: " + result.getName());
        System.out.println("   Error: " + result.getThrowable().getMessage());

        // Log additional failure details
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
    }

    /**
     * Called when a test method is skipped
     *
     * @param result the test result object
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭️ Test SKIPPED: " + result.getName());
        System.out.println("   Reason: " + (result.getThrowable() != null ?
                result.getThrowable().getMessage() : "Unknown"));
    }

    /**
     * Called when a test method fails but within success percentage
     *
     * @param result the test result object
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("⚠️ Test failed but within success percentage: " + result.getName());
    }

    /**
     * Called when the test suite starts execution
     *
     * @param context the test context object
     */
    @Override
    public void onStart(ITestContext context) {
        System.out.println("🎬 ===== TEST SUITE STARTED =====");
        System.out.println("   Suite: " + context.getName());
        System.out.println("   Start time: " + context.getStartDate());
    }

    /**
     * Called when the test suite finishes execution
     *
     * @param context the test context object
     */
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 ===== TEST SUITE FINISHED =====");
        System.out.println("   Suite: " + context.getName());
        System.out.println("   Total tests: " + context.getAllTestMethods().length);
        System.out.println("   Passed: " + context.getPassedTests().size());
        System.out.println("   Failed: " + context.getFailedTests().size());
        System.out.println("   Skipped: " + context.getSkippedTests().size());
        System.out.println("   End time: " + context.getEndDate());
        System.out.println("   Duration: " + (context.getEndDate().getTime() - context.getStartDate().getTime()) + "ms");
    }
}
