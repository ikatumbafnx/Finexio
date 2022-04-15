package com.finixio.pages.providerPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class NewServiceApplicationPage extends BaseMethods {

    @FindBy(id = "tbServiceName")
    private WebElement ServiceName_input;

    @FindBy(id = "tbServiceCost")
    private WebElement ServiceCost_input;

    @FindBy(id = "tbServiceDescription")
    private WebElement ServiceDescription_input;

    @FindBy(id = "tbServiceDownPayment")
    private WebElement ServiceDownPayment_input;

    @FindBy(id = "btnAddService")
    private WebElement AddService_Btn;

    @FindBy(xpath = "//input[@class='btn btn-outline-secondary service-btn service-cancel']")
    private WebElement CancelService_Btn;


    @Step("Provider Adding/Creating {0} as a Service .")
    public void addService(String serviceName,
                           String cost,
                           String serviceDescription,
                           String downPaymentAmount) throws Exception {
        waitForElementToBePresent("Name",ServiceName_input);
        enterText("Name",ServiceName_input,serviceName);
        enterText("Cost",ServiceCost_input,cost);
        enterText("Description",ServiceDescription_input,serviceDescription);
        enterText("Down Payment",ServiceDownPayment_input,downPaymentAmount);

        clickButton(AddService_Btn,"Add Service");

    }



}
