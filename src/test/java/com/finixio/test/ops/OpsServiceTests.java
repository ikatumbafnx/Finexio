package com.finixio.test.ops;

import static com.finixio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finixio.BaseWeb;
import com.finixio.driver.DriverManager;
import com.finixio.pages.opsPortal.LoginPage;
import com.finixio.pages.opsPortal.OpsTopMenu;
import com.finixio.pages.opsPortal.Applications.ApplicationSummaryPage;
import com.finixio.pages.opsPortal.Applications.BorrowersPage;
import com.finixio.pages.opsPortal.Applications.ManageApplications;
import com.finixio.pages.opsPortal.Providers.CreateProviderAccount;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.DateUtils;
import com.finixio.utils.NumberUtils;
import com.finixio.utils.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Story;

/**
 * @author Ivan Katumba on 11/9/2021
 * @project QAAutomation
 */
public class OpsServiceTests extends BaseWeb {

        Logger logger = LogManager.getLogger(getClass().getName());
        Map<String, String> customerData = new HashMap<>();
        NumberUtils numberUtils = new NumberUtils();
        StringUtil stringUtil = new StringUtil();
        DateUtils dateUtils = new DateUtils();
        Map<String, String> applicationData = getApplicationData();
        public HashMap<String, String> providerData;

        @Epic("OPS Portal Experience")
        @Description("The ability of an Authorized User in OPS  to create a Providers Account")
        @Test(priority = 0, groups = {
                        "OPSPortal" }, description = "The ability of an Authorized User in OPS  to create a Providers Account")
        @Link(name = "Azure Dev Ops", url = "https://dev.azure.com/healthcarefinancedirect/HFD/_workitems/edit/26")
        @Story("HFD-12356 : The ability of an Authorized User in OPS  to create a Providers Account")
        public void creatingProviderFromOPSPortal() throws Exception {
                LoginPage loginPage = new LoginPage();
                OpsTopMenu opsTopMenu = new OpsTopMenu();
                CreateProviderAccount createProviderAccount = new CreateProviderAccount();

                providerData = new HashMap<>();

                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startOPSportal();
                loginPage.loginIntoOpsPortal(configuration().opsusername(), configuration().opspassword());
                // String company = applicationData.get("BusinessName");
                opsTopMenu.goToSpecificMenu("Providers", "Create Provider Account", null);
                providerData.put("CompanyID", applicationData.get("CompanyID"));
                providerData.put("ProviderName", applicationData.get("BusinessName"));

                providerData.put("ContactName",
                                applicationData.get("FirstName") + " " + applicationData.get("LastName"));
                providerData.put("Email", applicationData.get("Email"));
                providerData.put("StreetAddress", applicationData.get("Street"));
                providerData.put("City", applicationData.get("CityName"));
                providerData.put("Country", "US");
                providerData.put("State", applicationData.get("State"));
                providerData.put("Zip", applicationData.get("ZipCode"));
                providerData.put("Phone", applicationData.get("Phone"));
                providerData.put("Fax", applicationData.get("Phone"));
                providerData.put("Username", applicationData.get("Email"));

                providerData.put("Password", "password");
                providerData.put("SecretQn", "What is Automation");
                providerData.put("SecretAns", "Automation is Key");
                providerData.put("Program", "Advantage");

                createProviderAccount.createProvidersAccount(providerData);

        }

        @Epic("OPS Portal Experience")
        @Description("The ability of a providert  to Log into OPS Portal and Access some links")
        @Test(priority = 1, groups = {
                        "PatientPortal" }, description = "TC-2000 ::: Verify The ability of a providert  to Log into OPS Portal and Access some links")
        @Story("HFD-12356 : The ability of a providert  to Log into OPS Portal and Access some links")
        public void loggingIntoTheOPSPortal() throws Exception {
                LoginPage loginPage = new LoginPage();
                OpsTopMenu opsTopMenu = new OpsTopMenu();
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startOPSportal();
                loginPage.loginIntoOpsPortal(configuration().opsusername(), configuration().opspassword());
                opsTopMenu.goToSpecificMenu("System", "Manage API Tokens", null);
                opsTopMenu.goToSpecificMenu("Reports", "Accounting", null);
                opsTopMenu.goToSpecificMenu("System", "Communications System", "View Communications Event Defs");
                opsTopMenu.goToSpecificMenu("System", "Logout...", null);

        }

        @Epic("OPS Portal Experience")
        @Description("Ops Portal - Wallet MS: Add/Update Card using apostrophe in Nickname")
        @Test(priority = 2, groups = {
                        "PatientPortal" }, description = "TC-29 ::: Ops Portal - Wallet MS: Add/Update Card using apostrophe in Nickname")
        @Link(name = "Test Case : 29 ", url = "https://dev.azure.com/healthcarefinancedirect/HFD/_workitems/edit/29")
        @Story("HFD-12356 : Wallet MS: Add/Update Card using apostrophe in Nickname")
        public void add_update_card_name_as_NickName() throws Exception {

                testcases.put("TestCaseID", "29");
                String appID = "5102586";
                LoginPage loginPage = new LoginPage();
                OpsTopMenu opsTopMenu = new OpsTopMenu();
                ManageApplications manageApplications = new ManageApplications();
                ApplicationSummaryPage applicationSummaryPage = new ApplicationSummaryPage();
                BorrowersPage borrowersPage = new BorrowersPage();
                Map<String, String> applicationData = getApplicationData();
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startOPSportal();
                loginPage.loginIntoOpsPortal(configuration().opsusername(), configuration().opspassword());
                opsTopMenu.goToSpecificMenu("Applications", "Manage Applications...", null);
                manageApplications.searchForTheAccountID(appID);
                DriverManager.waitForPageToLoad(30);

                sa = assertionUtil.assertEquals("Verifying that you are on the summary page",
                                manageApplications.verifyThatYouAreAtTheManageListPage(), "ServerMode",
                                "You are not on the List Page ");
                System.out.println(
                                "-------------------------" + manageApplications.verifyThatYouAreAtTheManageListPage());
                manageApplications.viewApplicationSummary(appID);

                sa = assertionUtil.assertEquals("Verifying that you are on the summary page",
                                applicationSummaryPage.verifyThatYouAreAtTheApplicantionsSummaryPage(appID), appID,
                                "You are not on the Application Summary Page for " + appID);
                opsTopMenu.goToSpecificMenu("Applications", "Borrowers...", null);
                sa = assertionUtil.assertEquals("Verifying that you are on the Borrowers page",
                                borrowersPage.verifyThatYouAreAtTheBorrowersSummaryPage(appID), appID,
                                "You are not on the Borrowers Summary Page for " + appID);
                borrowersPage.updateCardInfoForBorrower(
                                "Credit",
                                appID,
                                applicationData.get("FirstName") + "'" + applicationData.get("LastName"),
                                applicationData.get("CardNo"),
                                applicationData.get("CVV"),
                                "05",
                                "2022");

                sa.assertAll();

        }

        @Epic("OPS Portal Experience")
        @Description("Ops Portal - Wallet MS: Edit payment method expiration year")
        @Test(priority = 3, groups = {
                        "PatientPortal" }, description = "TC-28 ::: Ops Portal - Wallet MS: Edit payment method expiration year")
        @Link(name = "Test Case : 28 ", url = "https://dev.azure.com/healthcarefinancedirect/HFD/_workitems/edit/28")
        @Story("HFD-12356 : Wallet MS: Edit payment method expiration year")
        public void edit_payment_method_expiration_year() throws Exception {

                testcases.put("TestCaseID", "28");
                String appID = "5102586";
                LoginPage loginPage = new LoginPage();
                OpsTopMenu opsTopMenu = new OpsTopMenu();
                ManageApplications manageApplications = new ManageApplications();
                ApplicationSummaryPage applicationSummaryPage = new ApplicationSummaryPage();
                BorrowersPage borrowersPage = new BorrowersPage();
                Map<String, String> applicationData = getApplicationData();
                SoftAssert sa;
                AssertionUtil assertionUtil = new AssertionUtil();
                startOPSportal();
                loginPage.loginIntoOpsPortal(configuration().opsusername(), configuration().opspassword());
                opsTopMenu.goToSpecificMenu("Applications", "Manage Applications...", null);
                manageApplications.searchForTheAccountID(appID);
                DriverManager.waitForPageToLoad(30);

                sa = assertionUtil.assertEquals("Verifying that you are on the summary page",
                                manageApplications.verifyThatYouAreAtTheManageListPage(), "ServerMode",
                                "You are not on the List Page ");
                System.out.println(
                                "-------------------------" + manageApplications.verifyThatYouAreAtTheManageListPage());
                manageApplications.viewApplicationSummary(appID);

                sa = assertionUtil.assertEquals("Verifying that you are on the summary page",
                                applicationSummaryPage.verifyThatYouAreAtTheApplicantionsSummaryPage(appID), appID,
                                "You are not on the Application Summary Page for " + appID);
                opsTopMenu.goToSpecificMenu("Applications", "Borrowers...", null);
                sa = assertionUtil.assertEquals("Verifying that you are on the Borrowers page",
                                borrowersPage.verifyThatYouAreAtTheBorrowersSummaryPage(appID), appID,
                                "You are not on the Borrowers Summary Page for " + appID);
                borrowersPage.updateCardInfoForBorrower(
                                "Debit",
                                appID,
                                applicationData.get("FirstName") + "'" + applicationData.get("LastName"),
                                applicationData.get("CardNo"),
                                applicationData.get("CVV"),
                                "02",
                                "2022");

                sa.assertAll();

        }

}
