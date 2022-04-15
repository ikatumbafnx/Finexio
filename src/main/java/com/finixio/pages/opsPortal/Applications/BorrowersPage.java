package com.finixio.pages.opsPortal.Applications;

import com.finixio.driver.DriverManager;
import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class BorrowersPage extends BaseMethods {

    @FindBy(css = "#ctl00_MainContent_UCAppSummary1_xlabProcID")
    private WebElement ProcedureIDDisplayText;

    @FindBy(css = "#ctl00_MainContent_UCBorrowers1_xbtnSavePmtInfo")
    private WebElement SavePaymentInfoButton;

    // WebElents for CreditCard Update

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbCCName_I")
    private WebElement NameOnCardInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbCCNo_I")
    private WebElement CreditCardNumberInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbCCSecurity_I")
    private WebElement CVVInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xcbCCMonth_I")
    private WebElement ExpireMonthInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xcbCCYear_I")
    private WebElement ExpireYearInput;

    // WebElents for DebitCard Update

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbDCName_I")
    private WebElement NameOnDebitCardInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbDCNo_I")
    private WebElement DebitCardNumberInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xtbDCSecurity_I")
    private WebElement DebitCardCVVInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xcbDCMonth_I")
    private WebElement DebitCardExpireMonthInput;

    @FindBy(id = "ctl00_MainContent_UCBorrowers1_xcbDCYear_I")
    private WebElement DebitCardExpireYearInput;

    @Step("Verify that you are on the Borrowers summary screen for Application {0} ")
    public String verifyThatYouAreAtTheBorrowersSummaryPage(String ApplnID) throws Exception {
        DriverManager.waitForPageToLoad(30);
        waitForElementToBePresent("Procedure ID", ProcedureIDDisplayText);
        String confirmation = getText(ProcedureIDDisplayText);
        return confirmation;
    }

    /**
     * 
     * @param ApplnID
     * @param name
     * @param cardNo
     * @param cvv
     * @param month
     * @param year
     * @throws Exception
     */
    @Step("Updating CreditCard Information for the Borrower {0} ")
    public void updateCardInfoForBorrower(String cardType, String ApplnID,
            String name,
            String cardNo,
            String cvv,
            String month,
            String year) throws Exception {

        switch (cardType.toLowerCase()) {
            case "credit":
                DriverManager.waitForPageToLoad(30);
                waitForElementToBePresent("Full Name of Card", NameOnCardInput);
                enterText("Name On Card", NameOnCardInput, name);
                enterText("Credit Card No", CreditCardNumberInput, cardNo);
                enterText("CVV2/CID", CVVInput, cvv);
                enterText("Expiration Date Month", ExpireMonthInput, month);
                enterText("Expiration Date Year", ExpireYearInput, year);
                clickButton(SavePaymentInfoButton, "Save Payment Info");
                break;
            case "debit":
                DriverManager.waitForPageToLoad(30);
                waitForElementToBePresent("Full Name of Card", NameOnDebitCardInput);
                enterText("Name On Card", NameOnDebitCardInput, name);
                enterText("Credit Card No", DebitCardNumberInput, cardNo);
                enterText("CVV2/CID", DebitCardCVVInput, cvv);
                enterText("Expiration Date Month", DebitCardExpireMonthInput, month);
                enterText("Expiration Date Year", DebitCardExpireYearInput, year);
                clickButton(SavePaymentInfoButton, "Save Payment Info");
                break;

            default:
                DriverManager.waitForPageToLoad(30);
                waitForElementToBePresent("Full Name of Card", NameOnCardInput);
                enterText("Name On Card", NameOnCardInput, name);
                enterText("Credit Card No", CreditCardNumberInput, cardNo);
                enterText("CVV2/CID", CVVInput, cvv);
                enterText("Expiration Date Month", ExpireMonthInput, month);
                enterText("Expiration Date Year", ExpireYearInput, year);
                clickButton(SavePaymentInfoButton, "Save Payment Info");
                break;

        }

    }

}
