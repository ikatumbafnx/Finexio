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

import org.json.JSONArray;
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
public class MinApplicationHappyPathTest extends BaseApi {

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
                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Getting Access Token or Authenticating Provider  15357"));

                if (envparam.get("env").equalsIgnoreCase("sandbox")) {
                        String offerflagValue = dbhelper.getUseFinancePlanFlag("15357");
                        if (offerflagValue.equalsIgnoreCase("0")) {

                                String rowNum = dbhelper.updateUseFinancePlanFlag("15357", 1);

                        }

                        json = v3GetToken.GetToken(envparam.get("grantType"), envparam.get("clientID_QA_15357"),
                                        envparam.get("clientSecret_QA_15357"));

                } else {

                        json = v3GetToken.GetToken(envparam.get("grantType"), envparam.get("clientID_QADEV_15357"),
                                        envparam.get("clientSecret_QADEV_15357"));
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
                        Thread.sleep(10000);
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

        @Description("Getting All Offers available for the Financial Application")
        @Test(priority = 4, groups = {
                        "v3" }, description = "Getting All Offers available for the Financial Application")
        @Story("HFD-39785 : Getting All Offers available for the Financial Application")
        public void gettingAllOffersforAvailableFortheApplicant() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", "2023-03-01");
                offersData.put("FirstPaymentDueDate", "2023-03-20");

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Getting All Offers available for the Financial Application " + AppilicationID
                                                + " with a service Cost of" + offersData.get("ServiceCost")));

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
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

                // Offers That are Returned
                JSONArray offersList = offers.getJSONArray("offers");

                Iterator<Object> iterator = offersList.iterator();
                while (iterator.hasNext()) {
                        JSONObject jsonObject = (JSONObject) iterator.next();

                        for (String key : jsonObject.keySet()) {

                                offerListings.put(key, jsonObject.get(key));

                        }

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                System.out.println("The List of Offer is: " + offerListings);

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("Finance Date",
                                financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                                "The Finance Date");
                sa = assertionUtil.assertEquals("First Payment DueDate",
                                financeOfferData.get("firstPaymentDueDate"), offersData.get("FirstPaymentDueDate"),
                                "The First Payment DueDate ");
                sa.assertAll();

        }

        @Description("Setting Offers for the Financial Application")
        @Test(priority = 5, groups = { "v3" }, description = "Setting an Offers for the Financial Application")
        @Story("HFD-39785 : Setting an Offers for the Financial Application")
        public void settingAnOffersfortheApplication() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                String offerID = (String) offerListings.get("id");
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "635");

                setoffersData.put("FinanceAmount", offerListings.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount", offerListings.get("defaultDownPaymentAmount"));
                setoffersData.put("TermMonths", offerListings.get("termMonths"));
                setoffersData.put("InterestRate", offerListings.get("interestRate"));
                setoffersData.put("FinanceDate", financeOfferData.get("financeDate"));
                setoffersData.put("FirstPaymentDueDate", financeOfferData.get("firstPaymentDueDate"));

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Setting Offer  " + offerID + " to applicant " + AppilicationID));

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + offerID;
                json = setOfferRequest.setOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Applicantion ID", accountData.get("applicationId"),
                                financeOfferData.get("applicationId"), "The Application ID");
                sa = assertionUtil.assertEquals("Offer ID", accountData.get("offerId"), offerID,
                                "The Offer ID ");

                System.out.println("The Offer ID id:   " + accountData.get("offerId"));

                System.out.println("USED Offer ID id:   " + offerID);

                sa.assertAll();

        }

        @Description("Getting all offers that are set to an application ")
        @Test(priority = 6, groups = { "v3" }, description = "Getting all offers that are set to an application ")
        @Story("HFD-39785 : Getting all offers that are set to an application ")
        public void gettingallOffersAttachedToAnApplicant() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "636");

                String service;
                Object json;

                setoffersData.put("FinanceAmount", financeOfferData.get("financeAmount"));
                setoffersData.put("DownPaymentAmount", financeOfferData.get("downPaymentAmount"));
                setoffersData.put("ApplicationOfferId", null);
                setoffersData.put("FinanceDate", financeOfferData.get("financeDate"));
                setoffersData.put("FirstPaymentDueDate", financeOfferData.get("firstPaymentDueDate"));

                service = apiConfiguration().v3Underwritting();
                json = getSingleOfferRequest.getSingleOffer(AppilicationID);

                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Geting all Offers that were set to Applicant  " + AppilicationID));

                // jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedGETReponseWithNoContentTypeWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa.assertAll();
                // Validation

        }

}
