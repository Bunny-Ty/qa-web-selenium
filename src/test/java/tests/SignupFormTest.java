package tests;

import core.TestData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class SignupFormTest extends BaseTest {

    @Test(description = "TC-REG-002: Signup is blocked when both Name and Email are empty")
    public void signupBlockedWhenNameAndEmailEmpty() {
        LoginPage login = new LoginPage(driver).open();
        login.submitSignup();
        assertBubbleOn(login, "signup-name");
    }

    @DataProvider(name = "partialSignup")
    public Object[][] partialSignup() {
        return new Object[][] {
                {TestData.NAME, "", "signup-email"},
                {"", TestData.uniqueEmail(), "signup-name"}
        };
    }

    @Test(dataProvider = "partialSignup", description = "TC-REG-003: Signup is blocked when either Name or Email is empty")
    public void signupBlockedWhenOnlyOneFieldFilled(String name, String email, String emptyField) {
        LoginPage login = new LoginPage(driver).open();
        login.enterSignupName(name).enterSignupEmail(email);
        login.submitSignup();
        assertBubbleOn(login, emptyField);
    }

    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][] {{"test"}, {"test@"}};
    }

    @Test(dataProvider = "invalidEmails", description = "TC-REG-004: Signup is blocked when Email is invalid")
    public void signupBlockedForInvalidEmail(String invalidEmail) {
        LoginPage login = new LoginPage(driver).open();
        login.enterSignupName(TestData.NAME).enterSignupEmail(invalidEmail);
        login.submitSignup();
        assertBubbleOn(login, "signup-email");
    }

    private void assertBubbleOn(LoginPage login, String dataQa) {
        Assert.assertEquals(login.activeFieldDataQa(), dataQa, "Focus should be on the invalid field");
        Assert.assertFalse(login.validationMessage(dataQa).isEmpty(),
                "The field should show a validation message");
        Assert.assertTrue(login.currentUrl().endsWith("/login"),
                "The page should stay on /login but the URL is " + login.currentUrl());
    }

}
