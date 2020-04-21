package com.kayak.tests;

import com.kayak.pages.HomePage;
import com.kayak.utils.BrowserUtils;
import com.kayak.utils.ConfigurationReader;
import com.kayak.utils.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HomePageTest extends TestBase {

    private static Logger log = LogManager.getLogger(HomePage.class.getName());

    @Test(dataProvider = "data_info")
    @Parameters({"browser"})
    public void bookedFlight(String originCity, String destinationCity,String departDay,String returnDay) throws IOException, InterruptedException {
        extentLogger = report.createTest("Book flight");
        HomePage homePage= new HomePage();
        BrowserUtils.waitPlease(4);
        homePage.deletButtonElement.click();
        log.info("default airport deleted");

        BrowserUtils.waitPlease(2);

        homePage.originCityElement.sendKeys(originCity);
        BrowserUtils.waitPlease(3);
        homePage.originCityElement.sendKeys(Keys.RETURN);
        log.info("origin city entered");
        homePage.destInitilizeElement.click();
        BrowserUtils.waitPlease(2);
        homePage.destinationCityElement.sendKeys(destinationCity);
        BrowserUtils.waitPlease(3);
        homePage.destinationCityElement.sendKeys(Keys.RETURN);
        log.info("destination entered");

        homePage.dataInitilize.click();

        //evaluate departure date
        for(int i = 0; i<11;i++) {
            String month1 = homePage.monthRangeElement1.getAttribute("innerHTML");
            String month2 = homePage.monthRangeElement2.getAttribute("innerHTML");
            String month = month1 + month2;
            String months[] = month.split(" ");
            Set<String> m = new HashSet<>(Arrays.asList(months));

            if(!m.add(departDay.split(" ")[0]) & !m.add(departDay.split(" ")[2])) break;
            homePage.nextmonthElement.click();
            BrowserUtils.waitPlease(3);
        }
        BrowserUtils.waitPlease(3);
        String d = departDay.split(" ")[0]+" "+departDay.split(" ")[1];
        driver.findElement(By.xpath("//div[@aria-label='"+d+"']")).click();      //  <======  ?   why we use a.length not b.length


        // evaluate return date
        for(int i = 0; i<11;i++) {
            String month1 = homePage.monthRangeElement1.getAttribute("innerHTML");
            String month2 = homePage.monthRangeElement2.getAttribute("innerHTML");
            String month = month1 + month2;
            String months[] = month.split(" ");
            Set<String> m = new HashSet<>(Arrays.asList(months));
            if(!m.add(returnDay.split(" ")[0]) & !m.add(returnDay.split(" ")[2])) break;
            homePage.nextmonthElement.click();
            BrowserUtils.waitPlease(3);
        }

        BrowserUtils.waitPlease(3);
        String r = returnDay.split(" ")[0]+" "+returnDay.split(" ")[1];
        driver.findElement(By.xpath("//div[@aria-label='"+r+"']")).click();


        homePage.searchButtonElement.click();
        log.info("Successfully submitted!");
        BrowserUtils.waitPlease(3);

        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
            BrowserUtils.waitPlease(3);
            try {
                homePage.clsPopUp.click();
            }
            catch (ElementNotInteractableException e){
                log.error(e);
                log.info("can't close popup window");
            }
        }

        int N = Integer.parseInt(ConfigurationReader.getProperty("N"));
        log.info("will check"+N+"results");
        Iterator<WebElement> itr = homePage.results.iterator();
        int i = 0;
        while(itr.hasNext() & i < N) {
            String city = itr.next().getAttribute("textContent");
            Assert.assertTrue(city.toLowerCase().contains(originCity.toLowerCase()));
            log.info("Result:"+i+" --departure matched to the search");
            extentLogger.pass("PASS");
            Assert.assertTrue(city.toLowerCase().contains(destinationCity.toLowerCase()));
            log.info("Result:"+i+" --arrival matched to the search");
            extentLogger.pass("PASS");
            i++;
        }

    }






    @DataProvider(name ="data_info")
    public static Object[][] datas(){
        return new Object[][]{
                {"Oakland","los angeles","May 10 2020","May 20 2020"},
                {"san francisco","Los angeles","June 1 2020","July 15 2020"},
                {"oakland","wisconsin","December 1 2020","December 20 2020"},
                {"San francisco","New york","February 2 2021","February 5 2021"}};

    }


}
