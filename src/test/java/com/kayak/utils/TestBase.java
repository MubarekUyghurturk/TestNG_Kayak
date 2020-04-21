package com.kayak.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


public class TestBase {

    public static  WebDriver driver;

    protected SoftAssert softAssert;
    protected Pages pages;
    protected static ExtentReports report;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest extentLogger;
    private static final Logger log = LogManager.getLogger();

    @BeforeSuite(alwaysRun = true)
    @Parameters("test")
    public void setUpTest(@Optional String test) {
        // actual reporter
        report = new ExtentReports();
        // System.getProperty("user.dir") ---> get the path to current project
        // test-output --> folder in the current project, will be created by testng if
        // it does not already exist
        // report.html --> name of the report file
        if (test == null) {
            test = "reports";
        }
        String filePath = System.getProperty("user.dir") + "/test-output/" + test + "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM_dd_yyyy")) + "/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);
        log.info("Report path: "+filePath);
        report.attachReporter(htmlReporter);
        report.setSystemInfo("browser", ConfigurationReader.getProperty("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));
        htmlReporter.config().setDocumentTitle("kayak Test automation");
        htmlReporter.config().setReportName("kayak Test automation");
        if (System.getenv("runner") != null) {
            extentLogger.info("Running: " + System.getenv("runner"));
        }
    }
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional String browser) {
        driver = Driver.getDriver(browser);
        pages = new Pages();
        softAssert = new SoftAssert();
        driver.manage().timeouts().implicitlyWait(Long.valueOf(ConfigurationReader.getProperty("implicitwait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        String URL = ConfigurationReader.getProperty("url");
        driver.get(URL);
        log.info("URL: "+URL);
    }





    @AfterMethod
    @Parameters("browser")
    public void teardown(@Optional String browser, ITestResult result) {
        // checking if the test method failed
        if (result.getStatus() == ITestResult.FAILURE) {
            // get screenshot using the utility method and save the location of the screenshot
            String screenshotLocation = BrowserUtils.getScreenshot(result.getName());

            // capture the name of test method
            extentLogger.fail(result.getName());

            // add the screenshot to the report
            try {
                extentLogger.addScreenCaptureFromPath(screenshotLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // capture the exception thrown
            extentLogger.fail(result.getThrowable());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentLogger.skip("Test Case Skipped is " + result.getName());
        }
        if(browser == null){
            browser = ConfigurationReader.getProperty("browser");
        }
        extentLogger.log(Status.INFO, MarkupHelper.createLabel("Browser: "+browser, ExtentColor.ORANGE));
        softAssert.assertAll();
        Driver.closeDriver();
    }
}
