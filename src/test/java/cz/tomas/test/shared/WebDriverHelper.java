package cz.tomas.test.shared;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Singleton
public class WebDriverHelper {

    private final Logger logger = LoggerFactory.getLogger(WebDriverHelper.class);

    @Inject
    private StateHolder stateHolder;

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
     * Uses the provided xpath expression to search in the DOM of the active page (where the driver is atm)
     * @param driver is an instance of {@link WebDriver}
     * @param xpathExpression is a xpath expression that we'll feed to the driver
     * @return instance of found {@code WebElemnet} or {@code null} if nothing was found
     */
    public WebElement findElementByXpath(WebDriver driver, String xpathExpression){
        try {
            waitASec(driver);
            String currentUrl = driver.getCurrentUrl();
            return driver.findElement(By.xpath(xpathExpression));
        } catch (NoSuchElementException noSuchElementException){
            Supplier<String> errorMessage = () -> "Element could not be found by provided xpath expression: " + xpathExpression;
            logger.error(errorMessage);
        }
        return null;
    }

    /**
     * Used the provided elementId to search the DOM for the element with provided ID.
     * @param driver is an instance of {@link WebDriver}
     * @param elementId is the HTML 'id' attribute
     * @return instance of found {@link WebElement} or {@code null} if nothing was found
     */
    public WebElement findElementById(WebDriver driver, String elementId){
        try {
            return driver.findElement(By.id(elementId));
        } catch (NoSuchElementException noSuchElementException){
            Supplier<String> errorMessage = () -> "Element could not be found by provided elementId: " + elementId;
            logger.error(errorMessage);
        }
        return null;
    }

    public WebElement findElementByText(WebDriver driver, String text){
        try {
            return driver.findElement(By.partialLinkText(text));
        } catch (NoSuchElementException noSuchElementException){
            Supplier<String> errorMessage = () -> "Element could not be found by provided text: " + text;
            logger.error(errorMessage);
        }
        return null;
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
            Supplier<String> errorMessage = () -> "Screenshot capture target/screenshot_" + getTimestamp() + ".png failed - no screenshot will be recorded";
            logger.error(errorMessage);
        }
    }

    private long getTimestamp(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    private void waitASec(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

}
