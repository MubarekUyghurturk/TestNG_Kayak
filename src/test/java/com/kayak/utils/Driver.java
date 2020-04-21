package com.kayak.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.security.auth.login.Configuration;

public class Driver {
    private static WebDriver driver;

// for create singleton class , i make my constructor private
    private Driver() {
    }


    public synchronized static WebDriver getDriver(String browser) {
        // String browser ==>  it originally comes from xml file to test base class, from test base it comes here
        if (driver == null) {
            // first we check if the value from xml file is null or not
            // if the value from xml file NOT null we use
            // the value from xml file IS null, we get the browser from properties file

            browser = browser == null ? ConfigurationReader.getProperty("browser") : browser;

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;


                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();

                default:
                    throw new RuntimeException("Illegal browser type!");
            }
        }
        return driver;
    }


    public static WebDriver getDriver() {

        return getDriver(null);
    }





    public  static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
