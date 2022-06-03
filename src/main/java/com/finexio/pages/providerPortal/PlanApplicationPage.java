package com.finexio.pages.providerPortal;

import io.qameta.allure.Step;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.finexio.driver.DriverManager;
import com.finexio.utils.DateUtils;
import com.finexio.web.base.BaseMethods;

import static com.finexio.web.base.BaseMethods.ESelectBy.selectByVisibleText;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class PlanApplicationPage extends BaseMethods {

    @FindBy(id = "ctl00_ContentPlaceHolder1_FirstName")
    private WebElement FirstName_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_LastName")
    private WebElement LastName_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_EmailAddress")
    private WebElement Email_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_PhoneNumber")
    private WebElement PhoneNumber_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_socialSecurityNumber")
    private WebElement SSN_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_ddServiceSelect_I")
    private WebElement PaymentPlanRequest_Input;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_dropDownEdit_DDD_DDTC_checkListBox_LBI7T2")
    private WebElement PaymentPlan_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_ddServiceSelect_DDD_L_LBI3T1")
    private WebElement paymentPlanTestservice;

    @FindBy(id = "ctl00_ContentPlaceHolder1_dateEdit_I")
    private WebElement BeginServiceOnDate_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_patientID")
    private WebElement PatientUniqueId_Input;

    @FindBy(id = "btnSubmit")
    private WebElement Submit_Btn;

    @FindBy(linkText = "What's an Easy App?")
    private WebElement ApplnPageTitle;

    @FindBy(linkText = "View existing Easy Applications")
    private WebElement ViewExistingApplications_Link;

    /**
     * Payment Plans Elements below
     */

    @FindBy(css = "#ctl00_ContentPlaceHolder1_ddServiceSelect_DDD_L_LBI7T1")
    private WebElement WaterfallPlan;

    @FindBy(id = "ctl00_ContentPlaceHolder1_dropDownEdit_DDD_DDTC_checkListBox")
    private WebElement PaymentPlanCheckList;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_dropDownEdit_DDD_DDTC_checkListBox_LBI0C")
    private WebElement firstPaln;

    @FindBy(id = "#ctl00_ContentPlaceHolder1_dropDownEdit_DDD_DDTC_checkListBox")
    private WebElement PaymentPlanCheckListTable;

    @FindBy(id = "ctl00_ContentPlaceHolder1_downPaymentAmount")
    private WebElement downpaymentAmount_Input;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_ddServiceSelect_I")
    private WebElement paymentplanOption_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_selectCustomAmount")
    private WebElement customAmountDropdown;

    @Step("Submitting a patient application for {0} {1}")
    public void submitPatientApplication(
            String FirstName,
            String LastName,
            String Email,
            String phoneNumber,
            String SSN,
            String PatientID) throws Exception {
        enterText("FirstName", FirstName_Input, FirstName);
        enterText("LastName", LastName_Input, LastName);
        enterText("Email", Email_Input, Email);
        enterText("Phone", PhoneNumber_Input, phoneNumber);
        enterText("SSN", SSN_Input, SSN);
        DateUtils dateUtils = new DateUtils();

        selectPlan();
        enterText("to begin service on", BeginServiceOnDate_Input, dateUtils.getDate("MM/dd/YYYY"));
        enterText("Patient Unique ID", PatientUniqueId_Input, PatientID);
        clickButton(Submit_Btn, "Submit");

    }

    @Step("Selecting the payment plan for the payment")
    public void selectPlan() throws Exception {
        // clickButton(PaymentPlanRequest_Input,"requesting a payment plan for");
        clickButton(paymentplanOption_Input, "requesting a payment plan for");
        // clickButton(PaymentPlan_Input,"requesting a payment plan for");
        // clearTextField(paymentplanOption_Input, "Requesting Payment Plan");
        enterText("Requesting Payment Plan", paymentplanOption_Input, "Waterfall Test Service");
        // window handles

        // returnToMainWindow();

        // setFocusToWindow();
        // clickButton(firstPaln, "Selected plan");
        // scrollDown();
        // DriverManager.getDriver().switchTo().activeElement().sendKeys(Keys.ENTER);
        // waitForElementToBePresent("Providers Name", PaymentPlan_Input);
        // clickButton(PaymentPlan_Input, "Selected plan");
        // waitForElementToBeDisplayedAndEnabledThenClick(paymentPlanTestservice);
        // waitForElementToBeDisplayedAndEnabledThenClick(WaterfallPlan);
        clickOutside();
        // enterText("Down Payment", downpaymentAmount_Input, "200");
        selectDropDownByElement(selectByVisibleText, customAmountDropdown, "without");
    }

    @Step("Verifying that provider is on the Patient Registration Screen")
    public String confirmOnApplicationPage() {
        String pageHeading = ApplnPageTitle.getText();
        return pageHeading;
    }

    @Step("View all the Application Submited from the Application Screen")
    public void viewExistingApplications() {
        waitForElementToBeDisplayedAndEnabledThenClick(ViewExistingApplications_Link);

    }

}
