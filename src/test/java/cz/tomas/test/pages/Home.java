package cz.tomas.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Home {

    private final WebDriver driver;


    public Home(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//a[contains(@href,'/downloads')]")
    WebElement downloadLink;

    public void clickDownloadsLink(){
        downloadLink.click();
    }
}
