package cz.tomas.test.shared;

import com.google.inject.Inject;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import jakarta.inject.Singleton;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.function.Supplier;

@Singleton
public class WebDriverInit {

    private static final String LOCAL_GRID = "http://172.17.0.1:4444";
    private final Logger logger = LoggerFactory.getLogger(WebDriverInit.class);

    @Inject
    private GridHelper gridHelper;

    @Inject
    private MavenPropertiesReader mavenPropertiesReader;

    public WebDriver getDriver(){
        if (isGridUp(getGridUrl())) {
            try {
                URL gridUrl = new URL(LOCAL_GRID);
                return startDriver(gridUrl);
            } catch (MalformedURLException e) {
                String errorMessage = MessageFormat.format("Error has occurred when trying to initialize Selenium Grid URL. Underlying exception was: {0}",e.getMessage());
                System.out.println(errorMessage);
            }
        }

        return null;
    }

    private String getGridUrl(){
        String gridUrl = mavenPropertiesReader.getSeleniumGridUrl();
        if(null != gridUrl){
            return gridUrl;
        }
        Supplier<String> errorMessage = () -> "Unable to get Selenium Grid URL, using the fall-back value of " + LOCAL_GRID;
        logger.error(errorMessage);
        return LOCAL_GRID;
    }

    /**
     * Closes the browser session & quits the browser
     * @param driver is an instance of {@link WebDriver} that has a driver opened. If {@code null}, nothing happens.
     */
    public void closeBrowser(WebDriver driver){
        if(null != driver){
            driver.quit();
        }
    }

    private WebDriver startDriver(URL gridUrl){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("platformName", Platform.LINUX);

        return RemoteWebDriver.builder()
                .oneOf(chromeOptions)
                .address(gridUrl)
                .build();
    }

    private boolean isGridUp(String gridUrl){
        String statusGridUrl = gridUrl + "/status";
        if(null != gridHelper){
            return gridHelper.getGridStatus(statusGridUrl);
        }
        return false;
    }

}
