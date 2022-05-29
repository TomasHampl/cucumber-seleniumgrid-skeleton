package cz.tomas.test.shared;


import com.google.inject.Singleton;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Supplier;

@Singleton
public class WebDriverHelper {

    private final Logger logger = LoggerFactory.getLogger(WebDriverHelper.class);

    /**
     * Helper method that attempts to take a screenshot of the page where the web-driver is currently located.
     * @param webDriver is an instance of {@link WebDriver} used to take the screenshot. If all goes well it'll be found
     *                  in '{@code target/screenshot_timestamp.png}'
     */
    public void takeScreenshot(WebDriver webDriver){
        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
        File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
        this.processScreenshot(screenShot);
    }

    /**
     * Helper method that attempts to take a screenshot of the provided {@code WebElement}.
     * @param webElement is an instance of the {@link WebElement} that contains the 'screenshot' capability. If all goes well it'll be found
     *      *                  in '{@code target/screenshot_timestamp.png}'
     */
    public void takeScreenshot(WebElement webElement){
        File screenshot = webElement.getScreenshotAs(OutputType.FILE);
        this.processScreenshot(screenshot);
    }

    private void processScreenshot(File screenshot){
        File destinationFile = new File("target/screenshot_" + getTimestamp() + ".png");
        try {
            FileHandler.copy(screenshot,destinationFile);
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "Screenshot capture target/screenshot_" + getTimestamp() + ".png failed - no screenshot will not be recorded";
            logger.error(errorMessage);
        }
    }

    private long getTimestamp(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

}
