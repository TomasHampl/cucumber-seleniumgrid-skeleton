package cz.tomas.test;

import cz.tomas.test.shared.StateHolder;
import cz.tomas.test.shared.WebDriverHelper;
import cz.tomas.test.shared.WebDriverInit;
import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;



@ScenarioScoped
public class MyStepdefs {

    private final WebDriverInit webDriverInit;

    private WebDriver driver;
    private final StateHolder stateHolder;
    private final WebDriverHelper webDriverHelper;

    String pageTitle;

    @Inject
    public MyStepdefs(WebDriverInit webDriverInit, StateHolder stateHolder, WebDriverHelper webDriverHelper) {
        this.webDriverInit = webDriverInit;
        this.stateHolder = stateHolder;
        this.webDriverHelper = webDriverHelper;
    }

    @Given("url to visit with browser is {string}")
    public void iWantToOpenUrlInBrowser(String urlToVisit) {
        this.driver = webDriverInit.getDriver();
        this.driver.get(urlToVisit);
        String pageTitle = driver.getTitle();
        stateHolder.setPageTitle(pageTitle);
        stateHolder.setUrl(urlToVisit);

    }

    @Then("page title is {string}")
    public void statusIs(String pageTitle) {
        assertTrue(stateHolder.getPageTitle().equalsIgnoreCase(pageTitle));
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



    @After
    public void closeBrowser(){
        webDriverInit.closeBrowser(driver);
    }
}
