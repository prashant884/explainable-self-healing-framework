package com.prashant.framework.pages;

import com.prashant.framework.core.HealingEngine;
import com.prashant.framework.models.LocatorBundle;
import org.openqa.selenium.By;

public class LoginPage {

    private final HealingEngine healingEngine = new HealingEngine();

    private final LocatorBundle usernameField = LocatorBundle.of(
            "Username Field",
            By.name("wrongusername"),
            By.cssSelector("input[name='username']"),
            By.xpath("//input[@name='username']")
    );

    private final LocatorBundle passwordField = LocatorBundle.of(
            "Password Field",
            By.name("password"),
            By.cssSelector("input[name='password']"),
            By.xpath("//input[@name='password']")
    );

    private final LocatorBundle loginButton = LocatorBundle.of(
            "Login Button",
            By.cssSelector("input.button"),
            By.xpath("//input[@value='Log In']"),
            By.xpath("//div[@class='login']//input[@type='submit']")
    );

    private final LocatorBundle accountsOverviewHeader = LocatorBundle.of(
            "Accounts Overview Header",
            By.xpath("//h1[contains(text(),'Accounts Overview') or contains(text(),'Accounts Overview')]") ,
            By.xpath("//div[@id='showOverview']//h1"),
            By.cssSelector("div#showOverview h1")
    );

    public void login(String username, String password) {
        healingEngine.type(usernameField, username);
        healingEngine.type(passwordField, password);
        healingEngine.click(loginButton);
    }

    public boolean isAccountsOverviewDisplayed() {
        return healingEngine.isDisplayed(accountsOverviewHeader);
    }

    public String getAccountsOverviewText() {
        return healingEngine.getText(accountsOverviewHeader);
    }
}
