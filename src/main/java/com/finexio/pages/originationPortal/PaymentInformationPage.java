package com.finexio.pages.originationPortal;

import io.qameta.allure.Step;

import static com.finexio.web.base.BaseMethods.ESelectBy.selectByVisibleText;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.driver.DriverManager;
import com.finexio.web.base.BaseMethods;

/**
 * @author Ivan Katumba on 10/14/2021
 * @project HFD-Automation
 */
public class PaymentInformationPage extends BaseMethods {

    /**
     * Payment Methods
     */
    @FindBy(xpath = "//input[@data-testid='primaryPaymentType_CC']")
    private WebElement creditcard;

    @FindBy(xpath = "//input[@data-testid='primaryPaymentType_DC']")
    private WebElement debitCard;

    @FindBy(xpath = "//input[@data-testid='primaryPaymentType_HSA']")
    private WebElement fsaHsa;

    @FindBy(id = "formCoApplicantAddress")
    private WebElement address_Input;

    @FindBy(id = "formNameOnCardPrimary")
    private WebElement nameOnCard_Input;

    @FindBy(id = "formCardNumberPrimary")
    private WebElement cardNumber_Input;

    @FindBy(id = "formExpDatePrimary")
    private WebElement selectMonth_dropdown;

    @FindBy(id = "formExpYearPrimary")
    private WebElement selectYear_dropdown;

    @FindBy(id = "formSecurityCodePrimary")
    private WebElement securityCode_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/label[1]")
    private WebElement agreement_checkBox;

    @FindBy(id = "formTermMonths")
    private WebElement paymentTerm;



    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/button[1]")
    private WebElement completePaymentSetup_Btn;


    @Step("Adding Payment Information for the Patient")
    public void addPaymentInformation (
            String nameOnCard,
            String CardNo,
            String Month, String Year,
            String CVV ) throws Exception {


        DriverManager.waitUntilElementIsPresent(35,paymentTerm);
        enterText("Name On Card",nameOnCard_Input,nameOnCard);
        enterText("Card Number",cardNumber_Input,CardNo);
        selectDropDownByElement(selectByVisibleText,selectMonth_dropdown,Month);
        selectDropDownByElement(selectByVisibleText,selectYear_dropdown,Year);
        enterText("Security Code",securityCode_Input,CVV);
        DriverManager.waitUntilElementIsPresent(25,agreement_checkBox);
        clickButton(agreement_checkBox,"Consent to Agree to Terms");
        clickButton(completePaymentSetup_Btn,"Complete Payment Setup");
        String currentPage = DriverManager.getDriver().getCurrentUrl();
        System.out.println(currentPage);

    }

}
