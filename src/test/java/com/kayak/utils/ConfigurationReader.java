package com.kayak.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties configFile;


    static {

        try{

            // give a path to my configuration file
            String path ="configuration.properties";
            // create an object of input stream to access my properties file
            FileInputStream input = new FileInputStream(path);
            // initializa my config file
            configFile = new Properties();
            // load properties file
            configFile.load(input);
            //close input stream
            input.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getProperty(String Key){
        return configFile.getProperty(Key);
    }

}
