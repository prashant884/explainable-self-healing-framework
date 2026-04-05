package com.prashant.framework.tests;

import com.prashant.framework.base.BaseTest;
import com.prashant.framework.pages.LoginPage;
import com.prashant.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.prashant.framework.listeners.TestListener;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    @Test(description = "Validate login with explainable self-healing locators")
    public void verifyLoginWithHealingFramework() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));

        Assert.assertTrue(loginPage.isAccountsOverviewDisplayed(), "Accounts Overview header is not displayed.");
        Assert.assertTrue(loginPage.getAccountsOverviewText().contains("Accounts Overview"),
                "Login may have failed because Accounts Overview text was not found.");
    }
}
