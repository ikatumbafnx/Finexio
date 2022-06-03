package com.finexio.pages;

import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.finexio.driver.DriverManager;

import static com.finexio.config.ConfigurationManager.configuration;
import static org.openqa.selenium.support.PageFactory.initElements;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class AbstractPageObject {

    protected AbstractPageObject() {
        initElements(new AjaxElementLocatorFactory(DriverManager.getDriver(),
                configuration().timeout()), this);
    }



}
