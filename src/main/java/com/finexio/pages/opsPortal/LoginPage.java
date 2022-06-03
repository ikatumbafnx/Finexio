package com.finexio.pages.opsPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

public class LoginPage extends BaseMethods {

    @FindBy(id = "MainLogin1_UserName")
    private WebElement usernameField;

    @FindBy(id = "MainLogin1_Password")
    private WebElement passwordField;

    @FindBy(id = "MainLogin1_RememberMe")
    private WebElement rememberCheckBox;

    @FindBy(id = "MainLogin1_LoginButton")
    private WebElement loginButton;

    @Step("Filling the username field with {0}")
    public void fillUsername(String username) {

        this.usernameField.sendKeys(username);
    }
    @Step("Filling in the password field with {1}")
    public void fillPassword(String password){
        this.passwordField.sendKeys(password);
    }

    @Step("Logging into the Ops Portal as a user with username {0} and password {1}")
    public void loginIntoOpsPortal(String username, String password) throws Exception {
        enterText("UserName",usernameField,username);
        enterText("Password",passwordField,password);
        clickButton(rememberCheckBox, "Remember Me Checked");
        clickButton(loginButton, "Logging in");
    }


}