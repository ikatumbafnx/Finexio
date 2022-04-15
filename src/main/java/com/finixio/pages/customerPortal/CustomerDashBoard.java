package com.finixio.pages.customerPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/25/2021
 * @project HFD-Automation
 */
public class CustomerDashBoard extends BaseMethods {

    @FindBy(id = "MainContent_lblWelcome")
    private WebElement DashboardHeader;

    @FindBy(id = "MainContent_btnPayNow")
    private WebElement payNow_Btn;

    @FindBy(id = "MainContent_btnPayments")
    private WebElement viewFinanceDetails_Lnk;

    @Step("View Customers Finance Details ")
    public void viewFinanceDetails() throws Exception {
       // clicking on the view finance details;
        clickButton(viewFinanceDetails_Lnk, "View Finance Details");
    }

    @Step("Pay Down Payment")
    public void intiatepaydownpayment() throws Exception {
        // clicking on the view finance details;
        clickButton(payNow_Btn, "View Finance Details");
    }




}
