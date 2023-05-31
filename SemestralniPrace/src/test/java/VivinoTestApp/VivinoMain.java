package VivinoTestApp;

import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class VivinoMain {

    WebDriver webDriver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

//    ,
//            "Red,Rhone Valley,38",
//            "White,Rhone Valley,42",
//            "Red,Napa Valley,36",
//            "White,Napa Valley,38",
//            "Red,Napa Valley,42",
//            "White,Piemonte,36",
//            "Red,Piemonte,38",
//            "White,Piemonte,42"

    @ParameterizedTest
    @CsvSource({"White,Rhone Valley,36"})
    public void startProcess(String wineType, String region,String rating ) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        Search search = new Search(webDriver, wineType, Integer.parseInt(rating), region );
        search.completeSearchBar();
        boolean result = true;
//        System.out.println(Arrays.toString(search.vinoFilter.selectVine.resultArray));
        Assertions.assertArrayEquals(new boolean[] {true,true, true}, search.vinoFilter.selectVine.resultArray);
//        for(boolean istrue :search.vinoFilter.selectVine.resultArray) {
//            if (!istrue) {
//                result = false;
//                break;
//            }
//        }
//        Assertions.assertTrue(result);
        webDriver.close();
    }

    @ParameterizedTest
    @CsvSource({"Red,Bordeaux,42"})
    public void isReviewTheSame(String wineType, String region, String rating) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        WinePage winePage = new WinePage(webDriver);
        Search search = new Search(webDriver, wineType, Integer.parseInt(rating), region );
        search.doTheVineChoise();

        WebElement headline = webDriver.findElement(By.cssSelector("body > div.wrap > div.grid.topSection > div > div > div.mobile-column-1.tablet-column-8.desktop-column-6 > div.row.header.breadCrumbs > div > h1 > span.headline"));
        WebElement vintage = webDriver.findElement(By.cssSelector("body > div.wrap > div.grid.topSection > div > div > div.mobile-column-1.tablet-column-8.desktop-column-6 > div.row.header.breadCrumbs > div > h1 > span.vintage"));

        String ratingOfWine = winePage.getAvRating();
        String amountOfRatings = winePage.getAmountOfRatings();

        new SearchInput(webDriver, headline.getText() + " " + vintage.getText());
        List<WebElement> searchingWine2 = webDriver.findElements(By.cssSelector("body > div.wrap > section.search-page.section-alt > div > div > div > div.search-page__content a"));
        searchingWine2.get(0).click();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String ratingToControl = winePage.getAvRating();
        String amountOfRatingsToControl = winePage.getAmountOfRatings();

        Assertions.assertEquals(ratingOfWine, ratingToControl);
        Assertions.assertEquals(amountOfRatings, amountOfRatingsToControl);

        webDriver.close();
    }



    @Test
    public void ifParamsMatches(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        WebElement input = webDriver.findElement(By.cssSelector("input[placeholder='Search any wine']"));
        String name = "Château Lafite Rothschild";
        input.sendKeys(name);
        input.submit();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> searchingWine = webDriver.findElements(By.cssSelector("body > div.wrap > section.search-page.section-alt > div > div > div > div.search-page__content a"));
        searchingWine.get(0).click();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement resultName = webDriver.findElement(By.cssSelector("div[class='mobile-column-4 tablet-column-8 desktop-column-6'] a[class='winery']"));
        Assertions.assertEquals(name, resultName.getText());
        webDriver.close();
    }
//    @CsvSource({"Beef","Lamb","Veal","Game (deer, venison)","Poultry","Mushrooms","Cured meat","Goat Cheese","Mature and hard cheese","Mild and soft cheese","Pasta","Spicy food","Aperitif","Appetizers and snacks","Lean fish","Rich fish(Salmon, tuna etc)","Shellfish","Vegetarian"})

    @ParameterizedTest
    @CsvSource({"Beef"})
    public void isCorrectMeal(String meal) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        WebElement pairs = webDriver.findElement(By.cssSelector("span[title='Pairings']"));
        new Actions(webDriver)
                .moveToElement(pairs)
                .perform();
        //span[title='Game (deer, venison)']

        String cssSel = "div.subMenu_subsection__79U0r span[title='"+ meal+"']";
        WebElement mealLink = webDriver.findElement(By.cssSelector(cssSel));
        Thread.sleep(500);
        mealLink.click();
        Thread.sleep(4000);
        boolean result = true;
        for (boolean r: manageWinePage(meal)){
            if (!r) {
                result = false;
                break;
            }
        }

        Assertions.assertTrue(result);
        webDriver.close();
//        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt a"));
//        for(WebElement a:wines) {
//                System.out.println(a.getText());
//            }
        }

    public ArrayList<Boolean> manageWinePage( String mealName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        ArrayList<Boolean> result = new ArrayList<>();
        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt > a:first-child"));
        ArrayList<String> links = new ArrayList<>();
        for(WebElement wine: wines){
            links.add(wine.getAttribute("href"));
        }
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        int iterCountSize = wines.size()-1;
//        System.out.println(iterCountSize);
        if(iterCountSize > 5) iterCountSize = 5;
        outerloop:
        for(int j = 0; j <= iterCountSize; j++){
            webDriver.get(links.get(j));
//            //WebElement wine = wines.get(j);
//            //wait.until(ExpectedConditions.visibilityOf(wine));
//            Actions action = new Actions(webDriver);
//            action.keyDown(Keys.CONTROL).click(wines.get(j)).keyUp(Keys.CONTROL).build().perform();
//            //webDriver.get(links.get(j));
//            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
//            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
//            webDriver.switchTo().window(tabs.get(1));
//            System.out.println(tabs.size());
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            for(int i = 900; i<=2100; i += 300)js.executeScript("window.scrollTo(0, "+ i +" )");
            List<WebElement> meals = webDriver.findElements(By.cssSelector("div[class='col mobile-column-6 tablet-column-10 desktop-column-7'] a div:nth-child(4)"));
            if(meals.size() == 0)continue ;
            wait.until(ExpectedConditions.visibilityOf(meals.get(meals.size()-1)));
            for(WebElement meal: meals) {
                String findingName =  meal.getText();
                if (Objects.equals(findingName, mealName)) {
                    //webDriver.close();
                    //webDriver.switchTo().window(tabs.get(0));
                    result.add(true);
                    continue outerloop;
                }
            }
            //webDriver.switchTo().window(tabs.get(0));
            result.add(false);
        }
        return result;
    }

    @Test
    public void checkIfLikedWinesInWichList() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        Profile profile = new Profile(webDriver);
        profile.login();
        Thread.sleep(2000);
        new SearchInput(webDriver, "Château Lafite Rothschild");
        new ResultPage(webDriver).getNthElement(0);
        new WinePage(webDriver).putOnWishList();
        profile.goToProfile();
        Assertions.assertEquals("Château Lafite Rothschild", profile.checkWishList());
        //webDriver.close();
    }

    @ParameterizedTest
    @CsvSource({"name1, surname1","name2, surname2","Test, Seleniumov"})
    public void changeNameAndSurnameTest(String name, String surname) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        Profile profile = new Profile(webDriver);
        profile.login();
        profile.goToSettings();
        profile.changeNameAndSurname(name, surname);
        profile.goToProfile();
        String result = webDriver.findElement(By.cssSelector("body > div.wrap > div.user-profile > div > div.container > div.col-sm-4.col-xs-12.user-profile-sidebar > div.user-profile-sidebar-group.user-details.clearfix > div.user-header.text-center.clearfix > div.user-header__name.header-medium.bold")).getText();
        Assertions.assertEquals(name +" " + surname, result);
        webDriver.close();
    }

    @Test
    public void commentTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        Profile profile = new Profile(webDriver);
        profile.login();
        Thread.sleep(1000);
        new SearchInput(webDriver, "Château Lafite Rothschild");
        new ResultPage(webDriver).getNthElement(0);
        WinePage winePage = new WinePage(webDriver);
        winePage.clickOnRaiting();
        Thread.sleep(1000);
        String mess = "Test";
        winePage.setStars(mess);
        String newReview = winePage.getYourReviews();
        winePage.deleteLastReview();
        webDriver.switchTo().alert().accept();
        Assertions.assertEquals(true, newReview.contains(mess));
        webDriver.close();
    }

    @Test
    public void checkLike() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/FR/en/hans-greyl-sauvignon-blanc/w/1177078?year=2020&price_id=31750299#all_reviews");
        Profile profile = new Profile(webDriver);
        profile.login();
        Thread.sleep(2000);
        scrollPage(1000);
        Thread.sleep(1000);
        scrollPage(1500);
        Thread.sleep(1000);
        scrollPage(2100);
        WinePage winePage = new WinePage(webDriver);
        Thread.sleep(1000);
        String countOfLikeBefore = webDriver.findElement(By.cssSelector("#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-8.desktop-column-9 > div:nth-child(1) > div > div.communityReviewItem__reviewSection--1OjzI > div.communityReview__reviewInfo--16fi- > div.communityReview__userActions--2RDK9 > a.anchor_anchor__m8Qi-.actionButton_anchor__kdR3r.communityReview__innerUserAction--Z7ESk.likeButton__likeButton--XTBaQ > div.likeButton__likeCount--1stJS")).getText();
        winePage.likeComment();
        Thread.sleep(500);
        String countOfLikeAfter = webDriver.findElement(By.cssSelector("#all_reviews > div:nth-child(3) > div.col.mobile-column-6.tablet-column-8.desktop-column-9 > div:nth-child(1) > div > div.communityReviewItem__reviewSection--1OjzI > div.communityReview__reviewInfo--16fi- > div.communityReview__userActions--2RDK9 > a.anchor_anchor__m8Qi-.actionButton_anchor__kdR3r.communityReview__innerUserAction--Z7ESk.likeButton__likeButton--XTBaQ.likeButton__active--18SRT > div.likeButton__likeCount--1stJS")).getText();
        winePage.unlikeComment();
        Assertions.assertEquals(Integer.parseInt(countOfLikeBefore)+1, Integer.parseInt(countOfLikeAfter));
        webDriver.close();
    }

    @Test
    public void checkCart() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        Profile profile = new Profile(webDriver);
        profile.login();
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("#navigation-container > div > nav > div.navigation_rightNav__D674c > div:nth-child(1) > div")).click();
        String cssSel = "#navigation-container > div > nav > div.navigation_rightNav__D674c > div:nth-child(1) > div.navigationItem_closeWrapper__n28xG > div.navigationItem_container__6CZxX.shipToDropdown_navItem__op5qL.navigationItem_alignContainerRight__glRHR > ul > li:nth-child(11)";
        webDriver.findElement(By.cssSelector(cssSel)).click();
        Thread.sleep(2000);
        WebElement input = webDriver.findElement(By.cssSelector("input[placeholder='Search any wine']"));
        String name = "Château Paloumey Haut-Médoc 2018";
        input.sendKeys(name);
        input.submit();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> searchingWine = webDriver.findElements(By.cssSelector("body > div.wrap > section.search-page.section-alt > div > div > div > div.search-page__content a"));
        searchingWine.get(0).click();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Thread.sleep(100);
        String headline = webDriver.findElement(By.cssSelector("body > div.wrap > div.grid.topSection > div > div > div.mobile-column-1.tablet-column-8.desktop-column-6 > div.row.header.breadCrumbs > div > h1 > span.headline")).getText();
        String vintage = webDriver.findElement(By.cssSelector("body > div.wrap > div.grid.topSection > div > div > div.mobile-column-1.tablet-column-8.desktop-column-6 > div.row.header.breadCrumbs > div > h1 > span.vintage")).getText();
        scrollPage(400);
        WebElement minusButton = webDriver.findElement(By.cssSelector("#purchase-availability > div > div.purchaseAvailability__purchaseAvailability--3bov5 > div > div.purchaseAvailability__row--S-DoM.purchaseAvailability__cta--1Dpz4 > div > div > div.quantityPicker__decrease--31bYs"));
        for(int i = 0; i<=5; i++){
            minusButton.click();
        }
        webDriver.findElement(By.cssSelector("#purchase-availability > div > div.purchaseAvailability__purchaseAvailability--3bov5 > div > div.purchaseAvailability__row--S-DoM.purchaseAvailability__cta--1Dpz4 > button > span")).click();
        String result = webDriver.findElement(By.cssSelector("#cart-page > div.cartPage__cartPage--2fVRe > div > div > div.cartPage__cartList--14OQf > div > div.cartListItem__vintage--1MgnJ > div.cartListItem__details--3vJi6 > a > span:nth-child(2)")).getText();
        webDriver.findElement(By.cssSelector("div.cartListItem__actions--2fUSl > a.anchor__anchor--2QZvA.cartListItem__deleteItem--3Ykqd")).click();
        Assertions.assertEquals(vintage, result);
        webDriver.close();
    }


//    @Test
//    public void



    public void scrollPage(int scrollLenght){
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, "+ scrollLenght +" )");
    }


}
