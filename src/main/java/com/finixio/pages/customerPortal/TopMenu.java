package com.finixio.pages.customerPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/25/2021
 * @project HFD-Automation
 */
public class TopMenu extends BaseMethods {

    @FindBy(linkText = "Dashboard")
    private WebElement dashBoard_lnk;

    @FindBy(id = "linkPayment")
    private WebElement goToPayments;

    @FindBy(linkText = "Profile")
    private WebElement goToProfile_lnk;

    @FindBy(id = "Feedback")
    private WebElement gotoFeedback_lnk;

    @FindBy(id = "btSignOut")
    private WebElement logout_Lnk;

    @Step("Customer going to or Clicking on the {0} from the {1} screen or page. ")
    public void gotoMenuSpecifiedByCustomer(String menuName , String fromPageName) throws Exception {
        // clicking on the menu link specified by the customer

        switch (menuName) {

            case "Dashboard":
                clickButton(dashBoard_lnk, "View DashBoard");
                break;
            case "Payment":
                clickButton(goToPayments, "View Payments");
                break;
            case "Profile":
                clickButton(goToProfile_lnk, "View Profile");
                break;
            case "Feedback":
                clickButton(gotoFeedback_lnk, "View FeedBack Items");
                break;
            case "Logout":
                clickButton(logout_Lnk, "Logout of Application");
                break;
            default:
                clickButton(dashBoard_lnk, "View DashBoard");
                break;


        }

    }


}
