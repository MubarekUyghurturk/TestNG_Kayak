package com.kayak.tests;

import com.kayak.utils.TestBase;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;



public class TestDemo {

    WebDriver driver;


    @BeforeMethod
    public void setUp(){
        System.out.println( "Before every method");
    }



    @Test

    public void googleSetup() {


        System.setProperty("webdriver.chrome.driver", "/Users/mubarekuyghurturk/Downloads/chromedriver");


        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("java");
        search.sendKeys(Keys.ENTER);


        String title = driver.getTitle();
        System.out.println( driver.getTitle());

        Assert.assertEquals(driver.getTitle(),"java - Google Search");

    }



    @AfterMethod
    public void tearDown(){
        System.out.println( "After every Method");


    }



}
