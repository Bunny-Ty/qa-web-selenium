package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage extends BasePage {

    private static final By CREATED_HEADING = qa("account-created");
    private static final By DELETED_HEADING = qa("account-deleted");
    private static final By CONTINUE_BUTTON = qa("continue-button");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public String createdHeading() {
        return headingText(CREATED_HEADING);
    }

    public String deletedHeading() {
        return headingText(DELETED_HEADING);
    }

    public boolean isContinueDisplayed() {
        return driver.findElement(CONTINUE_BUTTON).isDisplayed();
    }

    public String pageText() {
        return driver.findElement(By.tagName("body")).getText().replaceAll("[\\s\\u00A0]+", " ");
    }

    public HomePage continueToHome() {
        clickAndWaitForHome(CONTINUE_BUTTON);
        return new HomePage(driver);
    }

    private String headingText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }
}