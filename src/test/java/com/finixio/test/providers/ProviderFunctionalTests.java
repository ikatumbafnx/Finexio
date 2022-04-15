package com.finixio.test.providers;

import static com.finixio.config.ConfigurationManager.configuration;

import java.util.Map;

import com.finixio.BaseWeb;
import com.finixio.TestListener;
import com.finixio.pages.customerPortal.CustomerLoginPage;
import com.finixio.pages.home.HomePage;
import com.finixio.pages.providerPortal.ApplicationsListPage;
import com.finixio.pages.providerPortal.ApplicationsPage;
import com.finixio.pages.providerPortal.CongulationsPage;
import com.finixio.pages.providerPortal.NewServiceApplicationPage;
import com.finixio.pages.providerPortal.PlanApplicationPage;
import com.finixio.pages.providerPortal.ProviderLogin;
import com.finixio.pages.providerPortal.ProviderTopMenu;
import com.finixio.pages.providerPortal.WelcomePage;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.NumberUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
@Listeners({ TestListener.class })
public class ProviderFunctionalTests extends BaseWeb {

        SoftAssert sa;
        AssertionUtil assertionUtil = new AssertionUtil();
        Logger logger = LogManager.getLogger(getClass().getName());
        Map<String, String> applicationData = getApplicationData();
        NumberUtils numberUtils = new NumberUtils();

        @Epic("Providers Experience")
        @Description("The ability of a well registered Provider to access their account via Providers portal")
        @Test(groups = { "ProviderPortal" }, description = "TC-2000 ::: Verify that a valid ability of a well registered Provider to access their account via Providers portal")
        @Story("HFD-12356 : Verify that a valid Patient can loginto with his username and Password")
        public void providerLogin() throws Exception {
                startprovidersportal();
                ProviderLogin providerLogin = new ProviderLogin();
                WelcomePage welcomePage = new WelcomePage();
                providerLogin.loginIntoProviderPortal(configuration().providerusername(),
                                configuration().providerpassword());
                SoftAssert sa = new SoftAssert();
                sa.assertEquals(welcomePage.verifyPresenceOfHomePage(), "Welcome!");
                sa.assertAll();

        }

        @Epic("Providers Experience")
        @Description("The ability of a well registered Patient to access their account via portal")
        @Test(groups = { "PatientPortal" }, description = "TC-2000 ::: Verify that a valid Patient can loginto with his username and Password")
        @Story("HFD-12356 : Verify that a valid Patient can loginto with his username and Password")
        public void providerLoginFromMainWebsite() throws Exception {
                startMainWebsite();
                HomePage homePage = new HomePage();
                WelcomePage welcomePage = new WelcomePage();
                CustomerLoginPage patientLoginPage = new CustomerLoginPage();
                homePage.accessPatientPortal();
                // patientLoginPage.fillEmail("bos@gmail.com");
                // patientLoginPage.fillPassword("E#ghate1236");
                patientLoginPage.loginIntoCustomerPortal("E#ghate1236", "E#ghate1236");
                SoftAssert sa = new SoftAssert();
                sa.assertEquals(welcomePage.verifyPresenceOfHomePage(), "Welcome!");
                sa.assertAll();

                // patientLoginPage.loginIntoPatientPortal("E#ghate1236","E#ghate1236");

        }

        @Epic("Providers Experience")
        @Description("The ability of a provider to create an account for the Patient")
        @Test(groups = { "PatientPortal" }, description = "TC-2000 ::: Verify that the provider can create an account for a patient")
        @Story("HFD-12356 : Creating an account for the provider")
        public void createEasyAppSubmission() throws Exception {
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startprovidersportal();
                ProviderLogin providerLogin = new ProviderLogin();
                WelcomePage welcomePage = new WelcomePage();
                CongulationsPage congulationsPage = new CongulationsPage();
                ApplicationsListPage applicationsListPage = new ApplicationsListPage();
                providerLogin.loginIntoProviderPortal(configuration().providerusername(),
                                configuration().providerpassword());

                sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(),
                                "Welcome!", "The page title is wrong");
                welcomePage.acceptCookiesPolicy();
                welcomePage.registerpatient();
                PlanApplicationPage planApplicationPage = new PlanApplicationPage();
                planApplicationPage.confirmOnApplicationPage();
                planApplicationPage.submitPatientApplication(applicationData.get("FirstName"),
                                applicationData.get("LastName"), applicationData.get("Email"),
                                applicationData.get("Phone"), applicationData.get("SSN"),
                                applicationData.get("PatientID"));

                congulationsPage.confirmAccountCreation();
                planApplicationPage.viewExistingApplications();
                sa = assertionUtil.assertEquals("Applications List Paged Title",
                                applicationsListPage.verifyAppIsonApplicationListScreen(), "Applications Sent ",
                                "The page title is wrong");

                sa.assertAll();
        }

        @Epic("Providers Experience")
        @Description("The ability of a providert  to create a service")
        @Test(groups = { "PatientPortal" }, description = "TC-2000 ::: Verify The ability of a providert  to create a service")
        @Story("HFD-12356 : The ability of a providert  to create a service")
        public void creatingandaddingaservice() throws Exception {
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startprovidersportal();
                ProviderLogin providerLogin = new ProviderLogin();
                NewServiceApplicationPage createservice = new NewServiceApplicationPage();
                WelcomePage welcomePage = new WelcomePage();
                providerLogin.loginIntoProviderPortal(configuration().providerusername(),
                                configuration().providerpassword());

                sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(),
                                "Welcome!", "The page title is wrong");
                welcomePage.acceptCookiesPolicy();
                welcomePage.createServives();

                createservice.addService("All Testing", "2000", "Only Affordable to someone who can afford it.", "125");
                welcomePage.viewallservices();
                implicitWait(20);

        }

        @Epic("Applications Portal")
        @Description("Verify The ability of a provider to review and read the existing applications")
        @Test( description = "The ability of a provider to filter the applications submitted")
        public void FilterApplicationsSubmitted() throws Exception {
                String filter = "Status";
                String criteria = "Approved";
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startprovidersportal();
                ProviderLogin providerLogin = new ProviderLogin();
                WelcomePage welcomePage = new WelcomePage();
                ProviderTopMenu providerTopMenu = new ProviderTopMenu();
                ApplicationsPage applicationsPage = new ApplicationsPage();
                providerLogin.loginIntoProviderPortal(configuration().providerusername(),
                                configuration().providerpassword());

                sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(),
                                "Welcome!", "The page title is wrong");
                
                welcomePage.acceptCookiesPolicy();
                providerTopMenu.goTogApplicationsPage();

                sa = assertionUtil.assertTrue(applicationsPage.verifyPresenceOfApplicationsPage(), "Applications page not found");

                applicationsPage.filteringApplicationsBy(filter, criteria);

                sa = assertionUtil.assertEquals("Applications filtered", applicationsPage.verifyApplicationsListedByCriteria(filter,criteria), criteria, "The filter was not applied correctly");
                sa.assertAll();
        }

        // @Epic("PI2")
        // @Description("The ability of a patient to create an account from
        // Origionation")
        // @Test(groups = {"PatientPortal"},description = "TC-2000 ::: Verify that the
        // provider can create an account for a patient")
        // @Story("HFD-12356 : Creating an account for the provider")
        // public void createPatientOriginationAccount() throws Exception {
        //
        // Integer last4ofSSN =
        // numberUtils.getFirstOrLastFromANumber(applicationData.get("SSN"),"last",4);
        // String email = applicationData.get("Email");
        // startOriginationPortal();
        // GetStartedPage getStartedPage = new GetStartedPage();
        // WhoIsApplyingFirstPage whoIsApplyingFirstPage = new WhoIsApplyingFirstPage();
        // ContactInfoPage contactInfoPage = new ContactInfoPage();
        // AddressPage addressPage = new AddressPage();
        // getStartedPage.getStarted(last4ofSSN.toString(),email);
        // // sa = assertionUtil.assertEquals("Who is applying for
        // financing?",whoIsApplyingFirstPage.confirmOnWhoisApplyingPage(),"Who is
        // applying for financing?","The page title is wrong");
        // whoIsApplyingFirstPage.addDetailofwhoisApplyingForFinancing("11","25","2002");
        // // sa = assertionUtil.assertEquals("How can we contact
        // you?",contactInfoPage.confirmOnContactPage(),"How can we contact you?","The
        // page title is wrong");
        // // contactInfoPage.addHowWeCanContactYou("t@t.com","5125636532");
        // //sa = assertionUtil.assertEquals("Where do you live & receive
        // mail?",addressPage.confirmOnAddressPage(),"Where do you live & receive
        // mail?","The page title is wrong");
        // // addressPage.addHowWeCanContactYou("123 Finnel
        // Cv","78737","Austin","Texas");
        // //sa.assertAll();
        // }

}
