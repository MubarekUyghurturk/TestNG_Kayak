package com.kayak.pages;

import com.kayak.utils.ConfigurationReader;
import com.kayak.utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HomePage {

    public HomePage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath="(//div[@class='_iac _irF _Hk _h-8'])[1]")
    public WebElement deletButtonElement;


    @FindBy(xpath="(//input[contains(@id,'-origin-airport')])[1]")
    public WebElement originCityElement;


    @FindBy(xpath="(//div[contains(@id,'-destination-airport-display')])[1]")
    public WebElement destInitilizeElement;


    @FindBy(xpath="//div[contains(@id,'-destination-airport-display')][1]")
    public WebElement destinationCityElement;


    @FindBy(xpath = "(//div[contains(@id,'-dateRangeInput-display-start-inner')])[1]")
    public WebElement dataInitilize;




    @FindBy(xpath="(//div[@id='stl-jam-cal-monthsGrid']/div)[2]/div[1]/div[1]")
    public WebElement monthRangeElement1;


    @FindBy(xpath="(//div[@id='stl-jam-cal-monthsGrid']/div)[3]/div[1]/div[1]")
    public WebElement monthRangeElement2;

    @FindBy(xpath=("//div[@id='stl-jam-cal-nextMonth']"))
    public WebElement nextmonthElement;

    @FindBy(xpath="(//button[contains(@id,'submit')])[1]")
    public WebElement searchButtonElement;

    @FindBy(xpath="(//*[contains(@id,'-dialog-close')])[10] | (//*[contains(@id,'-dialog-close')])[11]")
    public WebElement clsPopUp;


    @FindBy(xpath="//nav[@class='aria-controls']/p")
    public List<WebElement> results;



}
