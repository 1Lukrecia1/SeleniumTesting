package VivinoTestApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class VinoFilter {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private String region;

    SelectVine selectVine;

    //private int searchRating;
    private String[] setValues;
    @FindBy(how = How.CSS, using = "input[placeholder='Search regions']")
    WebElement regionInput;

    @FindBy(how = How.CSS, using = "body > div:nth-child(3) > div:nth-child(9) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > label:nth-child(4) > div:nth-child(4)")
    WebElement ratingInput36;
    @FindBy(how = How.CSS, using = "body > div:nth-child(3) > div:nth-child(9) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > label:nth-child(3) > div:nth-child(4)")
    WebElement ratingInput38;

    @FindBy(how = How.CSS, using = "body > div:nth-child(3) > div:nth-child(9) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > label:nth-child(2) > div:nth-child(4)")
    WebElement ratingInput42;


    public VinoFilter(WebDriver webDriver, WebDriverWait wait, String[] setValues) {
//        setValues[1] = region;
//        setValues[2] = String.valueOf(rating);
        this.setValues = setValues;
        this.wait = wait;
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver,this);
        //searchRating = rating;
//        WebElement inp = webDriver.findElement(By.cssSelector(""));
//        inp.click();
        selectRatingAndRegion();
    }

    public void selectRatingAndRegion(){
        regionInput.sendKeys(setValues[2]);
        if(Integer.parseInt(setValues[1]) <38) ratingInput36.click();
        else if (Integer.parseInt(setValues[1]) < 42) ratingInput38.click();
        else ratingInput42.click();
        List<WebElement> regions = webDriver.findElements(By.cssSelector("#explore-page-app > div > div > div.explorerPage__columns--1TTaK > div.explorerPage__filters--1Hmly > div > div:nth-child(5) > div.filterPills__items--_grOA > label:nth-child(1)"));
        String expectedValue = regions.get(0).getText();
        //System.out.println(expectedValue);
        //Assertions.assertEquals(expectedValue, region);
        regions.get(0).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        WebDriver.Timeouts timeouts = webDriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        setValues[2] = checkRegion() ;
        selectVine = new SelectVine(webDriver, setValues);
    }

    public String checkRegion(){
        String regionFilter = webDriver.findElement(By.cssSelector("label[class='pill__pill--2AMAs pill__selected--3KX2r activeRegionFilters__pill--1Q09m'] span[class='pill__text--24qI1']")).getText();
        if (regionFilter.contains(setValues[2])) return "true";
        return "false";
    }
}



//        WebDriver.Timeouts timeouts = webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        try {
//            timeouts.getScriptTimeout().wait(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //        int counter = 0;
//        for(WebElement link : wines){
//            if (counter == 5) {
//                JavascriptExecutor js = (JavascriptExecutor) webDriver;
//                js.executeScript("window.scrollBy(0,350)", "");
//                break;
//            }
//            counter++;
//            System.out.println(link.getAttribute("href"));
//        }
