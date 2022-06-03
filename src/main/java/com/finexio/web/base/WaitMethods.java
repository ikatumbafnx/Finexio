package com.finexio.web.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.finexio.driver.DriverManager;
import com.finexio.pages.AbstractPageObject;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitMethods extends AbstractPageObject {

//Class constants


    /**
     * Wait for Page state to be ready in DOM
     */

    /**
     * Implicit wait
     * @param timeout
     */
    public void implicitWait(int timeout) {
        DriverManager.getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.MILLISECONDS);
    }




}
