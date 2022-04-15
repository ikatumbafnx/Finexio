package com.finixio.pages.customerPortal;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/19/2021
 * @project HFD-Automation
 */
public class AccountCreationStep2 extends BaseMethods {

    @FindBy(id = "ctl00_MainContent_tbPassword")
    private WebElement password_Input;

    @FindBy(id = "ctl00_MainContent_tbPasswordConfirm")
    private WebElement passwordConfirm_Input;

    @FindBy(id = "MainContent_tbQuestion")
    private WebElement recoveryQuestion_Input;

    @FindBy(id = "MainContent_tbAnswer")
    private WebElement recoverAnswer_Input;

    @FindBy(id = "ctl00_MainContent_cbAccept_S_D")
    private WebElement agreeToTerms_chkBox;

    @FindBy(id = "MainContent_btnCreate_CD")
    private WebElement createAccount_Btn;


    public void createAccountFinalStep(String password, String recoverquestion, String recoverAnswer ) throws Exception {

        enterText("Password",password_Input,password);
        enterText("Confirm Password",passwordConfirm_Input,password);
        enterText("Recover Question",recoveryQuestion_Input,recoverquestion);
        enterText("Recovery Answer:" , recoverAnswer_Input,recoverAnswer);
        clickButton(agreeToTerms_chkBox,"I agree to the Terms of Use");
        clickButton(createAccount_Btn,"Create Account");


    }

}
