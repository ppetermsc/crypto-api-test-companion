package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

/**
 * TestNG listener for custom test execution handling.
 * Provides additional logging and reporting capabilities.
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class TestListener implements ITestListener {
    
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("🚀 Starting test: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        logger.info("✅ Test PASSED: {} ({} ms)", result.getName(), duration);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ Test FAILED: {}", result.getName());
        if (result.getThrowable() != null) {
            logger.error("Failure reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⏭️ Test SKIPPED: {}", result.getName());
        if (result.getThrowable() != null) {
            logger.warn("Skip reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("🎬 ===== TEST SUITE STARTED: {} =====", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("🏁 ===== TEST SUITE FINISHED: {} =====", context.getName());
        logger.info("Results - Total: {}, Passed: {}, Failed: {}, Skipped: {}",
                context.getAllTestMethods().length,
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
    }
}
