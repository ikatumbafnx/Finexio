package com.finexio.test.webservices.FinanceOffers;

import static com.finexio.config.ConfigurationManager.apiConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Epic("Get Offers, Select Offer, Change Terms and Activate ")
public class ChangeOfferTermsAndActivate extends BaseApi {

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

        public String token, offerflag;
        public String AppilicationID;
        Faker faker = new Faker();
        Map<String, String> applicationData = getApplicationData();
        Map<Object, Object> accountDetailsData = new HashMap<>();
        Map<String, Object> offersData = new HashMap<>();
        Map<String, Object> setoffersData = new HashMap<>();
        Map<String, Object> financeOfferData = new HashMap<>();
        Map<String, Object> offerListings = new HashMap<>();
        Map<String, Object> offerListingsChange = new HashMap<>();
        List<String> offerNos = new ArrayList<String>();
        JSONArray offersList, offersListChange;
        JSONObject offers;

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
                String json;

                service = apiConfiguration().v3Authenticate();

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

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "201",
                                "Call returned Error");

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

                service = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                accountDetailsData.put("status", accountData.get("status"));
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Applicantion Status",
                                accountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("id"), AppilicationID,
                                "The Application ID ");
                sa.assertAll();

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(
                                testResult -> testResult.setName("Underwritting application  " + AppilicationID));

                try {
                        Thread.sleep(5000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

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

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Getting Application " + AppilicationID
                                + " Details to verify that Application is in Approved Status "));

                json = getApplicationDetailsRequest.getAccountDetails(AppilicationID);

                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                Response response = restUtils.getStringGETReponseNoContentType(json, service,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
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

                sa.assertAll();

        }

        @Description("Adding Payment Method to the Application")
        @Test(priority = 4, groups = { "v3" }, description = "Adding Payment Method to the Application")
        @Story("HFD-39785 : Adding Payment Method to the Application")
        public void addingPaymentMethods() throws Exception {

                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult
                                .setName("Adding Payment Methods to Applicant " + AppilicationID));
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

        @Description("Getting All Offers available for the Financial Application")
        @Test(priority = 5, groups = {
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

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                offers = new JSONObject(response.asString());
                JsonPath accountData = response.jsonPath();

                financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                financeOfferData.put("downPaymentAmount", accountData.get("downPaymentAmount"));
                financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                financeOfferData.put("region", accountData.get("region"));
                financeOfferData.put("section", accountData.get("section"));

                financeOfferData.put("financeDate", accountData.get("financeDate"));
                financeOfferData.put("firstPaymentDueDate", accountData.get("firstPaymentDueDate"));
                financeOfferData.put("applicationId", accountData.get("applicationId"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Getting All Offers Available to Applicant "
                                + AppilicationID + " with a limit of " + offersData.get("ServiceCost")));

                // Offers That are Returned
                offersList = offers.getJSONArray("offers");

                Iterator<Object> iterator = offersList.iterator();
                while (iterator.hasNext()) {
                        JSONObject jsonObject = (JSONObject) iterator.next();

                        for (String key : jsonObject.keySet()) {

                                offerListings.put(key, jsonObject.get(key));

                        }
                        offerNos.add((String) offerListings.get("id"));

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
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

        @Description("Getting All Offers for the Financial Application")
        @Test(priority = 6, groups = { "v3" }, description = "Setting an Offers for the Financial Application")
        @Story("HFD-39785 : Setting an Offers for the Financial Application")
        public void settingAnOffersfortheApplication() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "635");

                String offerID = (String) offerListings.get("id");

                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                setoffersData.put("FinanceAmount", offerListings.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount", offerListings.get("defaultDownPaymentAmount"));
                setoffersData.put("TermMonths", offerListings.get("termMonths"));
                setoffersData.put("InterestRate", offerListings.get("interestRate"));
                setoffersData.put("FinanceDate", FinanceDate);
                setoffersData.put("FirstPaymentDueDate", firstPaymentDueDate);

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + offerID;
                json = setOfferRequest.setOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Setting Offers "
                                + offerID + " to Applicant  " + AppilicationID));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Applicantion ID", accountData.get("applicationId"),
                                financeOfferData.get("applicationId"), "The Application ID");
                sa = assertionUtil.assertEquals("Offer ID", accountData.get("offerId"), offerID,
                                "The Offer ID ");

                System.out.println("The Offer ID id:   " + accountData.get("offerId"));

                System.out.println("USED Offer ID id:   " + offerID);
                // sa = assertionUtil.assertEquals("Finance Date",
                // accountData.get("financeTerms.financeDate"), offersData.get("FinanceDate"),
                // "The Finance Date");
                // sa = assertionUtil.assertEquals("First Payment DueDate",
                // accountData.get("financeTerms.firstPaymentDueDate"),
                // offersData.get("FirstPaymentDueDate"),
                // "The First Payment DueDate ");

                // sa = assertionUtil.assertEquals("Finance Amount",
                // accountData.get("financeTerms.financeAmount"),
                // offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // accountData.get("financeTerms.downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                sa.assertAll();

        }

        @Description("Changing the Terms of the Offer that was set on the Account")
        @Test(priority = 7, groups = {
                        "v3" }, description = "Changing the Terms of the Offer that was set on the Account")
        @Story("HFD-39785 : Changing the Terms of the Offer that was set on the Account")
        public void changeOfferTerms() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                String offerID = (String) offerListings.get("id");

                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                setoffersData.put("FinanceAmount", offerListings.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount", offerListings.get("defaultDownPaymentAmount"));
                setoffersData.put("TermMonths", offerListings.get("termMonths"));
                setoffersData.put("InterestRate", offerListings.get("interestRate"));
                setoffersData.put("FinanceDate", FinanceDate);
                setoffersData.put("FirstPaymentDueDate", firstPaymentDueDate);

                service = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + offerID;
                json = setOfferRequest.setOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Changing  Offer to "
                                + offerID + " to Applicant  " + AppilicationID));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Applicantion ID", accountData.get("applicationId"),
                                financeOfferData.get("applicationId"), "The Application ID");
                sa = assertionUtil.assertEquals("Offer ID", accountData.get("offerId"), offerID,
                                "The Offer ID ");

                System.out.println("The Offer ID id:   " + accountData.get("offerId"));

                System.out.println("USED Offer ID id:   " + offerID);
                // sa = assertionUtil.assertEquals("Finance Date",
                // accountData.get("financeTerms.financeDate"), offersData.get("FinanceDate"),
                // "The Finance Date");
                // sa = assertionUtil.assertEquals("First Payment DueDate",
                // accountData.get("financeTerms.firstPaymentDueDate"),
                // offersData.get("FirstPaymentDueDate"),
                // "The First Payment DueDate ");

                // sa = assertionUtil.assertEquals("Finance Amount",
                // accountData.get("financeTerms.financeAmount"),
                // offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // accountData.get("financeTerms.downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                sa.assertAll();

        }

        @Description("Getting all offers that are set to an application ")
        @Test(priority = 8, groups = { "v3" }, description = "Getting all offers that are set to an application ")
        @Story("HFD-39785 : Getting all offers that are set to an application ")
        public void gettingallOffersAttachedToAnApplicant() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "636");

                setoffersData.put("FinanceAmount", financeOfferData.get("financeAmount"));
                setoffersData.put("DownPaymentAmount", financeOfferData.get("downPaymentAmount"));
                setoffersData.put("ApplicationOfferId", null);
                setoffersData.put("FinanceDate", financeOfferData.get("financeDate"));
                setoffersData.put("FirstPaymentDueDate", financeOfferData.get("firstPaymentDueDate"));

                service = apiConfiguration().v3Underwritting();
                json = getSingleOfferRequest.getSingleOffer(AppilicationID);
                // jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedGETReponseWithNoContentTypeWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                // sa = assertionUtil.assertEquals("Applicantion Status",
                // accountData.get("status"),
                // accountDetailsData.get("status"), "The Application Status ");
                // sa = assertionUtil.assertEquals("Application ID", accountData.get("id"),
                // AppilicationID,
                // "The Application ID ");
                // sa.assertAll();

        }

        @Description("Getting and Displaying Agreement to applicant")
        @Test(priority = 9, groups = { "v3" }, description = "Getting and Displaying Agreement to applicant")

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

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Retrieving Aggreement of Applicant "
                                + AppilicationID));
                sa = assertionUtil.assertEquals("Applicantion Status", response.statusCode(),
                                200,
                                "The Application Status ");
                // All Good

                sa.assertAll();
        }

        @Description("Applicant ESigning the Agreement")
        @Test(priority = 10, groups = { "v3" }, description = "Applicant ESigning theAgreement")
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

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("ESigning the Agreement of Applicant "
                                + AppilicationID));

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

        @Description("Activating the Account of the Applicant")
        @Test(priority = 11, groups = { "v3" }, description = "Activating the Account of the Applicant")
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

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Activating  Applicant "
                                + AppilicationID));

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
