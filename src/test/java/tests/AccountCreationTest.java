package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.Config;
import core.TestData;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;

public class AccountCreationTest extends AccountBaseTest {

    @Test(description = "TC-REG-001: Signup with a valid name and a new email opens the account form")
    public void signupOpensAccountForm() {
        SignupPage form = new LoginPage(driver).open().signUp(TestData.NAME, email);
        Assert.assertTrue(form.currentUrl().contains("/signup"),
                "The account form should be open but the URL is " + form.currentUrl());
        Assert.assertEquals(form.nameValue(), TestData.NAME, "Name should be filled in");
        Assert.assertEquals(form.emailValue(), email, "Email should be filled in");
    }

    @Test(description = "TC-REG-009: Create an account with valid data")
    public void createAccountWithValidData() {
        AccountPage created = registerNewAccount();
        Assert.assertTrue(created.currentUrl().contains("/account_created"),
                "URL should contain /account_created but is " + created.currentUrl());
        Assert.assertTrue(created.createdHeading().equalsIgnoreCase("Account Created!"),
                "Unexpected heading: " + created.createdHeading());
        String text = created.pageText();
        Assert.assertTrue(text.contains("Congratulations! Your new account has been successfully created!"),
                "Congratulations message not found");
        Assert.assertTrue(text.contains("You can now take advantage of member privileges "
                + "to enhance your online shopping experience with us."), "Member privileges message not found");
        Assert.assertTrue(created.isContinueDisplayed(), "Continue button should be displayed");
    }

    @Test(description = "TC-REG-010: Continue after account creation shows the logged-in state")
    public void continueShowsLoggedInState() {
        HomePage home = registerNewAccount().continueToHome();
        Assert.assertEquals(home.currentUrl().replace("#google_vignette", ""), Config.baseUrl(),
                "The home page should be displayed");
        Assert.assertTrue(home.isLogoutShown(), "Logout should be shown in the header");
        Assert.assertTrue(home.isLoggedIn(), "Delete Account should be shown in the header");
        Assert.assertTrue(home.loggedInText().contains(TestData.NAME),
                "Logged in text should contain the name, but is: " + home.loggedInText());
    }
}