package cz.tomas.test.shared;

import com.google.inject.Inject;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.Supplier;

@Singleton
public class ElementActions {

    @Inject
    private WebDriverHelper webDriverHelper;

    private final Logger logger = LoggerFactory.getLogger(ElementActions.class);

    /**
     * Simplest method that performs a 'click' on an element
     * @param xpathExpression is a String that represents a valid xpath expression
     * @param stateHolder is an instance of {@link StateHolder}, object leveraged primarily to pass information around
     */
    public void click(String xpathExpression, StateHolder stateHolder){
        WebElement webElement = webDriverHelper.findElementByXpath(stateHolder.getDriver(), xpathExpression);
        if(null != webElement){
            click(webElement);
        }
        Supplier<String> errorMessage = () -> "We found no element using the provided xpath expression: " + xpathExpression;
        logger.error(errorMessage);
    }

    /**
     * Simple method that performs a 'click' on an element
     * @param elementId is a String representing the elementID. If the element with such ID is not found, then no click is performed and respective entry is added to the logs
     * @param stateHolder is an instance of {@link StateHolder}, object leveraged primarily to pass information around
     */
    public void clickById(String elementId, StateHolder stateHolder){
        WebElement webElement = webDriverHelper.findElementById(stateHolder.getDriver(), elementId);
        if(null != webElement){
            click(webElement);
        }
        Supplier<String> errorMessage = () -> "We found no element with ID. Nothing was clicked: " + elementId;
        logger.error(errorMessage);
    }

    /**
     * Simplest method that performs a 'click' on an element
     * @param webElement is an instance of {@code WebElement}
     */
    public void click(WebElement webElement){
        webElement.click();
    }

    public void clickAndHold(String xpathExpression, StateHolder stateHolder){
        WebElement webElement = webDriverHelper.findElementByXpath(stateHolder.getDriver(), xpathExpression);
        new Actions(stateHolder.getDriver())
                .clickAndHold(webElement)
                .perform();
    }
}
