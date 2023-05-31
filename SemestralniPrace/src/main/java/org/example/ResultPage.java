package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResultPage {
    private WebDriverWait wait;
    private WebDriver webDriver;

    public ResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
    }

    public void getNthElement(int i){
        List<WebElement> searchingWine = webDriver.findElements(By.cssSelector("body > div.wrap > section.search-page.section-alt > div > div > div > div.search-page__content a"));
//        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(searchingWine.get(i)));
        searchingWine.get(i).click();
    }
}
