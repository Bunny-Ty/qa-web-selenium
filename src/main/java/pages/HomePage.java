package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import core.Config;

public class HomePage extends BasePage {

    private static final By PRODUCTS_LINK = By.cssSelector("a[href='/products']");

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
}