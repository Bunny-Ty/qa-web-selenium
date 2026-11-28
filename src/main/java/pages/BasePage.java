package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.AdPopup;
import core.Config;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected static By qa(String dataQa) {
        return By.cssSelector("[data-qa='" + dataQa + "']");
    }

    protected void clickAndWaitForUrl(By locator, String expectedUrlPart) {
        clickAndWait(locator, ExpectedConditions.urlContains(expectedUrlPart));
    }

    protected void clickAndWaitForHome(By locator) {
        String home = Config.baseUrl();
        clickAndWait(locator, d -> d.getCurrentUrl().replace("#google_vignette", "").equals(home));
    }

    private void clickAndWait(By locator, ExpectedCondition<Boolean> arrived) {
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.or(arrived, ExpectedConditions.urlContains("#google_vignette")));
        AdPopup.dismissIfPresent(driver);
        wait.until(arrived);
    }

    protected void type(By locator, String text) {
        WebElement field = driver.findElement(locator);
        field.clear();
        if (!text.isEmpty()) {
            field.sendKeys(text);
        }
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}