package com.kayak.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.awt.print.PageFormat;


public class BasePage {

    private static final Logger log = LogManager.getLogger();



    public BasePage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }



}
