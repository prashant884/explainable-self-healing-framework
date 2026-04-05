package com.prashant.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ReportManager {

    private static final ExtentReports EXTENT_REPORTS = createInstance();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ReportManager() {
    }

    private static ExtentReports createInstance() {
        try {
            Path reportsDir = Paths.get("reports");
            Files.createDirectories(reportsDir);
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportsDir.resolve("ExtentReport.html").toString());
            sparkReporter.config().setReportName("Explainable Self-Healing Framework Report");
            sparkReporter.config().setDocumentTitle("Automation Execution Report");

            ExtentReports extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Framework", "Explainable Self-Healing Automation Framework");
            extentReports.setSystemInfo("Author", "Prashant Kumar");
            return extentReports;
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize report", e);
        }
    }

    public static ExtentReports getExtentReports() {
        return EXTENT_REPORTS;
    }

    public static void createTest(String testName) {
        TEST.set(EXTENT_REPORTS.createTest(testName));
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static void unload() {
        TEST.remove();
    }
}
