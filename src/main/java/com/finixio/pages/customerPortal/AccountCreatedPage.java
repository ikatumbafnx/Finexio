package com.finixio.pages.customerPortal;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
