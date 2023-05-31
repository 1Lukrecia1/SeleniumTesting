package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class Profile {

    public boolean amILogin = false;
    final String email = "testsoftware20222023@gmail.com";
    final String password = "testselenium456";
    private WebDriver webDriver;
    private WebDriverWait wait;

    public Profile(WebDriver webDriver) throws InterruptedException {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        clickOnProfileButton();
        amILogin = true;
    }

    public void clickOnProfileButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement loginButton;
        if(!amILogin){
            loginButton = webDriver.findElement(By.cssSelector("#navigation-container > div > nav > div.navigation_rightNav__D674c > div.userMenu_loginLink__m9B0-"));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            loginButton.click();
        }else {
            loginButton = webDriver.findElement(By.cssSelector("#navigation-container > div > nav > div.navigation_rightNav__D674c > span > div > div > div > div"));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            loginButton.click();
        }
        Thread.sleep(500);
    }

    public void goToProfile() throws InterruptedException {
        clickOnProfileButton();
        webDriver.findElement(By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > span:nth-child(5) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1)")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    }

    public void login() throws InterruptedException {
        WebElement emailInput = webDriver.findElement(By.cssSelector("#email"));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(email);
        emailInput.submit();
//        Thread.sleep(500);
        WebElement passwordInput = webDriver.findElement(By.cssSelector("#password"));
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
        passwordInput.submit();
    }

    public String checkWishList(){
        webDriver.findElement(By.cssSelector("nav[class='user-profile-menu clearfix text-smaller semi inflate col-xs-12 col-sm-8 menu-original'] li:nth-child(3) a:nth-child(1)")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        WebElement nameOfWishedWine = webDriver.findElement(By.cssSelector("#main-content > div > div > div > div > div.wine-info > span > a"));
        String firstWineInWishList = nameOfWishedWine.getText();
        webDriver.findElement(By.cssSelector("#main-content > div > div > div > div > div.wine-info > p > a")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        new WinePage(webDriver).putOnWishList();
        return firstWineInWishList;
    }

    public void goToSettings() throws InterruptedException {
        if(amILogin){
            clickOnProfileButton();
            WebElement settings = webDriver.findElement(By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > span:nth-child(5) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)"));
            wait.until(ExpectedConditions.visibilityOf(settings));
            settings.click();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        }
        else {
            login();
            goToSettings();
        }
    }

    public void changeNameAndSurname(String name, String surname){
        ArrayList<String> name_sirname = new ArrayList<>();
        WebElement newFirstname = webDriver.findElement(By.cssSelector("#first_name"));
        newFirstname.clear();
        newFirstname.sendKeys(name);
        WebElement newSurname = webDriver.findElement(By.cssSelector("#last_name"));
        newSurname.clear();
        newSurname.sendKeys(surname);
        WebElement newAlias = webDriver.findElement(By.cssSelector("#alias"));
        newAlias.clear();
        newAlias.sendKeys(name + ' ' + surname);
//        JavascriptExecutor js = (JavascriptExecutor) webDriver;
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement saveButton = webDriver.findElement(By.cssSelector("#settings-form > section:nth-child(2) > div > div > div > button"));
        wait.until(ExpectedConditions.visibilityOf(saveButton));
        saveButton.click();
    }

    public String getComment(){
        String test = webDriver.findElement(By.cssSelector(".tasting-note.text-larger")).getText();
        return test;
    }
}
