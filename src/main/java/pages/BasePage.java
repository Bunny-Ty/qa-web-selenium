package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.AdPopup;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void clickAndWaitForUrl(By locator, String expectedUrlPart) {
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains(expectedUrlPart),
                ExpectedConditions.urlContains("#google_vignette")));
        AdPopup.dismissIfPresent(driver);
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}