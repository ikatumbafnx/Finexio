package com.finixio.pages.originationPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/14/2021
 * @project HFD-Automation
 */
public class GetStartedPage extends BaseMethods {

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[1]/input[1]")
    private WebElement last4SSN_Input;

    @FindBy(id = "formEmailAddress")
    private WebElement email_Input;

    @FindBy(xpath = "//button[contains(text(),'GET STARTED')]")
    private WebElement getStarted_Btn;

    @Step("Get Started for patient {1}")
     public void getStarted(String last4ssn , String email) throws Exception {
         enterText("Last 4 digits of SSN",last4SSN_Input,last4ssn);
         enterText("Email Address",email_Input,email);
         clickButton(getStarted_Btn,"GET STARTED");
     }

}
