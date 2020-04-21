package com.kayak.utils;

import com.kayak.pages.HomePage;
import com.kayak.pages.ResultPage;

public class Pages {

    private HomePage homePage;
    private ResultPage resultPage;



    public HomePage homePage(){

        if(homePage == null){
            homePage = new HomePage();
        }

        return homePage;
    }





    public ResultPage resultPage(){

        if(resultPage == null){
            resultPage = new ResultPage();
        }

        return resultPage;
    }




}
