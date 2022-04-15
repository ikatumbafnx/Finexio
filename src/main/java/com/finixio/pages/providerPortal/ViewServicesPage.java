package com.finixio.pages.providerPortal;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ViewServicesPage extends BaseMethods {

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_btnNewService")
    private WebElement AddService_Btn;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_DXFREditorcol3_I")
    private WebElement SearchServiceNameInput;

    @FindBy(css = "a[id='ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_DXCBtn0'] span")
    private WebElement EditLink;

    @FindBy(css = "a[id='ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_DXCBtn1'] span")
    private WebElement DeleteLink;

    // Service Searched Results Elemnts
    @FindBy(css = "tr[id='ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_DXDataRow0'] td:nth-child(2)")
    private WebElement SearchedServiceNameField;

    // Service Edit WebElemnts

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_ef16_tbServiceName_I")
    private WebElement EditServiceNameInput;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_ef16_ASPxMemo1_I")
    private WebElement EditServiceDescriptionInput;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_ef16_tbServiceCost_I")
    private WebElement EditServiceCostInput;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_ef16_btnUpdate")
    private WebElement EditServiceSaveBtn;

    @FindBy(css = "#ctl00_ContentPlaceHolder1_xpcMyAccount_myProviderServices_ASPxGridView1_ef16_btnCancel")
    private WebElement EditServiceCancelBtn;

    @Step("Provider Editing the  {0} Service .")
    public void editService(String serviceName,
            String cost,
            String serviceDescription) throws Exception {

        try {

            waitForElementToBePresent("Edit Service", EditLink);
            clickButton(EditLink, "Edit");
            waitForElementToBeDisplayedAndEnabledThenClick(EditServiceNameInput);
            enterText("Service Name", EditServiceNameInput, serviceName);
            enterText("Description", EditServiceDescriptionInput, serviceDescription);
            enterText("Cost", EditServiceCostInput, cost);
            // enterText("Down Payment",ServiceDownPayment_input,downPaymentAmount);
            clickButton(EditServiceSaveBtn, "Save");

        } catch (StaleElementReferenceException e) {
            // TODO: handle exception

            waitForElementToBePresent("Edit Service", EditLink);
            clickButton(EditLink, "Edit");
            waitForElementToBeDisplayedAndEnabledThenClick(EditServiceNameInput);
            enterText("Service Name", EditServiceNameInput, serviceName);
            enterText("Description", EditServiceDescriptionInput, serviceDescription);
            enterText("Cost", EditServiceCostInput, cost);
            // enterText("Down Payment",ServiceDownPayment_input,downPaymentAmount);
            clickButton(EditServiceSaveBtn, "Save");
        }

    }

    @Step("Provider Delleting the  {0} Service .")
    public void deleteService(String serviceName,
            String cost,
            String serviceDescription,
            String downPaymentAmount) throws Exception {
        // waitForElementToBePresent("Name",ServiceName_input);
        // enterText("Name",ServiceName_input,serviceName);
        // enterText("Cost",ServiceCost_input,cost);
        // enterText("Description",ServiceDescription_input,serviceDescription);
        // enterText("Down Payment",ServiceDownPayment_input,downPaymentAmount);

        // clickButton(AddService_Btn,"Add Service");

    }

    @Step("Provider Editing the  {0} Service .")
    public String searchServiceByName(String serviceName) throws Exception {
        String serviceNameText;
        waitForElementToBeDisplayedAndEnabledThenClick(SearchServiceNameInput);
        enterText("Service Name", SearchServiceNameInput, serviceName);
        clickOutside();
        serviceNameText = getText(SearchedServiceNameField);
        return serviceNameText;
    }
}
