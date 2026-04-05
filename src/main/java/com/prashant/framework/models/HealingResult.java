package com.prashant.framework.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HealingResult {

    private final boolean healed;
    private final boolean found;
    private final By usedLocator;
    private final String explanation;
    private final String recommendation;
    private final double confidenceScore;
    private final WebElement element;
    private final String screenshotPath;

    public HealingResult(boolean healed,
                         boolean found,
                         By usedLocator,
                         String explanation,
                         String recommendation,
                         double confidenceScore,
                         WebElement element,
                         String screenshotPath) {
        this.healed = healed;
        this.found = found;
        this.usedLocator = usedLocator;
        this.explanation = explanation;
        this.recommendation = recommendation;
        this.confidenceScore = confidenceScore;
        this.element = element;
        this.screenshotPath = screenshotPath;
    }

    public boolean isHealed() {
        return healed;
    }

    public boolean isFound() {
        return found;
    }

    public By getUsedLocator() {
        return usedLocator;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public WebElement getElement() {
        return element;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }
}
