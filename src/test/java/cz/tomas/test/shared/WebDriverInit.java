package cz.tomas.test.shared;

import com.google.inject.Inject;
import cz.tomas.test.dto.Browser;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import jakarta.inject.Singleton;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Supplier;

@Singleton
public class WebDriverInit {

    private static final String LOCAL_GRID = "http://172.17.0.1:4444";
    private final Logger logger = LoggerFactory.getLogger(WebDriverInit.class);

    @Inject
    private GridHelper gridHelper;

    @Inject
    private MavenPropertiesReader mavenPropertiesReader;

    /**
     * Instantiation method that returns a new instance of {@link WebDriver} associated with selenium grid instance or {@code null}
     * if none is available<br /><br />
     * @return new WebDriver instance
     */
    public WebDriver getDriver(){
        String gridBaseUrl = getGridUrl();
        if (isGridUp(gridBaseUrl)) {
            try {
                URL gridUrl = new URL(gridBaseUrl);
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
        List<Browser> availableBrowsers = gridHelper.getBrowsers(gridUrl + "/status");
        return RemoteWebDriver.builder()
                .oneOf((Capabilities) getBrowser(availableBrowsers))
                .address(gridUrl)
                .build();
    }

    private Object getBrowser(List<Browser> browserList){
        if(!browserList.isEmpty()){
            for (Browser browser : browserList){
                if ("Chrome".equalsIgnoreCase(browser.getName())) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setPlatformName(browser.getOs());
                    return chromeOptions;
                }
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPlatformName(browser.getOs());
                return firefoxOptions;
            }
        }
        return null;
    }

    private boolean isGridUp(String gridUrl){

        String statusGridUrl = gridUrl + "/status";
        if(null != gridHelper){
            return gridHelper.getGridStatus(statusGridUrl);
        }
        return false;
    }

}
