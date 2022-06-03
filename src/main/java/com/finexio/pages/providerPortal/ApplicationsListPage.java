package com.finexio.pages.providerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/13/2021
 * @project HFD-Automation
 */
public class ApplicationsListPage extends BaseMethods {

    @FindBy(id = "ctl00_ContentPlaceHolder1_lblUserName")
    private WebElement pageHeading;

    @Step("Verifying that the Provider is looking at the Applications List ")
    public String  verifyAppIsonApplicationListScreen() throws Exception {
        String welcomeMessage = getText(pageHeading);
        return welcomeMessage;
    }



}
