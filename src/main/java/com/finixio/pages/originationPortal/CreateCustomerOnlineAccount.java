package com.finixio.pages.originationPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/27/2021
 * @project HFD-Automation
 */
public class CreateCustomerOnlineAccount extends BaseMethods {

    @FindBy(id = "formEmail")
    private WebElement customerEmail;

    @FindBy(id = "formPassword")
    private WebElement customerpassword;

    @FindBy(id = "formCoApplicantAddress")
    private WebElement address_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/form[1]/div[3]/div[1]/button[1]")
    private WebElement createHFDAccount_Btn;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[1]")
    private WebElement succesfulMessage_txt;


    @Step("Creating a Customer Online Account Management Account:")
    public void creatHFDCustomerOnlineAccount(String email, String password) throws Exception {

        enterText("Email",customerEmail,email);
        enterText("Email",customerpassword,password);
        clickButton(createHFDAccount_Btn,"Create HFD Account");


    }

    public String verifyOnlineAccountCreateMessage() throws Exception {

        String succesMsg = getText(succesfulMessage_txt);
        return succesMsg;

    }


}
