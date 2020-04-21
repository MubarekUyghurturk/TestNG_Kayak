package com.kayak.pages;

import com.kayak.utils.Driver;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    public ResultPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
}
