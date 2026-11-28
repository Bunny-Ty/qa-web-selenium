package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignupPage extends BasePage {

    private static final By NAME = qa("name");
    private static final By EMAIL = qa("email");
    private static final By PASSWORD = qa("password");
    private static final By CREATE_ACCOUNT = qa("create-account");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public SignupPage waitUntilOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CREATE_ACCOUNT));
        return this;
    }

    public String nameValue() {
        return driver.findElement(NAME).getDomProperty("value");
    }

    public String emailValue() {
        return driver.findElement(EMAIL).getDomProperty("value");
    }

    public SignupPage fillValidAccount(String password) {
        driver.findElement(By.id("id_gender2")).click();
        type(PASSWORD, password);
        select("days", "1");
        select("months", "January");
        select("years", "2000");
        type(qa("first_name"), "Test");
        type(qa("last_name"), "User");
        type(qa("address"), "123 Test Street");
        select("country", "Singapore");
        type(qa("state"), "Test");
        type(qa("city"), "Test");
        type(qa("zipcode"), "123456");
        type(qa("mobile_number"), "123456789");
        return this;
    }

    public AccountPage submit() {
        clickAndWaitForUrl(CREATE_ACCOUNT, "/account_created");
        return new AccountPage(driver);
    }

    private void select(String id, String visibleText) {
        new Select(driver.findElement(By.id(id))).selectByVisibleText(visibleText);
    }
}