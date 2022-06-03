package com.finexio.pages.customerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 11/1/2021
 * @project HFD-Automation
 */
public class CustomerLinkVerificationPage extends BaseMethods {

    @FindBy(id = "MainContent_lbGoHome")
    private WebElement JumpIn_Btn;

    @FindBy(id = "MainContent_labHeader")
    private WebElement PageHeader_txt;

    @Step("Verifying that the  Customers account was verified from the email")
    public void jumpInAndLogintoCustomerAccount() throws Exception {

        String pageHeader = getText(PageHeader_txt);
        clickButton(JumpIn_Btn,"Jump in");

    }

    public String verifyAccountVerified() throws Exception {

        String succesMsg = getText(PageHeader_txt);
        return succesMsg;

    }


}
