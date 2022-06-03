package com.finexio.pages.customerPortal;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/19/2021
 * @project HFD-Automation
 */
 //testing
public class AccountCreatedPage extends BaseMethods {

    @FindBy(id = "MainContent_xlblSuccess")
    private WebElement successMessage_Txt;

    public String verifyCustomerCreated() throws Exception {

        String succesMsg = getText(successMessage_Txt);
        return succesMsg;

    }


}
