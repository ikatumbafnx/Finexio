package com.finexio.test.webservices.FinanceOffers;

import static com.finexio.config.ConfigurationManager.apiConfiguration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.finexio.BaseApi;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.DateUtils;
import com.finexio.utils.api.JsonFormatter;
import com.finexio.utils.api.RestUtils;
import com.finexio.webservices.FinanceOffers.GetOffersRequest;
import com.finexio.webservices.FinanceOffers.GetSingleOfferRequest;
import com.finexio.webservices.FinanceOffers.SetOfferRequest;
import com.finexio.webservices.v3.ActivateApplicationRequest;
import com.finexio.webservices.v3.AuthenticateRequest;
import com.finexio.webservices.v3.CreateApplicationRequest;
import com.finexio.webservices.v3.CreatePaymentMethodsRequest;
import com.finexio.webservices.v3.ESignAgreementRequest;
import com.finexio.webservices.v3.EmailAgreementRequest;
import com.finexio.webservices.v3.GetAgreementRequest;
import com.finexio.webservices.v3.GetApplicationDetailsRequest;
import com.finexio.webservices.v3.UnderwrittingRequest;
import com.github.javafaker.Faker;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import helpers.DBHelpers;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Epic("Finance Offers Min Application Happy Path")
public class GetOffersWithSpecificSections extends BaseApi {

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
        GetOffersRequest getOffersRequest = new GetOffersRequest();
        GetSingleOfferRequest getSingleOfferRequest = new GetSingleOfferRequest();
        SetOfferRequest setOfferRequest = new SetOfferRequest();
        DateUtils dateUtils = new DateUtils();
        DBHelpers helpers = new DBHelpers();

        public String token, offerflag;
        public String AppilicationID;
        Faker faker = new Faker();
        Map<String, String> applicationData = getApplicationData();
        Map<Object, Object> accountDetailsData = new HashMap<>();
        Map<String, Object> offersData = new HashMap<>();
        Map<String, Object> setoffersData = new HashMap<>();
        Map<String, Object> financeOfferData = new HashMap<>();
        Map<String, Object> offerListings = new HashMap<>();
        AllureLifecycle lifecycle = Allure.getLifecycle();

        // public String endpointurl = envparam.get("baseEnv");
        // public String clientID = envparam.get("clientID");
        // public String clientSecret = envparam.get("clientSecret");
        // public String grantType = envparam.get("grantType");
        // public String authUrl = envparam.get("authUrl");

        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "v3" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-12356 : Authenticate Users to V3 API")
        public void generateToken() throws Exception {

                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "626");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                String json = null;

                service = apiConfiguration().v3Authenticate();
                AllureLifecycle lifecycle = Allure.getLifecycle();

                /**
                 * This provider used here Finance Offers with multiple sections
                 * (ex: Aligners and General Dentistry, each with their own respective offers)
                 */

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Getting Access Token or Authenticating Provider  15357"));

                if (envparam.get("env").equalsIgnoreCase("sandbox")) {
                        String offerflagValue = dbhelper.getUseFinancePlanFlag("14240");
                        if (offerflagValue.equalsIgnoreCase("0")) {

                                String rowNum = dbhelper.updateUseFinancePlanFlag("14240", 1);

                        }

                        json = v3GetToken.GetToken(envparam.get("grantType"), "04307206-f43d-41df-9a88-9d9e33e122ba",
                                        "yeVsQlYVU3qJLdjvM1ZfNA");

                } else {

                        json = v3GetToken.GetToken(envparam.get("grantType"), "b8d99f19-15f1-40ba-bd93-0151bda599dd",
                                        "vSMftQo5qYD5z-b9xHJgsAAwQXBI01fGD0BII9Cfbky-3-bNM1n0tJ-09TBIu3v6");
                }

                offerflag = "1";

                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service, envparam.get("authUrl"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Description("Creating a Financing Application")
        @Test(priority = 1, groups = { "v3" }, description = "Creating a Financing Application")
        @Story("HFD-8965 : Creating a Financing Application")
        public void createApplication() throws Exception {

                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "627");
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
                                firstPaymentDueDate, 18, 3000, apiConfiguration().locale_us(), "666946673",
                                offerflag);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);

                jsonFormatter.printFormattedJson(response.asString(), "response", service);

                JsonPath applicationData = response.jsonPath();
                AppilicationID = applicationData.get("id");
                accountDetailsData.put("id", AppilicationID);
                accountDetailsData.put("firstName", applicationData.get("applicant.firstName"));
                accountDetailsData.put("lastName", applicationData.get("applicant.lastName"));
                accountDetailsData.put("providerId", applicationData.get("providerId"));
                accountDetailsData.put("status", applicationData.get("status"));
                accountDetailsData.put("postalCode", applicationData.get("applicant.address.postalCode"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Creating an Application for " + accountDetailsData.get("firstName")));

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                sa.assertAll();

        }

        @Description("Underwritting the Financial Application")
        @Test(priority = 2, groups = { "v3" }, description = "Underwritting the Financial Application")
        @Story("HFD-39785 : Underwritting the Financial Application")
        public void underwrittingApplication() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "628");

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Underwritting Application " + AppilicationID));

                service = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                accountDetailsData.put("status", accountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                accountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("id"), AppilicationID,
                                "The Application ID ");
                sa.assertAll();

        }

        @Description("Getting Application Details to verify that Application is in Approved Status")
        @Test(priority = 3, groups = {
                        "v3" }, description = "Getting Application Details to verify that Application is in Approved Status")
        @Story("HFD-10236 : Get Application Details")
        public void getApplicationDetails() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "629");

                service = apiConfiguration().v3getApplicationDetails();

                json = getApplicationDetailsRequest.getAccountDetails(AppilicationID);

                try {
                        Thread.sleep(30000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Getting Application Details to verify that Application  " + AppilicationID
                                                + " is in Approved Status"));

                Response response = restUtils.getStringGETReponseNoContentType(json, service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

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

                sa.assertAll();

        }

        @Description("Get Offers for Provider, Passing Invalid Section")
        @Test(priority = 4, groups = {
                        "v3" }, description = "Get Offers for Provider, Passing Invalid Section")
        public void gettingAllOffersforAvailableFortheApplicant() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                JSONObject createApplicationObj = new JSONObject();

                createApplicationObj.put("ServiceCost", 2500);
                createApplicationObj.put("DownPaymentAmount", 500);
                createApplicationObj.put("Section", "GenDentistry");
                createApplicationObj.put("FinanceDate", "2023-03-01");
                createApplicationObj.put("FirstPaymentDueDate", "2023-03-20");

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("The specified section does not exist in the current Provider Config."));

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffersOnDemand(AppilicationID, createApplicationObj);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JSONObject offers = new JSONObject(response.asString());
                JsonPath accountData = response.jsonPath();

                financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                financeOfferData.put("downPaymentAmount", accountData.get("downPaymentAmount"));
                financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                financeOfferData.put("region", accountData.get("region"));
                financeOfferData.put("section", accountData.get("section"));

                financeOfferData.put("financeDate", accountData.get("financeDate"));
                financeOfferData.put("firstPaymentDueDate", accountData.get("firstPaymentDueDate"));
                financeOfferData.put("applicationId", accountData.get("applicationId"));

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                System.out.println("The List of Offer is: " + offerListings);

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "400",
                                "Call returned Error");

                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("The Invalid error message of ",
                                accountData.get("message"),
                                "The specified section does not exist in the current Provider Config.",
                                "The Invalid Error ");
                sa.assertAll();

        }

        @Description("Get Offers for Provider Without Passing Required Section")
        @Test(priority = 5, groups = {
                        "v3" }, description = "Get Offers for Provider Without Passing Required Section")
        public void gettingAllOffersWithoutSections() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;

                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                JSONObject createApplicationObj = new JSONObject();

                createApplicationObj.put("ServiceCost", 2500);
                createApplicationObj.put("DownPaymentAmount", 500);
                // createApplicationObj.put("Section", "GenDentistry");
                createApplicationObj.put("FinanceDate", "2023-03-01");
                createApplicationObj.put("FirstPaymentDueDate", "2023-03-20");

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Section not specified in the request. Current provider requires a section to calculate offers."));

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffersOnDemand(AppilicationID, createApplicationObj);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JSONObject offers = new JSONObject(response.asString());
                JsonPath accountData = response.jsonPath();

                financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                financeOfferData.put("downPaymentAmount", accountData.get("downPaymentAmount"));
                financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                financeOfferData.put("region", accountData.get("region"));
                financeOfferData.put("section", accountData.get("section"));

                financeOfferData.put("financeDate", accountData.get("financeDate"));
                financeOfferData.put("firstPaymentDueDate", accountData.get("firstPaymentDueDate"));
                financeOfferData.put("applicationId", accountData.get("applicationId"));

                System.out.println("The List of Offer is: " + offerListings);

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "400",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("The Invalid error message of ",
                                accountData.get("message"),
                                "Section not specified in the request. Current provider requires a section to calculate offers.",
                                "The Invalid Error ");
                sa.assertAll();

        }

}
