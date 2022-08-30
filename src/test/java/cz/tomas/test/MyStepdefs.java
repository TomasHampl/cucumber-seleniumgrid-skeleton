package cz.tomas.test;

import com.google.inject.Inject;
import cz.tomas.test.shared.ElementActions;
import cz.tomas.test.shared.StateHolder;
import cz.tomas.test.shared.WebDriverHelper;
import cz.tomas.test.shared.WebDriverInit;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertTrue;



@ScenarioScoped
public class MyStepdefs {

    private final WebDriverInit webDriverInit;

    private WebDriver driver;
    private final StateHolder stateHolder;
    private final WebDriverHelper webDriverHelper;
    private final ElementActions elementActions;

    String pageTitle;

    @Inject
    public MyStepdefs(WebDriverInit webDriverInit, StateHolder stateHolder, WebDriverHelper webDriverHelper, ElementActions elementActions) {
        this.webDriverInit = webDriverInit;
        this.stateHolder = stateHolder;
        this.webDriverHelper = webDriverHelper;
        this.elementActions = elementActions;
    }

    @Given("url to visit with browser is {string}")
    public void iWantToOpenUrlInBrowser(String urlToVisit) {

        this.driver = webDriverInit.getDriver();
        this.driver.get(urlToVisit);
        String pageTitle = driver.getTitle();
        stateHolder.setDriver(this.driver);
        stateHolder.setPageTitle(pageTitle);
        stateHolder.setUrl(urlToVisit);

    }

    @Then("page title is {string}")
    public void statusIs(String pageTitle) {
        boolean titleInStateIsOk = stateHolder.getPageTitle().equalsIgnoreCase(pageTitle);
        if(titleInStateIsOk){
            assertTrue(true);
        } else {
            boolean titleIsOk = new WebDriverWait(stateHolder.getDriver(),Duration.ofSeconds(10)).until(ExpectedConditions.titleIs(pageTitle));
            assertTrue(titleIsOk);
        }
    }

    @Given("url to send http get request to is {string}")
    public void urlToSendHttpGetRequestToIs(String url) {
        stateHolder.setUrl(url);

    }

    @Then("http status is {int}")
    public void httpStatusIs(int httpStatus) {
        when()
                .request("GET", stateHolder.getUrl())
                .then()
                .statusCode(httpStatus);
    }

    @Then("we save a screenshot")
    public void takeScreenshot(){
        webDriverHelper.takeScreenshot(this.driver);
    }

    @When("we click on element with {string} text")
    public void weClickOnElementWithText(String text) {
        elementActions.click(webDriverHelper.findElementByText(driver,text));
    }

    @After
    public void closeBrowser(){
        webDriverInit.closeBrowser(driver);
    }


}
