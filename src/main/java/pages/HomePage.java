package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import core.Config;

public class HomePage extends BasePage {

    private static final By PRODUCTS_LINK = By.cssSelector("a[href='/products']");
    private static final By LOGIN_LINK = By.cssSelector("ul.navbar-nav a[href='/login']");
    private static final By LOGOUT_LINK = By.cssSelector("a[href='/logout']");
    private static final By DELETE_ACCOUNT_LINK = By.cssSelector("a[href='/delete_account']");
    private static final By LOGGED_IN_AS = By.xpath(
            "//ul[contains(@class,'navbar-nav')]//li[contains(normalize-space(.),'Logged in as')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.baseUrl());
        return this;
    }

    public ProductsPage goToProducts() {
        clickAndWaitForUrl(PRODUCTS_LINK, "/products");
        return new ProductsPage(driver);
    }

    public boolean isLoggedIn() {
        waitForHeader();
        return !driver.findElements(DELETE_ACCOUNT_LINK).isEmpty();
    }

    public boolean isLogoutShown() {
        waitForHeader();
        return !driver.findElements(LOGOUT_LINK).isEmpty();
    }

    public String loggedInText() {
        return driver.findElement(LOGGED_IN_AS).getText().trim();
    }

    public boolean isLoginLinkShown() {
        waitForHeader();
        return !driver.findElements(LOGIN_LINK).isEmpty();
    }

    public boolean isLoggedInTextShown() {
        waitForHeader();
        return !driver.findElements(LOGGED_IN_AS).isEmpty();
    }

    public AccountPage deleteAccount() {
        clickAndWaitForUrl(DELETE_ACCOUNT_LINK, "/delete_account");
        return new AccountPage(driver);
    }

    public LoginPage logout() {
        clickAndWaitForUrl(LOGOUT_LINK, "/login");
        return new LoginPage(driver).waitUntilLoaded();
    }

    private void waitForHeader() {
        wait.until((ExpectedCondition<Boolean>) d ->
                !d.findElements(DELETE_ACCOUNT_LINK).isEmpty() || !d.findElements(LOGIN_LINK).isEmpty());
    }
}