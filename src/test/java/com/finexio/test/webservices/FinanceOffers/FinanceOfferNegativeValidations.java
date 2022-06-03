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

public class FinanceOfferNegativeValidations extends BaseApi {

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
        List<String> offerNos = new ArrayList<String>();

        // public String endpointurl = envparam.get("baseEnv");
        // public String clientID = envparam.get("clientID");
        // public String clientSecret = envparam.get("clientSecret");
        // public String grantType = envparam.get("grantType");
        // public String authUrl = envparam.get("authUrl");
        // Validation

        @Epic("Get Offers, Select Offer, Change Terms and Activate ")
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

        @Epic("Get Offers for Application Still Pending ")
        @Description("Get Offers for Application Still Pending")
        @Test(priority = 1, groups = { "v3" }, description = "Get Offers for Application Still Pending")
        @Story("HFD-8965 :Get Offers for Application Still Pending")
        public void GetOffersforPendingApplication() throws Exception {
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service, getOfferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an Application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Calling the Get Offers
                offersData.put("ServiceCost", 5000.50);
                offersData.put("DownPaymentAmount", 500.01);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", "2022-02-03");
                offersData.put("FirstPaymentDueDate", "2022-02-18");

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Trying to GetOffers of Application "
                                + AppilicationID + " that is still Pending Approval"));

                // financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                // financeOfferData.put("region", accountData.get("region"));
                // financeOfferData.put("section", accountData.get("section"));

                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));
                // financeOfferData.put("applicationId", accountData.get("applicationId"));

                // // Offers That are Returned
                // JSONArray offersList = offers.getJSONArray("offers");

                // Iterator<Object> iterator = offersList.iterator();
                // while (iterator.hasNext()) {
                // JSONObject jsonObject = (JSONObject) iterator.next();

                // for (String key : jsonObject.keySet()) {

                // offerListings.put(key, jsonObject.get(key));

                // }
                // offerNos.add((String) offerListings.get("id"));

                // }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "400",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Status of ",
                                accountData.get("message"), "Application must be in approved status.",
                                "The Application Status");
                // sa = assertionUtil.assertEquals("Finance Date",
                // financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                // "The Finance Date");
                // sa = assertionUtil.assertEquals("First Payment DueDate",
                // financeOfferData.get("firstPaymentDueDate"),
                // offersData.get("FirstPaymentDueDate"),
                // "The First Payment DueDate ");

                sa.assertAll();

        }

        @Epic("Get Offers and Select Offer that Doesn't Exist")
        @Description("Get Offers and Select Offer that Doesn't Exist")
        @Test(priority = 2, groups = { "v3" }, description = "Get Offers and Select Offer that Doesn't Exist")
        @Story("HFD-8965 : Get Offers and Select Offer that Doesn't Exist")
        public void GetOffersSelectOfferthatDontExist() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", "2023-03-01");
                offersData.put("FirstPaymentDueDate", "2023-03-20");

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

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
                        offerNos.add((String) offerListings.get("id"));

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "200",
                                "Call returned Error");
                sa = assertionUtil.assertEquals("Finance Date",
                                financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                                "The Finance Date");
                sa = assertionUtil.assertEquals("First Payment DueDate",
                                financeOfferData.get("firstPaymentDueDate"), offersData.get("FirstPaymentDueDate"),
                                "The First Payment DueDate ");

                // Setting offers

                String offerID = (String) offerListings.get("id");

                setoffersData.put("FinanceAmount", financeOfferData.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount",
                                financeOfferData.get("defaultDownPaymentAmount"));
                setoffersData.put("ApplicationOfferId", null);
                setoffersData.put("TermMonths", offerListings.get("termMonths"));
                setoffersData.put("InterestRate", offerListings.get("interestRate"));
                setoffersData.put("FinanceDate", financeOfferData.get("financeDate"));
                setoffersData.put("FirstPaymentDueDate",
                                financeOfferData.get("firstPaymentDueDate"));
                String invalidOfferID = offerID + "563";
                setofferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + invalidOfferID;
                json = getOffersRequest.getOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", setofferservice);
                Response setofferresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                setofferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(setofferresponse.asString(), "response", setofferservice);
                JsonPath offeraccountData = setofferresponse.jsonPath();

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Setting Offer No " + invalidOfferID
                                + " that doesnt exist to applicant " + AppilicationID));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(setofferresponse.statusCode()), "400",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("The invalid Offer with ",
                                offeraccountData.get("fieldErrors[0].message"),
                                "The value '" + invalidOfferID + "' is not valid.", "The Application ID");

                sa.assertAll();

        }

        @Description("Get Offers and Pass Service Cost of 0")
        @Test(priority = 3, groups = { "v3" }, description = "Service cost must be greater than 0 Error")
        public void GetOffersWhenOServiceCostPassed() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                Map<String, Object> dataforOffer = new HashMap<>();
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                dataforOffer.put("ServiceCost", 0);
                dataforOffer.put("DownPaymentAmount", 500);
                dataforOffer.put("Section", "GenDentistry");
                dataforOffer.put("FinanceDate", "2023-03-01");
                dataforOffer.put("FirstPaymentDueDate", "2023-03-20");

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, dataforOffer);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

                financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                financeOfferData.put("downPaymentAmount", accountData.get("downPaymentAmount"));
                financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                financeOfferData.put("region", accountData.get("region"));
                financeOfferData.put("section", accountData.get("section"));

                financeOfferData.put("financeDate", accountData.get("financeDate"));
                financeOfferData.put("firstPaymentDueDate", accountData.get("firstPaymentDueDate"));
                financeOfferData.put("applicationId", accountData.get("applicationId"));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "200",
                                "Call returned Error");

                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("ERROR Message", offers.get("message"),
                                "Service cost must be greater than 0.",
                                "The Error Message");

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName(
                                "Verify that Service cost must be greater than 0 Error when 0 Service Cost is passed"));

        }

        @Description("Get Offers and Pass First Payment Due Date Before the Finance Date")
        @Test(priority = 4, groups = { "v3" }, description = "Service cost must be greater than 0 Error")
        public void PassPaymentDueDateBeforeFinannceDate() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName(
                                "Verify Error : First payment due date must occur on or after the finance date."));
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                String DueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, -30),
                                "MM/dd/YYYY", "YYYY-MM-dd");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", FinanceDate);
                offersData.put("FirstPaymentDueDate", DueDate);

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

                financeOfferData.put("financeAmount", accountData.get("financeAmount"));
                financeOfferData.put("downPaymentAmount", accountData.get("downPaymentAmount"));
                financeOfferData.put("creditGrade", accountData.get("creditGrade"));
                financeOfferData.put("region", accountData.get("region"));
                financeOfferData.put("section", accountData.get("section"));

                financeOfferData.put("financeDate", accountData.get("financeDate"));
                financeOfferData.put("firstPaymentDueDate", accountData.get("firstPaymentDueDate"));
                financeOfferData.put("applicationId", accountData.get("applicationId"));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "400",
                                "Call returned Error");

                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("ERROR Message", offers.get("message"),
                                "First payment due date must occur on or after the finance date.",
                                "The Error Message");

                sa.assertAll();

        }

        @Description("Get Offers and Set Offer with Mismatched Interest Rate")
        @Test(priority = 5, groups = {
                        "v3" }, description = "Interest rate indicated in the Finance Terms does not match the interest rate for the offer.")
        @Story("HFD-8965 : Get Offers and Select Offer that Doesn't Exist")
        public void MismatchIntrestRateError() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", FinanceDate);
                offersData.put("FirstPaymentDueDate", firstPaymentDueDate);

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

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
                        offerNos.add((String) offerListings.get("id"));

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "200",
                                "Call returned Error");
                sa = assertionUtil.assertEquals("Finance Date",
                                financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                                "The Finance Date");
                sa = assertionUtil.assertEquals("First Payment DueDate",
                                financeOfferData.get("firstPaymentDueDate"), offersData.get("FirstPaymentDueDate"),
                                "The First Payment DueDate ");

                // Setting offers

                String offerID = (String) offerListings.get("id");

                setoffersData.put("FinanceAmount", offerListings.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount", offerListings.get("defaultDownPaymentAmount"));
                setoffersData.put("TermMonths", offerListings.get("termMonths"));
                setoffersData.put("InterestRate", 12);
                setoffersData.put("FinanceDate", financeOfferData.get("financeDate"));
                setoffersData.put("FirstPaymentDueDate", financeOfferData.get("firstPaymentDueDate"));

                setofferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + offerID;
                json = setOfferRequest.setOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", setofferservice);
                Response setofferresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                setofferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(setofferresponse.asString(), "response", setofferservice);
                JsonPath offeraccountData = setofferresponse.jsonPath();

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName(
                                "Interest rate indicated in the Finance Terms does not match the interest rate for the offer. "));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(setofferresponse.statusCode()), "400",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("The error message",
                                offeraccountData.get("message"),
                                "Interest rate indicated in the Finance Terms does not match the interest rate for the offer.",
                                "The Error Message");

                sa.assertAll();

        }

        @Description("Get Offers and Set Offer with Mismatched Term Months")
        @Test(priority = 6, groups = { "v3" }, description = "Get Offers and Set Offer with Mismatched Term Months")
        public void SetOffersWithMismatchedTermsMonth() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", "2023-03-01");
                offersData.put("FirstPaymentDueDate", "2023-03-20");

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

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
                        offerNos.add((String) offerListings.get("id"));

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "200",
                                "Call returned Error");
                sa = assertionUtil.assertEquals("Finance Date",
                                financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                                "The Finance Date");
                sa = assertionUtil.assertEquals("First Payment DueDate",
                                financeOfferData.get("firstPaymentDueDate"), offersData.get("FirstPaymentDueDate"),
                                "The First Payment DueDate ");

                // Setting offers

                String offerID = (String) offerListings.get("id");

                setoffersData.put("FinanceAmount", offerListings.get("defaultFinanceAmount"));
                setoffersData.put("DownPaymentAmount", offerListings.get("defaultDownPaymentAmount"));
                setoffersData.put("TermMonths", 1);
                setoffersData.put("InterestRate", offerListings.get("interestRate"));
                setoffersData.put("FinanceDate", FinanceDate);
                setoffersData.put("FirstPaymentDueDate", firstPaymentDueDate);

                String invalidOfferID = offerID + "563";
                setofferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers/"
                                + offerID;
                json = setOfferRequest.setOffers(AppilicationID, setoffersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", setofferservice);
                Response setofferresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                setofferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(setofferresponse.asString(), "response", setofferservice);
                JsonPath offeraccountData = setofferresponse.jsonPath();

                // financeOfferData.put("FinanceAmount", accountData.get("financeAmount"));
                // financeOfferData.put("downPaymentAmount",
                // accountData.get("downPaymentAmount"));
                // financeOfferData.put("section", accountData.get("section"));
                // financeOfferData.put("financeDate", accountData.get("financeDate"));
                // financeOfferData.put("firstPaymentDueDate",
                // accountData.get("firstPaymentDueDate"));

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName(
                                "Term Months indicated in the Finance Terms does not match the term months for the offer."));

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(setofferresponse.statusCode()), "400",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("The invalid Offer with ",
                                offeraccountData.get("message"),
                                "Term Months indicated in the Finance Terms does not match the term months for the offer.",
                                "The Term Months");

                sa.assertAll();

        }

        @Description("Get Selected Offer for Application Without Offer Set")
        @Test(priority = 7, groups = { "v3" }, description = "Get Selected Offer for Application Without Offer Set")
        public void GetOfferNotSet() throws Exception {
                // testcases.put("TestSuiteID", "11");
                // testcases.put("TestCaseID", "34");
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service, getOfferservice, underwrittingservice,
                                addPaymentService, setofferservice;
                Object json, GetOfferjson;
                service = apiConfiguration().v3createapplication();
                DateUtils dateUtils = new DateUtils();
                System.out.println(dateUtils.getDate("YYYY-MM-dd"));
                String firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +30),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                String FinanceDate = dateUtils.getDate("YYYY-MM-dd");

                // Creating an application

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

                sa = assertionUtil.assertEquals("Application Status", applicationData.get("status"), "pending",
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Application State", applicationData.get("workflowType"),
                                "underwriting", "The Application WorkFlow State ");

                // Underwriting the application

                underwrittingservice = apiConfiguration().v3Underwritting();
                json = underwrittingRequest.underwriteApplication(AppilicationID);
                Response underwrittingresponse = restUtils.getAuthentPOSTReponseWithNoContentType(json,
                                underwrittingservice,
                                envparam.get("baseEnv"), "Bearer " + token);
                jsonFormatter.printFormattedJson(underwrittingresponse.asString(), "response", underwrittingservice);
                JsonPath underwrittingaccountData = underwrittingresponse.jsonPath();

                accountDetailsData.put("status", underwrittingaccountData.get("status"));

                sa = assertionUtil.assertEquals("Applicantion Status",
                                underwrittingaccountData.get("status"),
                                accountDetailsData.get("status"), "The Application Status ");
                sa = assertionUtil.assertEquals("Application ID", underwrittingaccountData.get("id"),
                                AppilicationID,
                                "The Application ID ");

                // Adding Payment

                addPaymentService = apiConfiguration().v3createPaymentMethods() + "/" + AppilicationID
                                + "/payment-methods";

                String fullName = accountDetailsData.get("firstName") + " " +
                                accountDetailsData.get("lastName");
                json = createPaymentMethodsRequest.AddPaymentMethods("credit",
                                "5105105105105100", fullName, 12, 2023,
                                accountDetailsData.get("postalCode").toString(), true);
                Response addpaymentresponse = restUtils.getAuthentPOSTReponse(json, addPaymentService,
                                envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(addpaymentresponse.asString(), "response", addPaymentService);
                JsonPath addpPaymentaccountData = addpaymentresponse.jsonPath();

                sa = assertionUtil.assertEquals("API Status CODE", response.statusCode(),
                                201,
                                "The Application Status ");
                sa = assertionUtil.assertEquals("Applicant CardholderName",
                                addpPaymentaccountData.get("cardholderName"), fullName,
                                "The Applicant CardholderName ");
                sa = assertionUtil.assertEquals("Applicant paymentMethodType",
                                addpPaymentaccountData.get("paymentMethodType"),
                                "credit", "The Applicant CardholderName ");

                // Wait for some time for Application to turn int Approved status
                try {
                        Thread.sleep(10000);
                } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }

                // Calling the Get Offers
                testcases.put("TestSuiteID", "503");
                testcases.put("TestCaseID", "634");

                offersData.put("ServiceCost", 2500);
                offersData.put("DownPaymentAmount", 500);
                offersData.put("Section", "GenDentistry");
                offersData.put("FinanceDate", "2023-03-01");
                offersData.put("FirstPaymentDueDate", "2023-03-20");

                getOfferservice = apiConfiguration().v3Underwritting() + "/" + AppilicationID + "/offers";
                json = getOffersRequest.getOffers(AppilicationID, offersData);
                jsonFormatter.printFormattedJson(json.toString(), "request", getOfferservice);
                Response getOffersresponse = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                getOfferservice,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(getOffersresponse.asString(), "response", getOfferservice);
                JSONObject offers = new JSONObject(getOffersresponse.asString());
                JsonPath accountData = getOffersresponse.jsonPath();

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
                        offerNos.add((String) offerListings.get("id"));

                }

                // sa = assertionUtil.assertEquals("Finance Amount",
                // financeOfferData.get("financeAmount"), offersData.get("financeAmount"),
                // "The Finance Ammount ");
                // sa = assertionUtil.assertEquals("Down Payment Amount",
                // financeOfferData.get("downPaymentAmount"),
                // offersData.get("DownPaymentAmount"),
                // "The Down Payment Amount ");
                // sa = assertionUtil.assertEquals("Offers Section",
                // financeOfferData.get("section"), offersData.get("Section"),
                // "The Offer Section ");
                sa = assertionUtil.assertEquals("Status Code", String.valueOf(getOffersresponse.statusCode()), "200",
                                "Call returned Error");
                sa = assertionUtil.assertEquals("Finance Date",
                                financeOfferData.get("financeDate"), offersData.get("FinanceDate"),
                                "The Finance Date");
                sa = assertionUtil.assertEquals("First Payment DueDate",
                                financeOfferData.get("firstPaymentDueDate"), offersData.get("FirstPaymentDueDate"),
                                "The First Payment DueDate ");

                // Get offers that were set

                service = apiConfiguration().v3Underwritting();
                GetOfferjson = getSingleOfferRequest.getSingleOffer(AppilicationID);

                // jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response GetSpecificOfferresponse = restUtils.getAuthenticatedGETReponseWithNoContentTypeWithBearer(
                                GetOfferjson,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath specficOfferData = GetSpecificOfferresponse.jsonPath();

                sa = assertionUtil.assertEquals("Status Code", String.valueOf(GetSpecificOfferresponse.statusCode()),
                                "400",
                                "Call returned Error");

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName(
                                "Application corresponding to applicationId: " + AppilicationID + " was not found."));

                sa = assertionUtil.assertEquals("The Invalid error message of ",
                                specficOfferData.get("message"),
                                "Application corresponding to applicationId: " + AppilicationID + " was not found.",
                                "The Get Offers ");

                sa.assertAll();

        }

}
