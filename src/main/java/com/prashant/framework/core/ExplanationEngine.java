package com.prashant.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

public final class ExplanationEngine {

    private ExplanationEngine() {
    }

    public static String buildExplanation(Exception primaryException, By successfulFallback) {
        if (primaryException instanceof NoSuchElementException) {
            return "Primary locator failed because the element was not found. Most likely reason: DOM attribute changed or locator became unstable. " +
                    "Framework recovered using alternate locator: " + successfulFallback;
        }
        if (primaryException instanceof TimeoutException) {
            return "Primary locator timed out. Most likely reason: synchronization issue, slow page load, or delayed rendering. " +
                    "Framework recovered using alternate locator: " + successfulFallback;
        }
        if (primaryException instanceof StaleElementReferenceException) {
            return "Primary locator became stale after page refresh or DOM re-render. Framework recovered using alternate locator: " + successfulFallback;
        }
        return "Primary locator failed with exception: " + primaryException.getClass().getSimpleName() +
                ". Framework recovered using alternate locator: " + successfulFallback;
    }

    public static String buildFailureExplanation(Exception primaryException) {
        if (primaryException instanceof NoSuchElementException) {
            return "Element was not found with the primary locator, and all fallback locators also failed. Root cause is likely a major DOM/UI change or wrong page state.";
        }
        if (primaryException instanceof TimeoutException) {
            return "Element was not visible/clickable within wait time, and fallback locators also failed. Root cause is likely page load delay, sync issue, or broken functionality.";
        }
        return "Primary locator and all fallback locators failed. Investigate locator strategy, page state, and application behavior.";
    }

    public static String buildRecommendation(boolean healed, By usedLocator) {
        if (healed) {
            return "Recommended permanent fix: update the Page Object primary locator to a more stable locator like: " + usedLocator;
        }
        return "Recommended action: inspect the DOM, validate page behavior, and update locator strategy after confirming the element still exists.";
    }
}
