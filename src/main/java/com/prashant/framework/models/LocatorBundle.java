package com.prashant.framework.models;

import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

public class LocatorBundle {

    private final String elementName;
    private final By primaryLocator;
    private final List<By> fallbackLocators;

    public LocatorBundle(String elementName, By primaryLocator, List<By> fallbackLocators) {
        this.elementName = elementName;
        this.primaryLocator = primaryLocator;
        this.fallbackLocators = fallbackLocators;
    }

    public static LocatorBundle of(String elementName, By primaryLocator, By... fallbackLocators) {
        return new LocatorBundle(elementName, primaryLocator, Arrays.asList(fallbackLocators));
    }

    public String getElementName() {
        return elementName;
    }

    public By getPrimaryLocator() {
        return primaryLocator;
    }

    public List<By> getFallbackLocators() {
        return fallbackLocators;
    }
}
