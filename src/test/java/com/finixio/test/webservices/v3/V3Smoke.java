package com.finixio.test.webservices.v3;

import static com.finixio.config.ConfigurationManager.apiConfiguration;

import java.util.HashMap;
import java.util.Map;

import com.finixio.BaseApi;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.DateUtils;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;
import com.finixio.webservices.v3.ActivateApplicationRequest;
import com.finixio.webservices.v3.AuthenticateRequest;
import com.finixio.webservices.v3.CreateApplicationRequest;
import com.finixio.webservices.v3.CreatePaymentMethodsRequest;
import com.finixio.webservices.v3.ESignAgreementRequest;
import com.finixio.webservices.v3.EmailAgreementRequest;
import com.finixio.webservices.v3.GetAgreementRequest;
import com.finixio.webservices.v3.GetApplicationDetailsRequest;
import com.finixio.webservices.v3.UnderwrittingRequest;
import com.github.javafaker.Faker;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import helpers.DBHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
@Feature("V3 Min Application")
public class V3Smoke extends BaseApi {
        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        AuthenticateRequest v3GetToken = new AuthenticateRequest();
        CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
        GetApplicationDetailsRequest getApplicationDetailsRequest = new GetApplicationDetailsRequest();
        UnderwrittingRequest underwrittingRequest = new UnderwrittingRequest();
        CreatePaymentMethodsRequest createPaymentMethodsRequest = new CreatePaymentMethodsRequest();
        GetAgreementRequest getAgreementRequest = new GetAgreementRequest();
        ESignAgreementRequest eSignAgreementRequest = new ESignAgreementRequest();
        EmailAgreementRequest emailAgreementRequest = new EmailAgreementRequest();
        ActivateApplicationRequest activateApplicationRequest = new ActivateApplicationRequest();
        DateUtils dateUtils = new DateUtils();
        DBHelpers dbhelper = new DBHelpers();

        private String token;
        private String AppilicationID;
        Faker faker = new Faker();
        Map<String, String> applicationData = getApplicationData();
        Map<Object, Object> accountDetailsData = new HashMap<>();

        // public String endpointurl = envparam.get("baseEnv");
        // public String clientID = envparam.get("clientID");
        // public String clientSecret = envparam.get("clientSecret");
        // public String grantType = envparam.get("grantType");
        // public String authUrl = envparam.get("authUrl");

        @Epic("V3 API Happy Path")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "v3" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-12356 : Authenticate Users to V3 API")
        public void generateToken() throws Exception {

                testcases.put("TestCaseID", "555");
                testcases.put("TestSuiteID", "501");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                String json = null;
                System.out.println(envparam.get("env"));
                // String offerflagValue = dbhelper.getUseFinancePlanFlag("15356");
                // if (offerflagValue.equalsIgnoreCase("1")) {

                // String rowNum = dbhelper.updateUseFinancePlanFlag("15356", 0);

                // }
                service = apiConfiguration().v3Authenticate();

                if (envparam.get("env").equalsIgnoreCase("sandbox")) {

                        json = v3GetToken.GetToken(envparam.get("grantType"), envparam.get("clientID_QA_15356"),
                                        envparam.get("clientSecret_QA_15356"));

                } else {

                        json = v3GetToken.GetToken(envparam.get("grantType"), envparam.get("clientID_QADEV_15356"),
                                        envparam.get("clientSecret_QADEV_15356"));

                }

                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service,
                                envparam.get("authUrl"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Creating a Financing Application")
        @Test(priority = 1, groups = { "v3" }, description = "Creating a Financing Application")
        @Story("HFD-8965 : Creating a Financing Application")
        public void createApplication() throws Exception {
                // testcases.put("TestCaseID", "34");
                testcases.put("TestCaseID", "556");
                testcases.put("TestSuiteID", "501");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                service = apiConfiguration().v3createapplication();

                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                json = createApplicationRequest.CreateApplication(applicationData.get("FirstName"),
                                applicationData.get("LastName"), applicationData.get("Email"), "+1-202-555-0175",
                                "1999-11-08", applicationData.get("Street"), applicationData.get("CityName"),
                                applicationData.get("State"), applicationData.get("ZipCode"), "US", FinanceDate,
                                firstPaymentDueDate, 18, 2000, apiConfiguration().locale_us(), "666946673",
                                "0");
                System.out.println("The Offer Flag is set to " + envparam.get("financeOfferFlag"));
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath applicationData = response.jsonPath();

                accountDetailsData.put("id", AppilicationID);
                accountDetailsData.put("firstName", applicationData.get("applicant.firstName"));
                accountDetailsData.put("lastName", applicationData.get("applicant.lastName"));
                accountDetailsData.put("providerId", applicationData.get("providerId"));
                accountDetailsData.put("status", applicationData.get("status"));
                accountDetailsData.put("postalCode", applicationData.get("applicant.address.postalCode"));

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "201",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                AppilicationID = applicationData.get("id");
                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Getting Application Details to verify that Application is in Pending Status")
        @Test(priority = 2, groups = {
                        "v3" }, description = "Getting Application Details to verify that Application is in Pending Status")
        @Story("HFD-10236 : Get Application Details")
        public void getApplicationDetails() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "558");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3getApplicationDetails();

                json = getApplicationDetailsRequest.getAccountDetails(AppilicationID);

                Response response = restUtils.getStringGETReponseNoContentType(json, service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Application ID", accountData.get("id"), AppilicationID,
                                "The Application ID ");
                sa = assertionUtil.assertEquals("Applicant Status", accountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status  ");
                sa = assertionUtil.assertEquals("Applicant First Name", accountData.get("applicant.firstName"),
                                accountDetailsData.get("firstName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("Applicant LastName", accountData.get("applicant.lastName"),
                                accountDetailsData.get("lastName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("The providerId", accountData.get("providerId"),
                                accountDetailsData.get("providerId"), "The providerId  ");
                // sa = assertionUtil.assertEquals("Applicant
                // NIN",accountData.get("Applicant.NIN"),accountDetailsData.get("NIN"),"The
                // Application NIN ");
                // sa = assertionUtil.assertEquals("Applicant
                // Phone",accountData.get("Applicant.Phone"),accountDetailsData.get("Phone"),"The
                // Application Phone ");
                // sa = assertionUtil.assertEquals("Applicant
                // Street",accountData.get("Applicant.Address.Street"),accountDetailsData.get("Street"),"The
                // Applicant Street ");
                // sa = assertionUtil.assertEquals("Applicant Zip
                // Code",accountData.get("Applicant.Address.Zip"),accountDetailsData.get("Zip"),"The
                // Applicant Zip ");
                // sa = assertionUtil.assertEquals("Applicant
                // CountryCode",accountData.get("Applicant.Address.CountryCode"),accountDetailsData.get("CountryCode"),"The
                // Applicant CountryCode ");
                // sa = assertionUtil.assertEquals("PersonReceivingServices
                // FirstName",accountData.get("PersonReceivingServices.FirstName"),accountDetailsData.get("PersonReceivingServicesFirstName"),"The
                // PersonReceivingServicesFirstName ");
                // sa = assertionUtil.assertEquals("PersonReceivingServices
                // LastName",accountData.get("PersonReceivingServices.LastName"),accountDetailsData.get("PersonReceivingServicesLastName"),"The
                // PersonReceivingServicesLastName ");
                // sa = assertionUtil.assertEquals("Applicant Principal Loan
                // Amount",accountData.get("Terms.PrincipalAmount").toString(),accountDetailsData.get("PrincipalAmount").toString(),"The
                // Applicant PrincipalAmount ");
                // sa = assertionUtil.assertEquals("Applicant
                // State",accountData.get("Terms.Statoid"),accountDetailsData.get("Statoid"),"The
                // Applicant Statoid ");
                // sa = assertionUtil.assertEquals("Applicant Payment
                // TermMonths",accountData.get("Terms.TermMonths"),accountDetailsData.get("TermMonths"),"The
                // Applicant TermMonths ");
                //
                // sa = assertionUtil.assertEquals("Applicant Finance
                // Date",accountData.get("Terms.FinanceDate"),accountDetailsData.get("FinanceDate"),"The
                // Applicant FinanceDate ");
                // sa = assertionUtil.assertEquals("Applicant First Payment
                // DueDate",accountData.get("Terms.FirstPaymentDueDate"),accountDetailsData.get("FirstPaymentDueDate"),"The
                // Applicant FirstPaymentDueDate ");
                //
                // sa = assertionUtil.assertEquals("Applicant
                // NameOnCard",accountData.get("PrimaryPaymentMethod.Card.NameOnCard"),accountDetailsData.get("NameOnCard"),"The
                // Applicant NameOnCard ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardFirstName",accountData.get("PrimaryPaymentMethod.Card.CardFirstName"),accountDetailsData.get("CardFirstName"),"The
                // Applicant CardFirstName ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardLastName",accountData.get("PrimaryPaymentMethod.Card.CardLastName"),accountDetailsData.get("CardLastName"),"The
                // Applicant CardLastName ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardNumber",accountData.get("PrimaryPaymentMethod.Card.CardNumber"),accountDetailsData.get("CardNumber"),"The
                // Applicant CardNumber ");
                // sa = assertionUtil.assertEquals("Card
                // ExpirationMonth",accountData.get("PrimaryPaymentMethod.Card.ExpirationMonth"),accountDetailsData.get("ExpirationMonth"),"The
                // Card ExpirationMonth ");
                // sa = assertionUtil.assertEquals("Card
                // ExpirationYear",accountData.get("PrimaryPaymentMethod.Card.ExpirationYear"),accountDetailsData.get("ExpirationYear"),"The
                // Card ExpirationYear ");

                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Underwritting the Financial Application")
        @Test(priority = 3, groups = { "v3" }, description = "Underwritting the Financial Application")
        @Story("HFD-39785 : Underwritting the Financial Application")
        public void underwrittingApplication() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "557");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                accountDetailsData.put("status", accountData.get("status"));
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Applicantion Status",
                                accountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("id"), AppilicationID,
                                "The Application ID ");
                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Getting Application Details to verify that Application is in Approved Status")
        @Test(priority = 4, groups = {
                        "v3" }, description = "Getting Application Details to verify that Application is in Approved Status")
        @Story("HFD-10236 : Get Application Details")
        public void getApplicationDetailsToVerrifyApproved() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "558");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3getApplicationDetails();

                json = getApplicationDetailsRequest.getAccountDetails(AppilicationID);

                try {
                        Thread.sleep(30000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                Response response = restUtils.getStringGETReponseNoContentType(json, service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Application ID", accountData.get("id"), AppilicationID,
                                "The Application ID ");
                sa = assertionUtil.assertEquals("Applicant Status", accountData.get("status"),
                                "approved", "The Application Status  ");
                sa = assertionUtil.assertEquals("Applicant First Name", accountData.get("applicant.firstName"),
                                accountDetailsData.get("firstName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("Applicant LastName", accountData.get("applicant.lastName"),
                                accountDetailsData.get("lastName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("The providerId", accountData.get("providerId"),
                                accountDetailsData.get("providerId"), "The providerId  ");
                // sa = assertionUtil.assertEquals("Applicant
                // NIN",accountData.get("Applicant.NIN"),accountDetailsData.get("NIN"),"The
                // Application NIN ");
                // sa = assertionUtil.assertEquals("Applicant
                // Phone",accountData.get("Applicant.Phone"),accountDetailsData.get("Phone"),"The
                // Application Phone ");
                // sa = assertionUtil.assertEquals("Applicant
                // Street",accountData.get("Applicant.Address.Street"),accountDetailsData.get("Street"),"The
                // Applicant Street ");
                // sa = assertionUtil.assertEquals("Applicant Zip
                // Code",accountData.get("Applicant.Address.Zip"),accountDetailsData.get("Zip"),"The
                // Applicant Zip ");
                // sa = assertionUtil.assertEquals("Applicant
                // CountryCode",accountData.get("Applicant.Address.CountryCode"),accountDetailsData.get("CountryCode"),"The
                // Applicant CountryCode ");
                // sa = assertionUtil.assertEquals("PersonReceivingServices
                // FirstName",accountData.get("PersonReceivingServices.FirstName"),accountDetailsData.get("PersonReceivingServicesFirstName"),"The
                // PersonReceivingServicesFirstName ");
                // sa = assertionUtil.assertEquals("PersonReceivingServices
                // LastName",accountData.get("PersonReceivingServices.LastName"),accountDetailsData.get("PersonReceivingServicesLastName"),"The
                // PersonReceivingServicesLastName ");
                // sa = assertionUtil.assertEquals("Applicant Principal Loan
                // Amount",accountData.get("Terms.PrincipalAmount").toString(),accountDetailsData.get("PrincipalAmount").toString(),"The
                // Applicant PrincipalAmount ");
                // sa = assertionUtil.assertEquals("Applicant
                // State",accountData.get("Terms.Statoid"),accountDetailsData.get("Statoid"),"The
                // Applicant Statoid ");
                // sa = assertionUtil.assertEquals("Applicant Payment
                // TermMonths",accountData.get("Terms.TermMonths"),accountDetailsData.get("TermMonths"),"The
                // Applicant TermMonths ");
                //
                // sa = assertionUtil.assertEquals("Applicant Finance
                // Date",accountData.get("Terms.FinanceDate"),accountDetailsData.get("FinanceDate"),"The
                // Applicant FinanceDate ");
                // sa = assertionUtil.assertEquals("Applicant First Payment
                // DueDate",accountData.get("Terms.FirstPaymentDueDate"),accountDetailsData.get("FirstPaymentDueDate"),"The
                // Applicant FirstPaymentDueDate ");
                //
                // sa = assertionUtil.assertEquals("Applicant
                // NameOnCard",accountData.get("PrimaryPaymentMethod.Card.NameOnCard"),accountDetailsData.get("NameOnCard"),"The
                // Applicant NameOnCard ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardFirstName",accountData.get("PrimaryPaymentMethod.Card.CardFirstName"),accountDetailsData.get("CardFirstName"),"The
                // Applicant CardFirstName ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardLastName",accountData.get("PrimaryPaymentMethod.Card.CardLastName"),accountDetailsData.get("CardLastName"),"The
                // Applicant CardLastName ");
                // sa = assertionUtil.assertEquals("Applicant
                // CardNumber",accountData.get("PrimaryPaymentMethod.Card.CardNumber"),accountDetailsData.get("CardNumber"),"The
                // Applicant CardNumber ");
                // sa = assertionUtil.assertEquals("Card
                // ExpirationMonth",accountData.get("PrimaryPaymentMethod.Card.ExpirationMonth"),accountDetailsData.get("ExpirationMonth"),"The
                // Card ExpirationMonth ");
                // sa = assertionUtil.assertEquals("Card
                // ExpirationYear",accountData.get("PrimaryPaymentMethod.Card.ExpirationYear"),accountDetailsData.get("ExpirationYear"),"The
                // Card ExpirationYear ");

                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Adding Payment Method to the Application")
        @Test(priority = 5, groups = { "v3" }, description = "Adding Payment Method to the Application")
        @Story("HFD-39785 : Adding Payment Method to the Application")
        public void addingPaymentMethods() throws Exception {

                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "559");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response response = restUtils.getAuthentPOSTReponse(json, service,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                accountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                accountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");
                sa.assertAll();

        }

        @Epic("V3 API Happy Path")
        @Description("Getting and Displaying Agreement to applicant")
        @Test(priority = 6, groups = { "v3" }, description = "Getting and Displaying Agreement to applicant")

        @Story("HFD-39785 : Getting and Displaying Agreement to applicant")
        public void retrievingAgreement() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "560");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3getAgreement();
                json = getAgreementRequest.getAgreement(AppilicationID);
                Response response = restUtils.getStringGETReponseNoContentType(json, service,
                                envparam.get("baseEnv"), "Bearer " + token);

                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Applicantion Status", response.statusCode(),
                                200,
                                "The Application Status ");

        }

        @Epic("V3 API Happy Path")
        @Description("Applicant ESigning the Agreement")
        @Test(priority = 7, groups = { "v3" }, description = "Applicant ESigning theAgreement")
        @Story("HFD-39785 : Applicant ESigning the Agreement")
        public void eSigningAgreement()
                        throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "561");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3eSignAgreement();
                json = eSignAgreementRequest.eSignAgreement(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                200,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant First Name",
                                accountData.get("applicant.firstName"),
                                accountDetailsData.get("firstName"), "The Application FirstName ");
                sa = assertionUtil.assertEquals("Applicant LastName",
                                accountData.get("applicant.lastName"),
                                accountDetailsData.get("lastName"), "The Application FirstName ");
                sa = assertionUtil.assertEquals("The providerId",
                                accountData.get("providerId"),
                                accountDetailsData.get("providerId"), "The providerId ");

                sa.assertAll();
        }

        @Epic("V3 API Happy Path")
        @Description("EMailing ESiggned Agreement to the Applicant")
        @Test(priority = 8, groups = { "v3" }, description = "EMailing ESiggned Agreement to the Applicant")
        @Story("HFD-39785 : EMailing ESiggned Agreement to the Applicant")
        public void eMailingAgreement() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "562");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3emailAgreement();
                json = emailAgreementRequest.eMailAgreement(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                204,
                                "The Application Status ");
                sa.assertAll();
        }

        @Epic("V3 API Happy Path")
        @Description("Activating the Account of the Applicant")
        @Test(priority = 9, groups = { "v3" }, description = "Activating the Account of the Applicant")
        @Story("HFD-39785 : Activating the Account of the Applicant")
        public void activatingAccount() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestCaseID", "563");
                testcases.put("TestSuiteID", "501");
                service = apiConfiguration().v3activateApplication();
                json = activateApplicationRequest.activateAccount(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                200,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicantion Status",
                                accountData.get("status"), "activated",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant First Name",
                                accountData.get("applicant.firstName"),
                                accountDetailsData.get("firstName"), "The Application FirstName ");
                sa = assertionUtil.assertEquals("Applicant LastName",
                                accountData.get("applicant.lastName"),
                                accountDetailsData.get("lastName"), "The Application FirstName ");
                sa = assertionUtil.assertEquals("The providerId",
                                accountData.get("providerId"),
                                accountDetailsData.get("providerId"), "The providerId ");

                sa.assertAll();
        }

}
