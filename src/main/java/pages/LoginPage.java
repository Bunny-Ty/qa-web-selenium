package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import core.AdPopup;
import core.Config;

public class LoginPage extends BasePage {

    private static final By LOGIN_EMAIL = qa("login-email");
    private static final By LOGIN_PASSWORD = qa("login-password");
    private static final By LOGIN_BUTTON = qa("login-button");
    private static final By LOGIN_ERROR = By.xpath("//form[.//*[@data-qa='login-button']]//p");
    private static final By SIGNUP_NAME = qa("signup-name");
    private static final By SIGNUP_EMAIL = qa("signup-email");
    private static final By SIGNUP_BUTTON = qa("signup-button");
    private static final By SIGNUP_ERROR = By.xpath("//form[.//*[@data-qa='signup-button']]//p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.baseUrl() + "login");
        AdPopup.dismissIfPresent(driver);
        return waitUntilLoaded();
    }

    public LoginPage waitUntilLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNUP_BUTTON));
        } catch (TimeoutException e) {
            throw new IllegalStateException("Login page did not load. URL=" + driver.getCurrentUrl()
                    + ", title=" + driver.getTitle(), e);
        }
        return this;
    }

    public LoginPage enterSignupName(String name) {
        type(SIGNUP_NAME, name);
        return this;
    }

    public LoginPage enterSignupEmail(String email) {
        type(SIGNUP_EMAIL, email);
        return this;
    }

    public void submitSignup() {
        driver.findElement(SIGNUP_BUTTON).click();
    }

    public SignupPage signUp(String name, String email) {
        enterSignupName(name).enterSignupEmail(email);
        clickAndWaitForUrl(SIGNUP_BUTTON, "/signup");
        return new SignupPage(driver).waitUntilOpen();
    }

    public LoginPage signUpExpectingError(String name, String email) {
        enterSignupName(name).enterSignupEmail(email);
        clickAndWaitForVisible(SIGNUP_BUTTON, SIGNUP_ERROR);
        return this;
    }

    public HomePage login(String email, String password) {
        type(LOGIN_EMAIL, email);
        type(LOGIN_PASSWORD, password);
        clickAndWaitForHome(LOGIN_BUTTON);
        return new HomePage(driver);
    }

    public LoginPage loginExpectingError(String email, String password) {
        type(LOGIN_EMAIL, email);
        type(LOGIN_PASSWORD, password);
        clickAndWaitForVisible(LOGIN_BUTTON, LOGIN_ERROR);
        return this;
    }

    public String signupError() {
        return driver.findElement(SIGNUP_ERROR).getText().trim();
    }

    public String signupErrorColor() {
        return driver.findElement(SIGNUP_ERROR).getCssValue("color");
    }

    public String loginError() {
        return driver.findElement(LOGIN_ERROR).getText().trim();
    }

    public String loginErrorColor() {
        return driver.findElement(LOGIN_ERROR).getCssValue("color");
    }

    public boolean isLoginAndSignupFormDisplayed() {
        return driver.findElement(LOGIN_BUTTON).isDisplayed()
                && driver.findElement(SIGNUP_BUTTON).isDisplayed();
    }
}