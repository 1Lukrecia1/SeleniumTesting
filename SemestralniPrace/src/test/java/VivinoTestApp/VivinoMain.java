package VivinoTestApp;

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
        System.out.println(Arrays.toString(search.vinoFilter.selectVine.resultArray));
        for(boolean istrue :search.vinoFilter.selectVine.resultArray) {
            if (!istrue) {
                result = false;
                break;
            }
        }
        Assertions.assertTrue(result);
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

    public ArrayList<Boolean> manageWinePage( String mealName){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        ArrayList<Boolean> result = new ArrayList<>();
        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt > a:first-child"));
        ArrayList<String> links = new ArrayList<>();
        for(WebElement wine: wines){
            links.add(wine.getAttribute("href"));
        }
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        int iterCountSize = wines.size()-1;
        System.out.println(iterCountSize);
        if(iterCountSize > 5) iterCountSize = 5;
        outerloop:
        for(int j = 0; j <= iterCountSize; j++){
//            WebElement wine = wines.get(j);
            //wait.until(ExpectedConditions.visibilityOf(wine));
            Actions action = new Actions(webDriver);
            action.keyDown(Keys.CONTROL).click(wines.get(j)).keyUp(Keys.CONTROL).build().perform();
            //webDriver.get(links.get(j));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1));
            System.out.println(tabs.size());
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            for(int i = 900; i<=2100; i += 300)js.executeScript("window.scrollTo(0, "+ i +" )");
            List<WebElement> meals = webDriver.findElements(By.cssSelector("div[class='col mobile-column-6 tablet-column-10 desktop-column-7'] a div:nth-child(4)"));
            if(meals.size() == 0)continue ;
            wait.until(ExpectedConditions.visibilityOf(meals.get(meals.size()-1)));
            for(WebElement meal: meals) {
                String findingName =  meal.getText();
                if (Objects.equals(findingName, mealName)) {
                    webDriver.close();
                    webDriver.switchTo().window(tabs.get(0));
                    result.add(true);
                    continue outerloop;
                }
            }
            webDriver.switchTo().window(tabs.get(0));
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
        webDriver.close();
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

    

}
