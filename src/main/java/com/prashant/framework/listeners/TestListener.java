package com.prashant.framework.listeners;

import com.prashant.framework.utils.ReportManager;
import com.prashant.framework.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // no-op
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportManager.getTest().pass("Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtil.capture(result.getMethod().getMethodName() + "_listener_failure");
        ReportManager.getTest().fail(result.getThrowable());
        ReportManager.getTest().info("Failure screenshot: " + screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportManager.getTest().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.getExtentReports().flush();
        ReportManager.unload();
    }
}
