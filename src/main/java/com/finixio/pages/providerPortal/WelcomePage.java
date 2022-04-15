package com.finixio.pages.providerPortal;

import io.qameta.allure.Step;

import com.finixio.driver.DriverManager;
import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class WelcomePage extends BaseMethods {

    @FindBy(css = ".page-title")
    private WebElement WelcomeBanner;

    @FindBy(xpath = "/html[1]/body[1]/form[1]/div[6]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[1]/div[3]/a[1]")
    private WebElement viewPlanReadyForActivation;

    /**
     * Help Button Menus and Dropdown Links
     */
    @FindBy(id = "btnHelp")
    private WebElement Help_btn;

    @FindBy(linkText = "Watch tutorial")
    private WebElement Help_Watch_tutorial_Link;

    @FindBy(id = "lblScheduleTraining")
    private WebElement Help_Schedule_training_link;

    @FindBy(xpath = "//a[normalize-space()='Accept']")
    private WebElement AcceptCookies;

    /**
     * Quick Actions Links
     */

    @FindBy(linkText = "Go")
    private WebElement QuickAction_Go_Btn;

    @FindBy(id = "ctl00_ContentPlaceHolder1_btnEasyApplyWaterfall")
    private WebElement QuickAction_Apply_Btn;

    /**
     * Create Services Element
     */
    @FindBy(xpath = "//button[normalize-space()='Create']")
    private WebElement createService_Btn;

    @FindBy(xpath = "//button[normalize-space()='Invite']")
    private WebElement inviteUser_Btn;

    @FindBy(xpath = "//a[normalize-space()='View Services']")
    private WebElement viewServices_lnk;

    @FindBy(xpath = "/html[1]/body[1]/form[1]/div[6]/div[1]/div[1]/div[3]/div[1]/div[1]/div[3]/div[2]/div[1]/button[2]")
    private WebElement serviceDropDown_Drp;

    /**
     *
     * @return
     * @throws Exception
     */
    @Step("Verifying that the Provider is on the DashBoard")
    public String verifyPresenceOfHomePage() throws Exception {
        DriverManager.waitForPageToLoad(30);
        String welcomeMessage = getText(WelcomeBanner);
        return welcomeMessage;
    }

    @Step("Provider registering a Patient")
    public void registerpatient() throws Exception {
        clickButton(QuickAction_Apply_Btn, "Apply");
    }

    @Step("Accepting  cookies on the home page")
    public void acceptCookiesPolicy() {
        waitForElementToBePresent("Providers Name", AcceptCookies);
        AcceptCookies.click();
        // webDriver.switchTo().defaultContent();
    }

    @Step("View Applications Plans Ready for Activation")
    public void viewPendingApplicationsReadyForActivation() throws Exception {
        clickButton(viewPlanReadyForActivation, "Payment plans ready to be activated!");
    }

    @Step("Create Services  By the Provider")
    public void createServives() throws Exception {
        clickButton(createService_Btn, "Create Services");
    }

    @Step("Provider Inviting the User")
    public void inviteUser() throws Exception {
        clickButton(inviteUser_Btn, "Invite");
    }

    @Step("Provider Views the List of all Services")
    public void viewallservices() throws Exception {
        clickButton(serviceDropDown_Drp, "View Services");
        waitForElementToBePresent("View Services", viewServices_lnk);
        clickButton(viewServices_lnk, "View Services");
    }

}
