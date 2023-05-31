package org.example;

import org.example.VinoFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WinePage {
    private WebDriver webDriver;
    private WebDriverWait wait;
    VinoFilter vinoFilter;

    @FindBy(how = How.CSS, using = "a[class='anchor_anchor__m8Qi- scroll'] div[class='vivinoRating_caption__xL84P']")
    private WebElement ratingButton;

    public WinePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }

    public void putOnWishList(){
        webDriver.findElement(By.cssSelector(".addToWishlist__text--3BIjr")).click();

    }

    public void setStars(String message) throws InterruptedException {
        WebElement stars = webDriver.findElement(By.cssSelector(".anchor_anchor__m8Qi-.reviewRatingStars__anchor--3lBmA"));
        wait.until(ExpectedConditions.visibilityOf(stars));
        Thread.sleep(500);
        stars.click();
        Thread.sleep(500);
        WebElement inputField = webDriver.findElement(By.cssSelector("#baseModal > div > div > div:nth-child(2) > div:nth-child(2) > div._CNQhQ > textarea"));
        inputField.sendKeys(message);
        webDriver.findElement(By.cssSelector("#baseModal > div > div > div:nth-child(2) > div.row.ratingReviewEditor__lowerSection--jcx3I > div > div.ratingReviewEditor__allDoneButtonContainer--1SCAg > button")).click();
    }

    public String getYourReviews() throws InterruptedException {
        Thread.sleep(500);
        WebElement commentButton = webDriver.findElement(By.cssSelector(".anchor_anchor__m8Qi-.reviewAnchor__anchor--2NKFw.communityReview__reviewContent--3xA5s"));
        return commentButton.getText();
//        commentButton.click();
    }

    public void deleteLastReview(){
        webDriver.findElement(By.cssSelector("a[aria-label='More actions']")).click();
        WebElement delete = webDriver.findElement(By.cssSelector("li:nth-child(2) a:nth-child(1)"));
        wait.until(ExpectedConditions.visibilityOf(delete));
        delete.click();
    }

    public void likeComment(){
        WebElement like = webDriver.findElement(By.cssSelector("#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-8.desktop-column-9 > div:nth-child(1) > div > div.communityReviewItem__reviewSection--1OjzI > div.communityReview__reviewInfo--16fi- > div.communityReview__userActions--2RDK9 > a.anchor_anchor__m8Qi-.actionButton_anchor__kdR3r.communityReview__innerUserAction--Z7ESk.likeButton__likeButton--XTBaQ"));
        like.click();
    }

    public  void unlikeComment(){
        WebElement like = webDriver.findElement(By.cssSelector("#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-8.desktop-column-9 > div:nth-child(1) > div > div.communityReviewItem__reviewSection--1OjzI > div.communityReview__reviewInfo--16fi- > div.communityReview__userActions--2RDK9 > a.anchor_anchor__m8Qi-.actionButton_anchor__kdR3r.communityReview__innerUserAction--Z7ESk.likeButton__likeButton--XTBaQ.likeButton__active--18SRT"));
        like.click();
    }

    public void clickOnRaiting(){
        WebElement rait = webDriver.findElement(By.cssSelector("div[class='grid topSection'] i:nth-child(3)"));
        wait.until(ExpectedConditions.visibilityOf(rait));
        rait.click();

    }

    public String getAvRating() {
        ratingButton = webDriver.findElement(By.cssSelector("a[class='anchor_anchor__m8Qi- scroll'] div[class='vivinoRating_caption__xL84P']"));
        wait.until(ExpectedConditions.visibilityOf(ratingButton));
        ratingButton.click();
        String cssSelector = "#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-4.desktop-column-3.communityReviewsList__rateWine--RXgFW > div.vivinoRating_vivinoRating__RbvjH.vivinoRating_large__F7sLb > div.vivinoRating_averageValue__uDdPM";

        WebElement searchResultLink = webDriver.findElement(new By.ByCssSelector(cssSelector));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", searchResultLink);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchResultLink));

        String avRating = searchResultLink.getText();
        return avRating;
    }

    public String getAmountOfRatings() {
        String cssSelector = "#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-4.desktop-column-3.communityReviewsList__rateWine--RXgFW > div.vivinoRating_vivinoRating__RbvjH.vivinoRating_large__F7sLb > div.vivinoRating_starsAndCaption__uXvJB > div.vivinoRating_caption__xL84P";

        WebElement searchResultLink = webDriver.findElement(new By.ByCssSelector(cssSelector));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", searchResultLink);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchResultLink));

        String amOfRatings = searchResultLink.getText();
        return amOfRatings;
    }


}
