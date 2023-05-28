package VivinoTestApp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResultPage {
    private WebDriver webDriver;

    public ResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void getNthElement(int i){
        List<WebElement> searchingWine = webDriver.findElements(By.cssSelector("body > div.wrap > section.search-page.section-alt > div > div > div > div.search-page__content a"));
        searchingWine.get(i).click();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
