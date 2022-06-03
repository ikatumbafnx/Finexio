package com.finexio.test.webservices.EasyAPI;

import static com.finexio.config.ConfigurationManager.apiConfiguration;
import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finexio.BaseApi;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.api.JsonFormatter;
import com.finexio.utils.api.RestUtils;
import com.finexio.webservices.EasyAPI.GetAuthTokenRequest;
import com.finexio.webservices.EasyAPI.PeekRequest;
import com.finexio.webservices.EasyAPI.StartRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SleepArchTxAppJourneyRegressionTest extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        AssertionUtil assertionUtil = new AssertionUtil();
        GetAuthTokenRequest getAuthToken = new GetAuthTokenRequest();
        StartRequest startRequest = new StartRequest();
        PeekRequest easyapipeek = new PeekRequest();

        private String token, startToken;
        private String AppilicationID;

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "EasyAPI" }, description = "Retrieve an Access Authorization Token")
        // @Story("HFD-12356 : Authenticate Users to V2 API")
        public void generateToken() throws Exception {
                String service;
                String json;
                SoftAssert sa;

                testcases.put("TestCaseID", "595");
                testcases.put("TestSuiteID", "502");

                service = configuration().easyApiAuthService();
                json = getAuthToken.getAuthenticationToken(envparam.get("grantType"), envparam.get("clientID"),
                                envparam.get("clientSecret"));

                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service,
                                envparam.get("authUrl"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("EasyAPI Start")
        @Test(priority = 1, groups = { "EasyAPI" }, description = "EasyAPI Star")
        // @Story("HFD-12356 : Authenticate Users to V2 API")
        public void startAppln() throws Exception {
                String service;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "596");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();

                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", false);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);

                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                startToken = resData.get("Result.Token");
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Reference ID",
                                resData.get("Result.ReferenceId"), dataInput.get("ReferenceID"), "Reference ID");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Approved", "Disposition");
                sa = assertionUtil.assertEquals("GET Link Rel",
                                resData.get("Result.Links[0].Rel"), "redirect", "Get Link");
                sa = assertionUtil.assertEquals("GET Link Action",
                                resData.get("Result.Links[0].Action"), "GET", "Get Link");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("EasyAPI Peek")
        @Test(priority = 2, groups = { "EasyAPI" }, description = "EasyAPI Peek")
        @Story("HFD-12356 : Authenticate Users to V2 API")
        public void easyAPIPeek() throws Exception {
                String service;
                Object json;
                SoftAssert sa;
                Map<String, Object> dataInput = new HashMap<>();
                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiPeek();

                json = easyapipeek.easyAPIPeek(dataInput.get("ReferenceID"), startToken + "s");
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getPOSTReponseWithContentTypeTextApplicationJson(json.toString(), service,
                                envparam.get("baseEnv"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                // JSONArray jsonArray = resData.get("Result.Session.Application.Applicants");

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "404",
                                "Call returned Error");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify that the Disposition is Pending Approval ")
        @Test(priority = 3, groups = { "EasyAPI" }, description = "Verify that the Disposition is Pending Approval")
        @Story("HFD-12356 : Verify that the Disposition is Pending Approval")
        public void startApplnWithPendingApprovalDesposition() throws Exception {
                String service;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                testcases = new HashMap<>();
                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();

                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", false);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", false);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                startToken = resData.get("Result.Token");
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Reference ID",
                                resData.get("Result.ReferenceId"), dataInput.get("ReferenceID"), "Reference ID");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Pending Approval", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify that the Disposition is Exiting with exceptions when No National ID is added ")
        @Test(priority = 4, groups = {
                        "EasyAPI" }, description = "Verify that the Disposition is Exiting with exceptions when No National ID is added")
        @Story("HFD-12356 : Verify that the Disposition is Exiting with exceptions when No National ID is added")
        public void startApplnWithExitingWithExceptionDesposition() throws Exception {
                String service;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();

                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", null);
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", false);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                startToken = resData.get("Result.Token");
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant NationalId [Value] is missing",
                                "Reference ID");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify that a short Tiny URL is generated when ShortenURL parameter is set to true")
        @Test(priority = 5, groups = {
                        "EasyAPI" }, description = "Verify that a short Tiny URL is generated when ShortenURL parameter is set to true")
        @Story("HFD-12356 : Verify that a short Tiny URL is generated when ShortenURL parameter is set to true")
        public void verifyshorturl() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;
                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                startToken = resData.get("Result.Token");
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Reference ID",
                                resData.get("Result.ReferenceId"), dataInput.get("ReferenceID"), "Reference ID");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Approved", "Disposition");
                link = resData.get("Result.Links[0].Url");
                sa = assertionUtil.assertTrue(link.contains("https://tinyurl.com"), "No Match Found in the Link");
                sa = assertionUtil.assertEquals("GET Link Rel",
                                resData.get("Result.Links[0].Rel"), "redirect", "Get Link");
                sa = assertionUtil.assertEquals("GET Link Action",
                                resData.get("Result.Links[0].Action"), "GET", "Get Link");

                System.out.println("The Url is: " + link);
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the Provider ID is required to originate an application")
        @Test(priority = 6, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the Provider ID is required to originate an application")
        @Story("HFD-12356 : An error is thrown informing the caller that the Provider ID is required to originate an application")
        public void InsufficientDataNoPrequalification() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                // mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine1", dataInput.get("address.StreetLine1"));
                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                address.put("City", dataInput.get("address.City"));
                address.put("StatoId", dataInput.get("address.StatoId"));
                address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);

                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "400", "Call returned Error");
                sa = assertionUtil.assertEquals("ProviderId",
                                resData.get("errors.ProviderId[0]"), "The ProviderId field is required.",
                                "The The ProviderId");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that valid Provider ID is required to originate an application")
        @Test(priority = 7, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that valid Provider ID is required to originate an application")
        @Story("HFD-12356 : An error is thrown informing the caller that valid Provider ID is required to originate an application")
        public void InvalidProviderID() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 92);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                String op = "Options.LinkExpirationInDays";

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "400", "Call returned Error");
                // sa = assertionUtil.assertEquals("The field LinkExpirationInDays must be
                // between 1 and 90.",
                // resData.get("errors.*"),
                // "The field LinkExpirationInDays must be between 1 and 90.",
                // "The field LinkExpirationInDays must be between 1 and 90.");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the Email is invalid")
        @Test(priority = 8, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the Email is invalid")
        @Story("HFD-12356 : An error is thrown informing the caller that the Email is invalid")
        public void InvalidDOB() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;
                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-042");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                String op = "Options.LinkExpirationInDays";

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "400", "Call returned Error");
                // sa = assertionUtil.assertEquals("The field LinkExpirationInDays must be
                // between 1 and 90.",
                // resData.get("$.Application.Applicants[0].DOB"),
                // "The field LinkExpirationInDays must be between 1 and 90.",
                // "The field LinkExpirationInDays must be between 1 and 90.");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the Phone Number is invalid")
        @Test(priority = 9, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the Phone Number is invalid")
        @Story("HFD-12356 : An error is thrown informing the caller that the Phone Number is invalid")
        public void InvalidPhoneNumber() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);

                dataInput.put("communication.type", "Mobile");
                dataInput.put("communication.value", "801999789423");
                dataInput.put("communication.priority", 0);

                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("treatment.TotalCost", 2000);
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "");
                dataInput.put("communication2.priority", 0);

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();
                String op = "Options.LinkExpirationInDays";

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");

                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant Mobile [Value] is missing.",
                                "Applicant Phone Number ");
                // sa = assertionUtil.assertEquals("The field LinkExpirationInDays must be
                // between 1 and 90.",
                // resData.get("$.Application.Applicants[0].DOB"),
                // "The field LinkExpirationInDays must be between 1 and 90.",
                // "The field LinkExpirationInDays must be between 1 and 90.");

                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the Street Line 1 is required")
        @Test(priority = 10, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the Street Line 1 is required")
        @Story("HFD-12356 : An error is thrown informing the caller that the Street Line 1 is required")
        public void MissindStreetInAddress() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                address.put("City", dataInput.get("address.City"));
                address.put("StatoId", dataInput.get("address.StatoId"));
                address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);

                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant address property [StreetLine1] is missing.",
                                "Applicant Address property");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the City is required")
        @Test(priority = 11, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the City is required")
        @Story("HFD-12356 : An error is thrown informing the caller that the City is required")
        public void MissingCityInAddress() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                // dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine1", dataInput.get("address.StreetLine1"));
                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                // address.put("City", dataInput.get("address.City"));
                address.put("StatoId", dataInput.get("address.StatoId"));
                address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);
                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant address property [City] is missing.",
                                "Applicant City ");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that the Statoid is required")
        @Test(priority = 12, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that the Statoid is required")
        @Story("HFD-12356 : An error is thrown informing the caller that the Statoid is required")
        public void MissingStatoidInAddress() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                // dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");

                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine1", dataInput.get("address.StreetLine1"));
                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                address.put("City", dataInput.get("address.City"));
                // address.put("StatoId", dataInput.get("address.StatoId"));
                address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);
                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant address property [Statoid] is missing.",
                                "Applicant State ID ");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify The Primary applicant address property [PostalCode] is missing.")
        @Test(priority = 13, groups = {
                        "EasyAPI" }, description = "Verify The Primary applicant address property [PostalCode] is missing.")
        @Story("HFD-12356 : The Primary applicant address property [PostalCode] is missing.")
        public void MissingPostalCodeInAddress() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;
                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "primary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                // dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine1", dataInput.get("address.StreetLine1"));
                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                address.put("City", dataInput.get("address.City"));
                address.put("StatoId", dataInput.get("address.StatoId"));
                // address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);
                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant address property [PostalCode] is missing.",
                                "Applicant State ID ");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

        @Epic("Sleep Arch Full App Journey Regression")
        @Description("Verify An error is thrown informing the caller that a Primary Applicant is required")
        @Test(priority = 14, groups = {
                        "EasyAPI" }, description = "Verify An error is thrown informing the caller that a Primary Applicant is required")
        @Story("HFD-12356 : An error is thrown informing the caller that a Primary Applicant is required")
        public void MissingPrimaryApplicant() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();
                Map<String, Object> dataInput = new HashMap<>();
                dataInput.put("ReferenceID", "AB93802983883");
                dataInput.put("ProviderId", "15357");
                dataInput.put("IsForPrequalification", true);
                dataInput.put("applicant.type", "secondary");
                dataInput.put("applicant.firstname", "John");
                dataInput.put("applicant.lastname", "Doe");
                dataInput.put("applicant.dob", "2002-01-04");
                dataInput.put("applicant.nationalID", "123456789");
                dataInput.put("applicant.monthlyIncome", 1000);
                dataInput.put("communication.type", "Email");
                dataInput.put("communication.value", "qaautomation@healthcarefinancedirect.com");
                dataInput.put("communication.priority", 0);
                dataInput.put("options.isTest", true);
                dataInput.put("options.LinkExpirationInDays", 90);
                dataInput.put("options.ShortenUrl", true);

                dataInput.put("address.StreetLine1", "89 Lake St");
                dataInput.put("address.StreetLine2", "Suite 89");
                dataInput.put("address.City", "San Francisco");
                dataInput.put("address.StatoId", "CA");
                dataInput.put("address.PostalCode", "89845");
                dataInput.put("address.CountryCode", "US");
                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

                JSONObject mainObject = new JSONObject();
                JSONObject application = new JSONObject();
                JSONObject optionsObject = new JSONObject();
                JSONObject optionsElements = new JSONObject();
                JSONArray dispatchTypeArray = new JSONArray();

                JSONArray applicantsArray = new JSONArray();
                JSONObject applicantsArrayElements = new JSONObject();
                JSONObject address = new JSONObject();

                JSONArray communicationsArray = new JSONArray();
                JSONObject communicationsArrayElements = new JSONObject();

                optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

                dispatchTypeArray.put("SMS");
                dispatchTypeArray.put("Email");
                optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
                optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
                optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

                optionsObject.put("DispatchTypes", dispatchTypeArray);
                mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
                mainObject.put("ProviderId", dataInput.get("ProviderId"));

                applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
                applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
                applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
                applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
                applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
                applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

                address.put("StreetLine1", dataInput.get("address.StreetLine1"));
                address.put("StreetLine2", dataInput.get("address.StreetLine2"));
                address.put("City", dataInput.get("address.City"));
                address.put("StatoId", dataInput.get("address.StatoId"));
                address.put("PostalCode", dataInput.get("address.PostalCode"));
                address.put("CountryCode", dataInput.get("address.CountryCode"));

                communicationsArrayElements.put("Value", dataInput.get("communication.value"));
                communicationsArrayElements.put("Type", dataInput.get("communication.type"));
                communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

                communicationsArray.put(communicationsArrayElements);

                applicantsArrayElements.put("Address", address);
                applicantsArrayElements.put("Communications", communicationsArray);
                applicantsArray.put(applicantsArrayElements);
                // applicantsArray.put(communicationsArray);

                application.put("Options", optionsElements);
                application.put("Applicants", applicantsArray);

                mainObject.put("Application", application);
                mainObject.put("Options", optionsObject);
                json = startRequest.startappOnDemend(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa = assertionUtil.assertEquals("Exceptions",
                                resData.get("Result.Exceptions[0].Description"),
                                "The Primary applicant not found. Please specify a primary applicant before continuing.",
                                "Primary Applicant ");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Exiting with exceptions", "Disposition");
                sa.assertAll();

        }

}
