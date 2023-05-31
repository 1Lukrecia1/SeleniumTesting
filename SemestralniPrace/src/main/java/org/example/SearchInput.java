package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchInput {
    WebDriver webDriver;

    public SearchInput(WebDriver webDriver, String inputValue) {
        this.webDriver = webDriver;
        WebElement input = webDriver.findElement(By.cssSelector("input[placeholder='Search any wine']"));
        input.sendKeys(inputValue);
        input.submit();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
