package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String HOME_URL = "https://automationexercise.com/";
    private static final By PRODUCTS_LINK = By.cssSelector("a[href='/products']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(HOME_URL);
        return this;
    }

    public ProductsPage goToProducts() {
        clickAndWaitForUrl(PRODUCTS_LINK, "/products");
        return new ProductsPage(driver);
    }
}