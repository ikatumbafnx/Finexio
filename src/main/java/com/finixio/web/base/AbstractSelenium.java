package com.finixio.web.base;

import org.openqa.selenium.WebDriver;

public class AbstractSelenium{

    public WebDriver driver;
    public AbstractSelenium(WebDriver driver){
        this.driver = driver;
    }


}
