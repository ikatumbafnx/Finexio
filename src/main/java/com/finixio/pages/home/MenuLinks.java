package com.finixio.pages.home;

import io.qameta.allure.Step;

import com.finixio.pages.AbstractPageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuLinks  extends AbstractPageObject {



    @FindBy(xpath = "//div[@class='nav-button']")
    private WebElement LoginButton;

    @FindBy(linkText = "Patient")
    private WebElement PatientLogin;

    @FindBy(linkText = "Provider")
    private WebElement ProviderLogin;

    @FindBy(xpath = "//a[normalize-space()='Accept']")
    private WebElement AcceptCookies;


    @Step("Accessing the Patients Login Portal from the Home Page")
    public void accessPatientPortal(){
        acceptCookiesPolicy();
        LoginButton.click();
        PatientLogin.click();

    }

    @Step("Accessing the Providers Login Portal from the Home Page")
    public void accessProviderPortal(){
        acceptCookiesPolicy();
        LoginButton.click();
        ProviderLogin.click();

    }



    @Step("Accepting  cookies on the home page")
    private void acceptCookiesPolicy() {
        AcceptCookies.click();
        //webDriver.switchTo().defaultContent();
    }

}
