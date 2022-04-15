package com.finixio.pages.opsPortal.Providers;

import java.util.Map;

import com.finixio.utils.StringUtil;
import com.finixio.web.base.BaseMethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/10/2021
 * @project QAAutomation
 */
public class CreateProviderAccount extends BaseMethods {
    Logger logger = LogManager.getLogger(getClass().getName());

    @FindBy(id = "ctl00_MainContent_txProviderName_I")
    private WebElement providerName;

    @FindBy(id = "ctl00_MainContent_txCompanyID_I")
    private WebElement companyID;

    @FindBy(id = "ctl00_MainContent_txMainContact_I")
    private WebElement mainContactName;

    @FindBy(id = "ctl00_MainContent_txEmail_I")
    private WebElement emailAddress;

    @FindBy(id = "ctl00_MainContent_txStreet_I")
    private WebElement streetAddress;

    @FindBy(id = "ctl00_MainContent_txCity_I")
    private WebElement cityName;

    @FindBy(id = "ctl00_MainContent_xcbCountryCode_I")
    private WebElement countryDropDown;

    @FindBy(id = "ctl00_MainContent_xcbStatoid_I")
    private WebElement stateDropDown;

    @FindBy(id = "ctl00_MainContent_txZip_I")
    private WebElement zipCode;

    @FindBy(id = "ctl00_MainContent_txPhone_I")
    private WebElement primaryPhonenumber;

    @FindBy(id = "ctl00_MainContent_txFax_I")
    private WebElement faxNumber;

    // input[@id='ctl00_MainContent_cbParentProvider_I']

    @FindBy(id = "ctl00_MainContent_cbParentProvider_I")
    private WebElement parentProvider;

    @FindBy(id = "ctl00_MainContent_txUsername_I")
    private WebElement providerUsername;

    @FindBy(id = "ctl00_MainContent_txPassword_I")
    private WebElement providerPassword;

    @FindBy(id = "ctl00_MainContent_txPasswordConfirm_I")
    private WebElement confirmPassword;

    @FindBy(id = "ctl00_MainContent_txSecretQuestion_I")
    private WebElement secretQuestion;

    @FindBy(id = "ctl00_MainContent_txSecretAnswer_I")
    private WebElement secretQnAnswer;

    @FindBy(id = "ctl00_MainContent_cbProgramTemplate_I")
    private WebElement programTemplate;

    @FindBy(id = "ctl00_MainContent_btnCreateProvider_CD")
    private WebElement createProviderButton;

    @FindBy(css = "#ctl00_MainContent_divAlert")
    private WebElement successProviderCreatedBanner;

    // private HashMap<String, Object> hashMap;
    //
    // public void setHashMap(HashMap<String, Object> toSet) {
    // this.hashMap = toSet;
    // }
    //
    // public Object getFromKey(String key) {
    // return hashMap.get(key);
    // }
    //

    @Step("Creating Providers Account from OPS")
    public void createProvidersAccount(Map<String, String> providerData) throws Exception {
        StringUtil stringUtil = new StringUtil();

        waitForElementToBePresent("Providers Name", providerName);
        enterText("Providers Name", providerName, providerData.get("ProviderName"));
        enterText("Company ID", companyID, providerData.get("CompanyID"));

        enterText("Main Contact Name", mainContactName, providerData.get("ContactName"));
        enterText("Email", emailAddress, providerData.get("Email"));
        enterText("Street Address", streetAddress, providerData.get("StreetAddress"));
        enterText("City", cityName, providerData.get("City"));
        enterText("Country", countryDropDown, providerData.get("Country"));

        enterText("State", stateDropDown, providerData.get("State"));
        enterText("Zip Code", zipCode, providerData.get("Zip"));
        enterText("Phone Number", primaryPhonenumber, providerData.get("Phone"));
        enterText("Fax Number", faxNumber, providerData.get("Fax"));
        enterText("UserName", providerUsername, providerData.get("Username"));
        enterText("Password", providerPassword, providerData.get("Password"));
        enterText("Confirm Password", confirmPassword, providerData.get("Password"));
        enterText("Secret Question", secretQuestion, providerData.get("SecretQn"));
        enterText("Secret Answer", secretQnAnswer, providerData.get("SecretAns"));

        enterText("Programe", programTemplate, providerData.get("Program"));

        clickButton(createProviderButton, "Create Provider");

        String successMessage = getText(successProviderCreatedBanner);
        logger.info("The Provider was Scucessfully created is:  " + successMessage);
        logger.info("The Provider ID Created is: " + StringUtil.getNumberFromString(successMessage));

    }

}
