package com.finexio.test.providers;

import com.finexio.BaseWeb;
import com.finexio.TestListener;
import com.finexio.pages.providerPortal.*;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.DateUtils;
import com.finexio.utils.NumberUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Katumba on 10/15/2021
 * @project HFD-Automation
 */
@Listeners({ TestListener.class })
public class ProviderServicesTests extends BaseWeb {

    Logger logger = LogManager.getLogger(getClass().getName());
    Map<String, String> applicationData = getApplicationData();
    Map<String, String> servicesData = new HashMap<>();
    NumberUtils numberUtils = new NumberUtils();
    DateUtils dateUtils = new DateUtils();
    public String serviceName = "All Testing";

    @Epic("Providers Experience")
    @Link(value = "", name = "", url = "", type = "")
    @Description("The ability of a providert  to create a service")
    @Test(groups = {
            "PatientPortal" }, description = "TC-2000 ::: Verify The ability of a providert  to create a service")
    @Story("HFD-12356 : The ability of a providert  to create a service")
    public void creatingandaddingaservice() throws Exception {

        SoftAssert sa;

        AssertionUtil assertionUtil = new AssertionUtil();
        startprovidersportal();
        ProviderLogin providerLogin = new ProviderLogin();
        NewServiceApplicationPage createservice = new NewServiceApplicationPage();
        WelcomePage welcomePage = new WelcomePage();
        CongulationsPage congulationsPage = new CongulationsPage();
        ApplicationsListPage applicationsListPage = new ApplicationsListPage();
        providerLogin.loginIntoProviderPortal(configuration().providerusername(), configuration().providerpassword());

        sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(), "Welcome!",
                "The page title is wrong");
        welcomePage.acceptCookiesPolicy();
        welcomePage.createServives();

        createservice.addService(serviceName, "2000", "Only Affordable to someone who can afford it.", "125");
        welcomePage.viewallservices();

    }

    @Epic("Providers Experience")
    @Link(value = "", name = "", url = "", type = "")
    @Description("The ability of a provider  to Search  and Edit a Service or Plan ")
    @Test(groups = {
            "ProviderPortal" }, description = "TC-2000 ::: Verify The ability of a provider  to Search  and Edit a Service or Plan")
    @Story("HFD-12356 : The ability of a provider  to Search  and Edit a Service or Plan")
    public void search_and_edit_a_service() throws Exception {

        SoftAssert sa;
        AssertionUtil assertionUtil = new AssertionUtil();
        startprovidersportal();
        ProviderLogin providerLogin = new ProviderLogin();
        NewServiceApplicationPage createservice = new NewServiceApplicationPage();
        WelcomePage welcomePage = new WelcomePage();
        CongulationsPage congulationsPage = new CongulationsPage();
        ApplicationsListPage applicationsListPage = new ApplicationsListPage();
        ViewServicesPage viewServicesPage = new ViewServicesPage();
        providerLogin.loginIntoProviderPortal(configuration().providerusername(), configuration().providerpassword());

        sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(), "Welcome!",
                "The page title is wrong");
        welcomePage.acceptCookiesPolicy();
        welcomePage.viewallservices();
        String service = viewServicesPage.searchServiceByName(serviceName);
        sa = assertionUtil.assertEquals("Verifying the searched service", service, serviceName,
                "The service name returned in the serach doesnt match");

        viewServicesPage.editService(serviceName, "3000", "Only Affordable to someone who can afford it.");

    }
}