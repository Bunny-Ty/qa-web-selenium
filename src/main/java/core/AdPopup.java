package core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class AdPopup {

    private static final By CLOSE = By.xpath("//*[normalize-space(text())='Close']");
    private static final int MIN_FRAME_WIDTH = 500;

    private AdPopup() {
    }

    public static boolean isShowing(WebDriver driver) {
        return driver.getCurrentUrl().contains("#google_vignette");
    }

    public static boolean dismissIfPresent(WebDriver driver) {
        if (!isShowing(driver)) {
            return false;
        }
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
            if (!frame.isDisplayed() || frame.getSize().getWidth() < MIN_FRAME_WIDTH) {
                continue;
            }
            try {
                driver.switchTo().frame(frame);
                List<WebElement> close = driver.findElements(CLOSE);
                if (!close.isEmpty() && close.get(0).isDisplayed()) {
                    close.get(0).click();
                    return true;
                }
            } finally {
                driver.switchTo().defaultContent();
            }
        }
        return false;
    }
}