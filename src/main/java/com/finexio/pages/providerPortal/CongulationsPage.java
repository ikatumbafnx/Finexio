package com.finexio.pages.providerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.config.ConfigurationManager;
import com.finexio.driver.DriverManager;
import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/13/2021
 * @project HFD-Automation
 */
public class CongulationsPage extends BaseMethods {

    private int timeout = ConfigurationManager.configuration().timeout();

    @FindBy(id = "nextConfirmation")
    private WebElement Got_it_Btn;

    @Step("Confirming that the Application was succesfully created")
    public void confirmAccountCreation(){

       DriverManager.waitUntilElementIsPresent(timeout,Got_it_Btn);
       Got_it_Btn.click();
       //waitForElementToBeDisplayedAndEnabledThenClick(Got_it_Btn);

    }

}
