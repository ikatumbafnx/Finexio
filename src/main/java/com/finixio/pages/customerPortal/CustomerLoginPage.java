package com.finixio.pages.customerPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CustomerLoginPage extends BaseMethods {

   // BaseMethods baseMethods = new BaseMethods();

    @FindBy(id = "MainContent_LoginUser_UserName")
    private WebElement email;

    @FindBy(id = "MainContent_LoginUser_Password")
    private WebElement password;

    @FindBy(id = "MainContent_LoginUser_LoginButton")
    private WebElement LoginButton;

    @FindBy(id = "MainContent_btnCreateAccount")
    private WebElement createAccount_Btn;

    @FindBy(id = "MainContent_btnMakeAPayment")
    private WebElement makePayment_Btn;


    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPawword_lnk;

    @Step("Filling in the email with {0}")
    public void fillEmail(String email) {

        this.email.sendKeys(email);
    }

    @Step("Filling in the password with {0}")
    public void fillPassword(String password) {

        this.password.sendKeys(password);
    }

    @Step("Logining into Patient Portal as a patient with username {0} and with password {1}")
    public void loginIntoCustomerPortal(String email, String password) throws Exception {
         fillEmail(email);
         fillPassword(password);
        // this.LoginButton.click();
        clickButton(LoginButton, "Login In");
    }

    @Step("Create Customer Online Account from Customers Portal")
    public void createOnlineAccount() throws Exception {
       clickButton(createAccount_Btn, "Create An Account");
    }


}
