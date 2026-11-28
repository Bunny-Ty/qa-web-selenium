package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.Config;
import core.TestData;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;

public class AccountRulesTest extends AccountBaseTest {

    private static final String RED = "rgba(255, 0, 0, 1)";

    @Test(description = "TC-REG-005: Signup with an already registered email shows an error")
    public void signupWithExistingEmailShowsError() {
        LoginPage login = registerNewAccount().continueToHome().logout();
        login.signUpExpectingError(TestData.NAME, email);
        Assert.assertEquals(login.signupError(), "Email Address already exist!");
        Assert.assertEquals(login.signupErrorColor(), RED, "The error should be red");
        Assert.assertTrue(login.currentUrl().contains("/signup"),
                "URL should contain /signup but is " + login.currentUrl());
        Assert.assertTrue(login.isLoginAndSignupFormDisplayed(),
                "The login and signup page should stay displayed");
    }

    @DataProvider(name = "requiredFields")
    public Object[][] requiredFields() {
        return new Object[][] {
                {"password"}, {"first_name"}, {"last_name"}, {"address"},
                {"state"}, {"city"}, {"zipcode"}, {"mobile_number"}
        };
    }

    @Test(dataProvider = "requiredFields",
            description = "TC-REG-007: Account creation is blocked when a required field is empty")
    public void accountCreationBlockedWhenRequiredFieldEmpty(String field) {
        SignupPage form = new LoginPage(driver).open()
                .signUp(TestData.NAME, email)
                .fillValidAccount(password);
        form.clearField(field);
        form.submitExpectingValidation();
        Assert.assertEquals(form.activeFieldDataQa(), field, "Focus should be on the empty field");
        Assert.assertFalse(form.validationMessage(field).isEmpty(),
                "The empty field should show a validation message");
        Assert.assertTrue(form.currentUrl().contains("/signup"),
                "The page should stay on /signup but the URL is " + form.currentUrl());
    }

    @Test(description = "TC-REG-011: Delete the account; the deleted account can no longer log in")
    public void deletedAccountCanNoLongerLogIn() {
        HomePage home = registerNewAccount().continueToHome();
        AccountPage deleted = home.deleteAccount();
        markAccountDeleted();
        Assert.assertTrue(deleted.currentUrl().contains("/delete_account"),
                "URL should contain /delete_account but is " + deleted.currentUrl());
        Assert.assertTrue(deleted.deletedHeading().equalsIgnoreCase("Account Deleted!"),
                "Unexpected heading: " + deleted.deletedHeading());
        Assert.assertTrue(deleted.pageText().contains("Your account has been permanently deleted!"),
                "Deleted message not found");
        HomePage backHome = deleted.continueToHome();
        Assert.assertEquals(backHome.currentUrl().replace("#google_vignette", ""), Config.baseUrl(),
                "The home page should be displayed");
        LoginPage login = new LoginPage(driver).open();
        login.loginExpectingError(email, password);
        Assert.assertEquals(login.loginError(), "Your email or password is incorrect!");
        Assert.assertEquals(login.loginErrorColor(), RED, "The error should be red");
        Assert.assertFalse(new HomePage(driver).isLoggedIn(), "The user should not be logged in");
    }
}