package cz.tomas.test.shared;

import com.google.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

@Singleton
public class WebDriverInit {

    private static final String LOCAL_GRID = "http://172.17.0.1:4444";

    @Inject
    private GridHelper gridHelper;

    public WebDriver getDriver(){
        if (isGridUp(LOCAL_GRID)) {
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
