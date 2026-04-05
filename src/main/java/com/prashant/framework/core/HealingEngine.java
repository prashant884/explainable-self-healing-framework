package com.prashant.framework.core;

import com.aventstack.extentreports.Status;
import com.prashant.framework.base.DriverFactory;
import com.prashant.framework.models.HealingResult;
import com.prashant.framework.models.LocatorBundle;
import com.prashant.framework.utils.ConfigReader;
import com.prashant.framework.utils.ReportManager;
import com.prashant.framework.utils.ScreenshotUtil;
import com.prashant.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HealingEngine {

    private final WebDriver driver;
    private final int timeout;

    public HealingEngine() {
        this.driver = DriverFactory.getDriver();
        this.timeout = ConfigReader.getInt("explicit.wait");
    }

    public HealingResult findElement(LocatorBundle locatorBundle, boolean clickable) {
        List<String> attempts = new ArrayList<>();
        Exception primaryException;

        try {
            attempts.add("Trying primary locator: " + locatorBundle.getPrimaryLocator());
            WebElement element = clickable
                    ? WaitUtil.waitForClickable(locatorBundle.getPrimaryLocator(), timeout)
                    : WaitUtil.waitForVisible(locatorBundle.getPrimaryLocator(), timeout);

            log(Status.INFO, locatorBundle.getElementName() + " found using primary locator: " + locatorBundle.getPrimaryLocator());
            return new HealingResult(false, true, locatorBundle.getPrimaryLocator(),
                    "Primary locator worked successfully.",
                    "No locator update required.",
                    1.0, element, null);
        } catch (Exception e) {
            primaryException = normalizeException(e);
            attempts.add("Primary locator failed: " + primaryException.getClass().getSimpleName());
            log(Status.WARNING, locatorBundle.getElementName() + " primary locator failed: " + locatorBundle.getPrimaryLocator());
        }

        for (By fallback : locatorBundle.getFallbackLocators()) {
            try {
                attempts.add("Trying fallback locator: " + fallback);
                WebElement element = clickable
                        ? WaitUtil.waitForClickable(fallback, timeout)
                        : WaitUtil.waitForVisible(fallback, timeout);

                String explanation = ExplanationEngine.buildExplanation(primaryException, fallback);
                String recommendation = ExplanationEngine.buildRecommendation(true, fallback);
                String screenshot = ScreenshotUtil.capture(locatorBundle.getElementName().replaceAll("\\s+", "_") + "_healed");
                double confidence = calculateConfidence(fallback);

                log(Status.PASS, locatorBundle.getElementName() + " healed successfully using fallback: " + fallback);
                log(Status.INFO, explanation);
                log(Status.INFO, recommendation);
                log(Status.INFO, "Confidence score: " + confidence);
                log(Status.INFO, "Healing screenshot: " + screenshot);

                return new HealingResult(true, true, fallback, explanation, recommendation, confidence, element, screenshot);
            } catch (Exception ignored) {
                attempts.add("Fallback failed: " + fallback);
            }
        }

        String finalExplanation = ExplanationEngine.buildFailureExplanation(primaryException);
        String recommendation = ExplanationEngine.buildRecommendation(false, null);
        String screenshot = ScreenshotUtil.capture(locatorBundle.getElementName().replaceAll("\\s+", "_") + "_failure");

        log(Status.FAIL, locatorBundle.getElementName() + " could not be healed.");
        log(Status.FAIL, finalExplanation);
        log(Status.FAIL, recommendation);
        log(Status.FAIL, "Attempts: " + String.join(" | ", attempts));
        log(Status.FAIL, "Failure screenshot: " + screenshot);

        return new HealingResult(false, false, null, finalExplanation, recommendation, 0.0, null, screenshot);
    }

    public void type(LocatorBundle locatorBundle, String value) {
        HealingResult result = findElement(locatorBundle, false);
        if (!result.isFound()) {
            throw new NoSuchElementException(locatorBundle.getElementName() + " not found. " + result.getExplanation());
        }
        result.getElement().clear();
        result.getElement().sendKeys(value);
        log(Status.INFO, "Typed value into " + locatorBundle.getElementName() + " using locator: " + result.getUsedLocator());
    }

    public void click(LocatorBundle locatorBundle) {
        HealingResult result = findElement(locatorBundle, true);
        if (!result.isFound()) {
            throw new NoSuchElementException(locatorBundle.getElementName() + " not found. " + result.getExplanation());
        }
        result.getElement().click();
        log(Status.INFO, "Clicked on " + locatorBundle.getElementName() + " using locator: " + result.getUsedLocator());
    }

    public String getText(LocatorBundle locatorBundle) {
        HealingResult result = findElement(locatorBundle, false);
        if (!result.isFound()) {
            throw new NoSuchElementException(locatorBundle.getElementName() + " not found. " + result.getExplanation());
        }
        String text = result.getElement().getText();
        log(Status.INFO, "Captured text from " + locatorBundle.getElementName() + ": " + text);
        return text;
    }

    public boolean isDisplayed(LocatorBundle locatorBundle) {
        HealingResult result = findElement(locatorBundle, false);
        return result.isFound() && result.getElement().isDisplayed();
    }

    private double calculateConfidence(By locator) {
        String locatorValue = locator.toString().toLowerCase();
        if (locatorValue.contains("id:")) {
            return 0.95;
        }
        if (locatorValue.contains("name:")) {
            return 0.90;
        }
        if (locatorValue.contains("cssselector:")) {
            return 0.85;
        }
        if (locatorValue.contains("xpath:")) {
            return 0.75;
        }
        return 0.60;
    }

    private Exception normalizeException(Exception exception) {
        Throwable root = exception;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        if (root instanceof TimeoutException) {
            return (TimeoutException) root;
        }
        if (root instanceof NoSuchElementException) {
            return (NoSuchElementException) root;
        }
        return exception;
    }

    private void log(Status status, String message) {
        System.out.println("[" + status + "] " + message);
        if (ReportManager.getTest() != null) {
            ReportManager.getTest().log(status, message);
        }
    }
}
