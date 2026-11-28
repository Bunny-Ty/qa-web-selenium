package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.Config;
import core.TestData;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends AccountBaseTest {

    private static final String RED = "rgba(255, 0, 0, 1)";
    private static final String LOGIN_ERROR = "Your email or password is incorrect!";

    private LoginPage registerAndLogout() {
        return registerNewAccount().continueToHome().logout();
    }

    @Test(description = "TC-LOGIN-001: Login succeeds with the correct email and password")
    public void loginSucceedsWithCorrectCredentials() {
        HomePage home = registerAndLogout().login(email, password);
        Assert.assertEquals(home.currentUrl().replace("#google_vignette", ""), Config.baseUrl(),
                "The home page should be displayed");
        Assert.assertTrue(home.isLogoutShown(), "Logout should be shown in the header");
        Assert.assertTrue(home.isLoggedIn(), "Delete Account should be shown in the header");
        Assert.assertTrue(home.loggedInText().contains(TestData.NAME),
                "Logged in text should contain the name, but is: " + home.loggedInText());
    }

    @Test(description = "TC-LOGIN-002: Login fails with a wrong password")
    public void loginFailsWithWrongPassword() {
        LoginPage login = registerAndLogout();
        login.loginExpectingError(email, "wrong-" + password);
        Assert.assertEquals(login.loginError(), LOGIN_ERROR);
        Assert.assertEquals(login.loginErrorColor(), RED, "The error should be red");
        Assert.assertFalse(new HomePage(driver).isLoggedIn(), "The user should not be logged in");
    }

    @Test(description = "TC-LOGIN-003: Login fails with an email that is not registered")
    public void loginFailsWithUnregisteredEmail() {
        LoginPage login = new LoginPage(driver).open();
        login.loginExpectingError(TestData.uniqueEmail(), password);
        Assert.assertEquals(login.loginError(), LOGIN_ERROR);
        Assert.assertEquals(login.loginErrorColor(), RED, "The error should be red");
        Assert.assertFalse(new HomePage(driver).isLoggedIn(), "The user should not be logged in");
    }

    @DataProvider(name = "emptyLoginFields")
    public Object[][] emptyLoginFields() {
        return new Object[][] {
                {"", TestData.password(), "login-email"},
                {TestData.uniqueEmail(), "", "login-password"},
                {"", "", "login-email"}
        };
    }

    @Test(dataProvider = "emptyLoginFields",
            description = "TC-LOGIN-004: Login is blocked when the email or the password is empty")
    public void loginBlockedWhenEmailOrPasswordEmpty(String loginEmail, String loginPassword,
                                                     String emptyField) {
        LoginPage login = new LoginPage(driver).open();
        login.fillLogin(loginEmail, loginPassword);
        login.submitLogin();
        Assert.assertEquals(login.activeFieldDataQa(), emptyField, "Focus should be on the empty field");
        Assert.assertFalse(login.validationMessage(emptyField).isEmpty(),
                "The empty field should show a validation message");
        Assert.assertTrue(login.currentUrl().endsWith("/login"),
                "The page should stay on /login but the URL is " + login.currentUrl());
        Assert.assertFalse(new HomePage(driver).isLoggedIn(), "The user should not be logged in");
    }

    @Test(description = "TC-LOGIN-005: Logout returns to the login page")
    public void logoutReturnsToLoginPage() {
        LoginPage login = registerNewAccount().continueToHome().logout();
        Assert.assertEquals(login.currentUrl().replace("#google_vignette", ""),
                Config.baseUrl() + "login", "The login page should be displayed");
        HomePage header = new HomePage(driver);
        Assert.assertTrue(header.isLoginLinkShown(), "Signup / Login should be shown in the header");
        Assert.assertFalse(header.isLogoutShown(), "Logout should not be shown");
        Assert.assertFalse(header.isLoggedInTextShown(), "Logged in as should not be shown");
    }
}