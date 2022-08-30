package cz.tomas.test.shared;


import com.google.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.openqa.selenium.WebDriver;

/**
 * Data holding object. It contains several fields of which (probably) the most important is the WebDriver that holds reference
 * to currently instantiated WebDriver object.
 */
@Getter
@Setter
@ToString
@Singleton
public class StateHolder {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Inject
    private WebDriverInit webDriverInit;

    private String pageTitle;
    private String url;
    private WebDriver driver;

    public WebDriver initDriver(){
        if(null == driver){
            driver = webDriverInit.getDriver();
        }
        return driver;
    }

    public WebDriver getDriver(){
        if(null == driver){
            driver = initDriver();
        }
        return driver;
    }

}
