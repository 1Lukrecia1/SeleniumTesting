package org.example;

import io.netty.util.concurrent.BlockingOperationException;
import org.example.VinoFilter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Search {
    private String region;
    private int rating;
    WebDriverWait wait;
    private String[] setValues = new String[3];
    private WebDriver webDriver;
    private String url = "https://www.vivino.com";
    private String toFindWord;
    public VinoFilter vinoFilter, vinoFilterFor;
    @FindBy(how = How.CSS, using = "input[placeholder='Search any wine']")
    WebElement searchBar;


    public Search(WebDriver webDriver, String toFindWord, int rating, String region){
        setValues[0] = toFindWord + " wine";
        setValues[1] = String.valueOf(rating);
        setValues[2] = region;
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        PageFactory.initElements(this.webDriver, this);
        webDriver.get(url);
        this.toFindWord = toFindWord;
        this.rating = rating;
        this.region = region;
    }

    public void completeSearchBar(){
        searchBar.sendKeys(toFindWord);
        searchBar.submit();
        WebDriver.Timeouts timeouts = webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //vinoFilter = new VinoFilter(webDriver, rating, region , wait, setValues);
        vinoFilter = new VinoFilter(webDriver, wait, setValues);
        vinoFilter.selectRatingAndRegion();
    }

    public void doTheVineChoise() {
        searchBar.sendKeys(toFindWord);
        searchBar.submit();
        WebDriver.Timeouts timeouts = webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        vinoFilterFor = new VinoFilter(webDriver, wait, setValues);
        vinoFilterFor.inputsOfRatingAndRegion();
    }
}