package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import core.Config;

public class HomePage extends BasePage {

    private static final By PRODUCTS_LINK = By.cssSelector("a[href='/products']");
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
        return !driver.findElements(DELETE_ACCOUNT_LINK).isEmpty();
    }

    public boolean isLogoutShown() {
        return !driver.findElements(LOGOUT_LINK).isEmpty();
    }

    public String loggedInText() {
        return driver.findElement(LOGGED_IN_AS).getText().trim();
    }

    public AccountPage deleteAccount() {
        clickAndWaitForUrl(DELETE_ACCOUNT_LINK, "/delete_account");
        return new AccountPage(driver);
    }
}