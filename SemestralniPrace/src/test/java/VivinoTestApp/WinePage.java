package VivinoTestApp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WinePage {
    private WebDriver webDriver;
    VinoFilter vinoFilter;

    public WinePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void putOnWishList(){
        webDriver.findElement(By.cssSelector(".addToWishlist__text--3BIjr")).click();

    }
}
