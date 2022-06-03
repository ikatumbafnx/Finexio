package com.finexio.pages.providerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 11/3/2021
 * @project HFD-Automation
 */
public class AccountsSideMenu extends BaseMethods {

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T0")
    private WebElement profile_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T1T")
    private WebElement documents_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T2")
    private WebElement services_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T3")
    private WebElement users_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T4")
    private WebElement secretKeys_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T5")
    private WebElement customizations_lnk;

    @FindBy(id = "ctl00_ContentPlaceHolder1_xpcMyAccount_T6")
    private WebElement adjustments_lnk;

    @Step("Providers is Navigating to: {0}")
    public void navigateTo(String menuName) throws Exception {
        // clicking on the menu link specified by the customer

        waitForElementToBePresent("View Profiles", profile_lnk);

        switch (menuName) {

        case "Profile":
            clickButton(profile_lnk, "View DashBoard");
            break;
        case "Document":
            clickButton(documents_lnk, "View Payments");
            break;
        case "Services":
            clickButton(services_lnk, "View Profile");
            break;
        case "Users":
            clickButton(users_lnk, "View Users");
            break;
        case "Secret Key":
            clickButton(secretKeys_lnk, "View Secret Keys");
            break;
        case "Customization":
            clickButton(customizations_lnk, "Customizations");
            break;
        case "Adjustments":
            clickButton(adjustments_lnk, "Adjustments");
            break;

        default:
            clickButton(profile_lnk, "View Profiles");
            break;

        }

    }

}
