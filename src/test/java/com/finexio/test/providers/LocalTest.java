package com.finexio.test.providers;

import com.finexio.BaseWeb;
import com.finexio.pages.customerPortal.CustomerLinkVerificationPage;
import com.finexio.pages.customerPortal.CustomerLoginPage;
import com.finexio.pages.customerPortal.TopMenu;
import com.finexio.pages.originationPortal.*;
import com.finexio.pages.providerPortal.*;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.DateUtils;
import com.finexio.utils.NumberUtils;

import helpers.DBHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.finexio.config.ConfigurationManager.configuration;
import static com.finexio.driver.DriverManager.waitForPageToLoad;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivan Katumba on 10/27/2021
 * @project HFD-Automation
 */
public class LocalTest extends BaseWeb {
        Logger logger = LogManager.getLogger(getClass().getName());
        Map<String, String> applicationData = getApplicationData();
        Map<String, String> customerData = new HashMap<>();
        NumberUtils numberUtils = new NumberUtils();
        DateUtils dateUtils = new DateUtils();
        DBHelpers dbHelpers = new DBHelpers();

        @Epic("PI2")
        @Description("The ability of a provider to create an account for the Patient")
        @Test(priority = 0, groups = {
                        "PatientPortal" }, description = "TC-2000 ::: Verify that the provider can create an account for a patient")
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
                customerData.put("SSN", applicationData.get("SSN"));
                customerData.put("Email", applicationData.get("Email"));
                congulationsPage.confirmAccountCreation();
                planApplicationPage.viewExistingApplications();
                // sa = assertionUtil.assertEquals("Applications List Paged
                // Title",applicationsListPage.verifyAppIsonApplicationListScreen(),"Applications
                // Sent ","The page title is wrong");

                sa.assertAll();

        }

        @Epic("PI2")
        @Description("The ability of a provider to create an account for the Patient in the origination Portal")
        @Test(priority = 1, groups = {
                        "PatientPortal" }, description = "TC-2000 ::: The ability of a provider to create an account for the Patient in the origination Portal")
        @Story("HFD-12356 : Registering Customer in Origination Portal")
        public void RegisterCustomerInOriginationPortal() throws Exception {
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();

                Integer last4ofSSN = numberUtils.getFirstOrLastFromANumber(customerData.get("SSN"), "last", 4);
                String fullName = applicationData.get("FirstName") + " " + applicationData.get("LastName");
                String email = applicationData.get("Email");
                startOriginationPortal();
                GetStartedPage getStartedPage = new GetStartedPage();
                WhoIsApplyingFirstPage whoIsApplyingFirstPage = new WhoIsApplyingFirstPage();
                ContactInfoPage contactInfoPage = new ContactInfoPage();
                AddressPage addressPage = new AddressPage();
                PaymentInformationPage paymentInformationPage = new PaymentInformationPage();

                getStartedPage.getStarted(last4ofSSN.toString(), email);
                // sa = assertionUtil.assertEquals("Who is applying for
                // financing?",whoIsApplyingFirstPage.confirmOnWhoisApplyingPage(),"Who is
                // applying for financing?","The page title is wrong");
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();
                int year = localDate.getYear() - 21;
                String DOB = month + "/" + day + "/" + year;
                customerData.put("DOB", DOB);
                customerData.put("Email", email);
                customerData.put("FullName", fullName);
                whoIsApplyingFirstPage.addDetailofwhoisApplyingForFinancing(String.valueOf(month), String.valueOf(day),
                                String.valueOf(year));
                // sa = assertionUtil.assertEquals("How can we contact
                // you?",contactInfoPage.confirmOnContactPage(),"How can we contact you?","The
                // page title is wrong");
                contactInfoPage.continueToAddressScreen();

                // sa = assertionUtil.assertEquals("Where do you live & receive
                // mail?",addressPage.confirmOnAddressPage(),"Where do you live & receive
                // mail?","The page title is wrong");
                addressPage.addHowWeCanContactYou("123 Finnel Cv", "78737", "Austin", "Texas");

                waitForPageToLoad(45);
                paymentInformationPage.addPaymentInformation(fullName, applicationData.get("CardNo"), "10", "2023",
                                applicationData.get("CVV")

                );

                CreateCustomerOnlineAccount createCustomerOnlineAccount = new CreateCustomerOnlineAccount();
                createCustomerOnlineAccount.creatHFDCustomerOnlineAccount(customerData.get("Email"),
                                applicationData.get("******"));
                String onlinesuccesmessage = createCustomerOnlineAccount.verifyOnlineAccountCreateMessage();
                sa = assertionUtil.assertEquals("Successfully created an account! ", onlinesuccesmessage,
                                "Successfully created an account!", "The Success Message");
                sa.assertAll();

        }

        // @Epic("PI2")
        // @Description("The ability of a customer to create an Online Account from the
        // Customers Portal")
        // @Test(priority = 2, groups = {"PatientPortal"},description = "TC-2000 ::: The
        // ability of a customer to create an Online Account from the Customers Portal")
        // @Story("HFD-12356 : Create a Customer Online Account from Customers Portal")
        // public void customerCreatesAnOnlineAccount() throws Exception {
        //
        // SoftAssert sa;
        // AssertionUtil assertionUtil = new AssertionUtil();
        // CreateCustomerOnlineAccount createCustomerOnlineAccount = new
        // CreateCustomerOnlineAccount();
        // createCustomerOnlineAccount.creatHFDCustomerOnlineAccount(customerData.get("Email"),applicationData.get("******"));
        // String onlinesuccesmessage =
        // createCustomerOnlineAccount.verifyOnlineAccountCreateMessage();
        // sa = assertionUtil.assertEquals("Successfully created an account!
        // ",onlinesuccesmessage,"Successfully created an account! ","The Succeful
        // Message");
        // }

        // @Epic("PI2")
        // @Description("The ability of a customer to create an Online Account from the
        // Customers Portal")
        // @Test(priority = 2, groups = {"PatientPortal"},description = "TC-2000 ::: The
        // ability of a customer to create an Online Account from the Customers Portal")
        // @Story("HFD-12356 : Create a Customer Online Account from Customers Portal")
        // public void customerCreatesAnOnlineAccount() throws Exception {
        //
        // CustomerLoginPage customerLoginPage = new CustomerLoginPage();
        // AccountCreatedPage accountCreatedPage = new AccountCreatedPage();
        // AccountCreation accountCreation = new AccountCreation();
        // AccountCreationStep2 accountCreationStep2 = new AccountCreationStep2();
        // SoftAssert sa;
        // AssertionUtil assertionUtil = new AssertionUtil();
        // startcustomerportal();
        // customerLoginPage.createOnlineAccount();
        // accountCreation.createAccountStep1(customerData.get("DOB"),customerData.get("Email"));
        // accountCreationStep2.createAccountFinalStep(applicationData.get("******"),applicationData.get("******"),applicationData.get("******"));
        // sa = assertionUtil.assertEquals("You're all
        // set!",accountCreatedPage.verifyCustomerCreated(),"You're all set!","The
        // Congulations Message");
        // }
        //
        //
        @Epic("Customer Portal")
        @Description("The ability of a well registered customer to access their account via Customers portal")
        @Test(priority = 2, groups = {
                        "ProviderPortal" }, description = "TC-2000 ::: Verify The ability of a well registered customer to access their account via Customers portal")
        @Story("HFD-12356 : Verify The ability of a well registered customer to access their account via Customers portal")
        public void customerverifyingonlineaccount() throws Exception {
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                CustomerLinkVerificationPage customerLinkVerificationPage = new CustomerLinkVerificationPage();
                List<String> patientData = dbHelpers.getValidationUserID(customerData.get("Email"));
                String applicationID = patientData.get(1);
                customerData.put("ApplicationID", applicationID);
                verifyaccountviaemail(customerData.get("FullName"), customerData.get("Email"));
                String pageTittle = customerLinkVerificationPage.verifyAccountVerified();
                sa = assertionUtil.assertEquals("You're all set!", pageTittle, "You're all set!", "The Page Title ");
                customerLinkVerificationPage.jumpInAndLogintoCustomerAccount();
                sa.assertAll();
        }

        @Epic("PI2")
        @Description("The ability of a Provider to activate")
        @Test(priority = 3, groups = {
                        "PatientPortal" }, description = "TC-2000 ::: The ability of a customer to create an Online Account from the Customers Portal")
        @Story("HFD-12356 : Create a Customer Online Account from Customers Portal")
        public void providerActivatingthePatientAccount() throws Exception {

                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startprovidersportal();
                ProviderLogin providerLogin = new ProviderLogin();
                WelcomePage welcomePage = new WelcomePage();
                PendingPaymentsPlanPage pendingPaymentsPlanPage = new PendingPaymentsPlanPage();
                // ProviderTopMenu providerTopMenu = new ProviderTopMenu();
                String customerID = "4930503";// customerData.get("ApplicationID");
                ApplicationsListPage applicationsListPage = new ApplicationsListPage();
                providerLogin.loginIntoProviderPortal(configuration().providerusername(),
                                configuration().providerpassword());
                // providerTopMenu.goToPendingApplications();
                sa = assertionUtil.assertEquals("Welcome Page Title", welcomePage.verifyPresenceOfHomePage(),
                                "Welcome!", "The page title is wrong");
                welcomePage.acceptCookiesPolicy();
                // sa = assertionUtil.assertEquals("Applications List Paged
                // Title",applicationsListPage.verifyAppIsonApplicationListScreen(),"Applications
                // Sent ","The page title is wrong");
                welcomePage.viewPendingApplicationsReadyForActivation();
                pendingPaymentsPlanPage.findPendingApplicationsReadyForActivation(customerID);
                sa.assertAll();
        }

        @Epic("Customer Portal")
        @Description("The ability of a well registered customer to Verify their online account via Email Link")
        @Test(priority = 4, groups = {
                        "ProviderPortal" }, description = "TC-2000 ::: Verify The ability The ability of a well registered customer to Verify their online account via Email Linkl")
        @Story("HFD-12356 : Verify The ability The ability of a well registered customer to Verify their online account via Email Link")
        public void customersuccessTest() throws Exception {
                CustomerLoginPage customerLoginPage = new CustomerLoginPage();
                com.finexio.pages.customerPortal.TopMenu topMenu = new TopMenu();
                startcustomerportal();
                customerLoginPage.loginIntoCustomerPortal(customerData.get("Email"), applicationData.get("******"));
                topMenu.gotoMenuSpecifiedByCustomer("Dashboard", "Dashboard");
                topMenu.gotoMenuSpecifiedByCustomer("Payment", "Payment");
                topMenu.gotoMenuSpecifiedByCustomer("Profile", "Profile");
                topMenu.gotoMenuSpecifiedByCustomer("Feedback", "Feedback");
                topMenu.gotoMenuSpecifiedByCustomer("Dashboard", "Dashboard");
        }

}
