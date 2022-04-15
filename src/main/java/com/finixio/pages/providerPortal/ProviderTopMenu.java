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
public class ProviderTopMenu extends BaseMethods {

    @FindBy(id = "aHFDLogo")
    private WebElement ReturnHomeImage;

    @FindBy(id = "aPaymentPlan")
    private WebElement PaymentPlans;

    @FindBy(id = "ctl00_aApplications")
    private WebElement PaymentPlan_Applications;

    @FindBy(id = "ctl00_aPending")
    private WebElement PaymentPlan_Pending;

    @FindBy(className = "nav-link.aSettings")
    private WebElement SettingsGear;

    @FindBy(className = "nav-item.dropdown.notification-list.liSettingsProfile")
    private WebElement UserProfile;
    /**
     * Below are the webelement when you click the Profile Gear
     */
    @FindBy(linkText = "Leave Feedback")
    private WebElement Leavefeedback_Link;

    @FindBy(className = "//span[contains(text(),'Profile')]")
    private WebElement Profile;

    @FindBy(id = "ctl00_devPortal")
    private WebElement Profile_Developer_Link;

    @FindBy(id = "ctl00_lnkHelpCenter")
    private WebElement Profile_Helpceter_link;

    @FindBy(id = "ctl00_btnLogOut")
    private WebElement Logout_Link;

    @Step("Accessing the Pending Applications due for Activation:")
    public void goToPendingApplications() throws Exception {

        clickButton(PaymentPlans, "Payment Plans");
        clickButton(PaymentPlan_Pending, "Pending");

    }

    @Step("Accessing the Applications list")
    public void goTogApplicationsPage() throws Exception {

        clickButton(PaymentPlans, "Payment Plans");
        clickButton(PaymentPlan_Applications, "Applications");

    }

    @Step("Accessing the {1} from the {0} Main Menu on the OPS partal")
    public void goToSpecificMenu(String mainMenu, String menuItem) throws Exception {

        WebElement mainMenuElement = DriverManager.getDriver()
                .findElement(By.xpath("//span[normalize-space()='" + mainMenu + "']"));
        WebElement subMenuElement = DriverManager.getDriver()
                .findElement(By.xpath("//span[normalize-space()='" + menuItem + "']"));

        waitForElementToBePresent(mainMenu, mainMenuElement);
        clickButton(mainMenuElement, mainMenu);
        waitForElementToBePresent(menuItem, subMenuElement);
        clickButton(subMenuElement, menuItem);

    }

}
