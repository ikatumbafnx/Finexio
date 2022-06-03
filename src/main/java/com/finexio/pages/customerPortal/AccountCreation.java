package com.finexio.pages.customerPortal;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/19/2021
 * @project HFD-Automation
 */
public class AccountCreation  extends BaseMethods {

    @FindBy(id = "MainContent_dtDOB_I")
    private WebElement DOB_Input;

    @FindBy(id = "MainContent_tbEmail")
    private WebElement eMail_Input;

    @FindBy(id = "MainContent_btnContinue")
    private WebElement accountCreateContinue_Btn;

    public void createAccountStep1(String dateOfBirth , String email) throws Exception {

       enterText("Date of Birth",DOB_Input,dateOfBirth);
       enterText("Email",eMail_Input,email);
       clickButton(accountCreateContinue_Btn,"Continue");

    }




}
