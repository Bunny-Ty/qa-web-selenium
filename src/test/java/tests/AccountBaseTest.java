package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import core.TestData;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

public abstract class AccountBaseTest extends BaseTest {

    protected String email;
    protected String password;

    @BeforeMethod
    public void prepareCredentials() {
        email = TestData.uniqueEmail();
        password = TestData.password();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAccountIfLoggedIn() {
        if (driver == null) {
            return;
        }
        try {
            HomePage home = new HomePage(driver).open();
            if (home.isLoggedIn()) {
                home.deleteAccount();
            }
        } catch (RuntimeException e) {
            System.err.println("Cleanup failed for " + email + ": " + e.getMessage());
        }
    }

    protected AccountPage registerNewAccount() {
        return new LoginPage(driver).open()
                .signUp(TestData.NAME, email)
                .fillValidAccount(password)
                .submit();
    }
}