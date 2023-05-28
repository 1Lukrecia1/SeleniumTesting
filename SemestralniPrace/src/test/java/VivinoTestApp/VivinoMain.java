package VivinoTestApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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


//    @CsvSource({"Red, 42 , Napa Valley", "White, 37,Napa Valley "})

    WebDriver webDriver = new ChromeDriver();
//            "White,Rhone Valley,36","Red,Rhone Valley,38","White,Rhone Valley,42",            "Red,Napa Valley,36",
//            "White,Napa Valley,38",
//            "Red,Napa Valley,42",
//            "White,Piemonte,36",
//            "Red,Piemonte,38",
//            "White,Piemonte,42"
    @ParameterizedTest
    @CsvSource({"White,Rhone Valley,36",
            "Red,Rhone Valley,38",
            "White,Rhone Valley,42",
            "Red,Napa Valley,36",
            "White,Napa Valley,38",
            "Red,Napa Valley,42",
            "White,Piemonte,36",
            "Red,Piemonte,38",
            "White,Piemonte,42"})
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
    }

    @ParameterizedTest
    @CsvSource({"Beef", "Pork"})
    public void isCorrectMeal(String meal) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver.get("https://www.vivino.com/");
        WebElement pairs = webDriver.findElement(By.cssSelector("span[title='Pairings']"));
        new Actions(webDriver)
                .moveToElement(pairs)
                .perform();

        String cssSel = "span[title='"+ meal+"']";
        WebElement mealLink = webDriver.findElement(By.cssSelector(cssSel));
        mealLink.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean result = true;
        for (boolean r: manageWinePage(meal)){
            if (!r) {
                result = false;
                break;
            }
        }

        Assertions.assertTrue(result);
//        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt a"));
//        for(WebElement a:wines) {
//                System.out.println(a.getText());
//            }
        }

    public ArrayList<Boolean> manageWinePage( String mealName){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        List<WebElement> wines = webDriver.findElements(By.cssSelector("div.card__card--2R5Wh.wineCard__wineCardContent--3cwZt"));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        ArrayList<Boolean> result = new ArrayList<>();
        outerloop:
        for(int j = 0; j<=5 ; j++){
            WebElement wine = wines.get(j);
            Actions action = new Actions(webDriver);
            action.keyDown(Keys.CONTROL).click(wine).keyUp(Keys.CONTROL).build().perform();
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(j+1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(4));
            js.executeScript("window.scrollTo(0, 1000)");
            js.executeScript("window.scrollTo(0, 1500)");
            js.executeScript("window.scrollTo(0, 1900)");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<WebElement> meals = webDriver.findElements(By.cssSelector("div[class='col mobile-column-6 tablet-column-10 desktop-column-7'] a div:nth-child(4)"));
            for(WebElement meal: meals) {
                String findingName =  meal.getText();
                if (Objects.equals(findingName, mealName)) {
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
}
