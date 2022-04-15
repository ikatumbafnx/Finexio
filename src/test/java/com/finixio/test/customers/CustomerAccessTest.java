package com.finixio.test.customers;

import com.finixio.BaseWeb;
import com.finixio.TestListener;
import com.finixio.driver.DriverManager;
import com.finixio.pages.customerPortal.CustomerLoginPage;
import com.finixio.pages.customerPortal.TopMenu;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.NumberUtils;
import com.finixio.utils.PropertyUtils;
import com.finixio.web.base.Waiter;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.finixio.config.ConfigurationManager.configuration;

import java.util.Map;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
@Listeners({ TestListener.class })
public class CustomerAccessTest extends BaseWeb {

    PropertyUtils propertyUtils = new PropertyUtils();
    SoftAssert sa;
    AssertionUtil assertionUtil = new AssertionUtil();
    Logger logger = LogManager.getLogger(getClass().getName());
    Map<String, String> applicationData = getApplicationData();
    NumberUtils numberUtils = new NumberUtils();

    @Epic("Customer Portal")
    @Description("The ability of a well registered customer to access their account via Customers portal")
    @Test(groups = {
            "ProviderPortal" }, description = "TC-2000 ::: Verify The ability of a well registered customer to access their account via Customers portal")
    @Story("HFD-12356 : Verify The ability of a well registered customer to access their account via Customers portal")
    public void customersuccessTest() throws Exception {
        CustomerLoginPage customerLoginPage = new CustomerLoginPage();
        TopMenu topMenu = new TopMenu();
        startcustomerportal();
        customerLoginPage.loginIntoCustomerPortal("automation@test.com", "password");
        topMenu.gotoMenuSpecifiedByCustomer("Dashboard", "Dashboard");
        topMenu.gotoMenuSpecifiedByCustomer("Payment", "Payment");
        topMenu.gotoMenuSpecifiedByCustomer("Profile", "Profile");
        topMenu.gotoMenuSpecifiedByCustomer("Feedback", "Feedback");
        topMenu.gotoMenuSpecifiedByCustomer("Dashboard", "Dashboard");

        // ProviderLogin providerLogin = new ProviderLogin();
        // WelcomePage welcomePage = new WelcomePage();
        // providerLogin.loginIntoProviderPortal(configuration().providerusername(),
        // configuration().providerpassword());
        // SoftAssert sa = new SoftAssert();
        // sa.assertEquals(welcomePage.verifyPresenceOfHomePage(),"Welcome!");
        // sa.assertAll();

    }

}
