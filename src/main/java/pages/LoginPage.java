package pages;

import core.AdPopup;
import core.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By SIGNUP_NAME = By.cssSelector("[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL = By.cssSelector("[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON = By.cssSelector("[data-qa='signup-button']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.baseUrl() + "login");
        AdPopup.dismissIfPresent(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNUP_BUTTON));
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

    public String activeFieldDataQa() {
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return document.activeElement.getAttribute('data-qa')");
    }

    public String validationMessage(String dataQa) {
        return driver.findElement(By.cssSelector("[data-qa='" + dataQa + "']"))
                .getDomProperty("validationMessage");
    }

}
