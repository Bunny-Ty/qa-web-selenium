package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import core.TestData;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;

public abstract class AccountBaseTest extends BaseTest {

    protected String email;
    protected String password;
    private boolean accountExists;

    @BeforeMethod
    public void prepareCredentials() {
        email = TestData.uniqueEmail();
        password = TestData.password();
        accountExists = false;
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAccountIfExists() {
        if (driver == null) {
            return;
        }
        try {
            HomePage home = new HomePage(driver).open();
            if (!home.isLoggedIn() && accountExists) {
                home = new LoginPage(driver).open().login(email, password);
            }
            if (home.isLoggedIn()) {
                home.deleteAccount();
            }
        } catch (RuntimeException e) {
            System.err.println("Cleanup failed for " + email + ": " + e.getMessage());
        }
    }

    protected AccountPage registerNewAccount() {
        SignupPage form = new LoginPage(driver).open()
                .signUp(TestData.NAME, email)
                .fillValidAccount(password);
        accountExists = true;
        return form.submit();
    }

    protected void markAccountDeleted() {
        accountExists = false;
    }
}