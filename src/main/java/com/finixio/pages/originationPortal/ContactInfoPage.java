package com.finixio.pages.originationPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/14/2021
 * @project HFD-Automation
 */
public class ContactInfoPage extends BaseMethods {

    @FindBy(id = "emailAddress")
    private WebElement email_input;

    @FindBy(id = "formCoApplicantPhoneNumber")
    private WebElement phoneNumber_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/button[2]")
    private WebElement continue_Btn;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/button[1]")
    private WebElement backToStep1_Btn;

//    @FindBy(className = "step-header")
//    private WebElement pageTittle_Txt;

    /**
     * Service Details
     */


    @Step("Adding Contact Information for the Patient")
    public void addHowWeCanContactYou(String email,String phoneNo ) throws Exception {

        enterText("Email Address",email_input,"t@t.com");
        enterText("Phone Number",phoneNumber_Input,phoneNo);
        clickButton(continue_Btn,"Continue");

    }

    @Step("Continuing without Entering or Changing the Customer Details")
    public void continueToAddressScreen( ) throws Exception {

       clickButton(continue_Btn,"Continue");

    }

//    @Step("Verifying that Patient is on How can we contact you Page ")
//    public String confirmOnContactPage() throws Exception {
//
//        String pageTitle =  getText(pageTittle_Txt);
//        return pageTitle;
//
//    }



}
