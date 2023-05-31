package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectVine {
    String[] setValues;
    WebDriver webDriver;

    List<WebElement> filtersOfWine ;
    public String ratingOfWine;
    public boolean[] resultArray = new boolean[3];



    public SelectVine(WebDriver webDriver, String[] setValues) {
        this.setValues = setValues;
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver,this);
        WebDriver.Timeouts timeouts = this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        timeouts.implicitlyWait(Duration.ofSeconds(10));


        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.wineCard__wineCard--2dj2T div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt a"));
//        wines.get(0).click();
        webDriver.get(wines.get(0).getAttribute("href"));
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ratingOfWine = webDriver.findElement(By.cssSelector("div.vivinoRating_averageValue__uDdPM")).getText();
        filtersOfWine = webDriver.findElements(By.cssSelector("#wine-location-header > div > div > div > div.col.mobile-column-6.tablet-column-6.desktop-column-5 a"));
        ArrayList<String>filterValues = new ArrayList<>();

        for(WebElement a: filtersOfWine){
            filterValues.add(a.getText());
        }
        getCorrectValues(filterValues);
    }
    public void getCorrectValues(ArrayList<String> filterValues){
        for(String filterValue: filterValues){
            if(filterValue.equals(setValues[0])){
                resultArray[0] = true;
                continue;
                }
            }
        if(Double.parseDouble(ratingOfWine)*10 >= Integer.parseInt(setValues[1])) {
            resultArray[1] = true;
        }
        if(setValues[2].equals("true")){
            resultArray[2] = true;
        }
    }
}



