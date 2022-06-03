package com.finexio.pages.providerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ProviderLogin  extends BaseMethods {

    @FindBy(id = "loginMain_UserName")
    private WebElement email;

    @FindBy(id = "loginMain_Password")
    private WebElement password;

    @FindBy(id = "loginMain_LoginButton")
    private WebElement LoginButton;


    @Step
    public void fillEmail(String email) {

        this.email.sendKeys(email);
    }

    @Step
    public void fillPassword(String password) {

        this.password.sendKeys(password);
    }

    @Step
    public void login(String password) {

        this.password.sendKeys(password);
    }

    @Step("Logining into Patient Portal as a patient with username {0} and with password {1}")
    public void loginIntoProviderPortal(String emailaddress, String pssword) throws Exception {
        enterText("Username/Email",email,emailaddress);
        enterText("Password",password,pssword);
        clickButton(LoginButton, "Login In");
    }

}
