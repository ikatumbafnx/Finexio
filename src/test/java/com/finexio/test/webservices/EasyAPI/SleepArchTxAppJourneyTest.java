package com.finexio.test.webservices.EasyAPI;

import static com.finexio.config.ConfigurationManager.apiConfiguration;
import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.List;
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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SleepArchTxAppJourneyTest extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        AssertionUtil assertionUtil = new AssertionUtil();
        GetAuthTokenRequest getAuthToken = new GetAuthTokenRequest();
        StartRequest startRequest = new StartRequest();
        PeekRequest easyapipeek = new PeekRequest();
        Map<String, Object> dataInput = new HashMap<>();

        private String token, startToken;
        private String AppilicationID;

        @Epic(" Sleep Arch Full App Journey")
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

        @Epic(" Sleep Arch Full App Journey")
        @Description("EasyAPI Start App")
        @Test(priority = 1, groups = { "EasyAPI" }, description = "EasyAPI Start App")
        // @Story("HFD-12356 : Authenticate Users to V2 API")
        public void startAppln() throws Exception {
                String service, link;
                Object json;
                SoftAssert sa;
                testcases.put("TestCaseID", "596");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiStart();

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

                dataInput.put("communication2.type", "Mobile");
                dataInput.put("communication2.value", "6615562886");
                dataInput.put("communication2.priority", 0);

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

                json = startRequest.startapp(dataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                // JSONObject Links = new JSONObject(response.asString());
                JsonPath resData = response.jsonPath();
                List<String> urls = resData.get("Result.Links.Rel");
                System.out.println("The List of URLS :" + urls);
                startToken = resData.get("Result.Token");
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                link = resData.get("Result.Links[0].Url");
                // sa =
                // assertionUtil.assertTrue(link.contains("https://dev-originations.healthcarefinancedirect.com"),
                // "No Match Found in the Link");
                sa = assertionUtil.assertTrue(urls.contains("redirect"), "No redirect Link is found");
                sa = assertionUtil.assertEquals("Reference ID",
                                resData.get("Result.ReferenceId"), dataInput.get("ReferenceID"), "Reference ID");
                sa = assertionUtil.assertEquals("Disposition",
                                resData.get("Result.Disposition"), "Approved", "Disposition");
                // sa = assertionUtil.assertEquals("GET Link Rel",
                // resData.get("Result.Links[0].Rel"), "redirect", "Get Link");
                // sa = assertionUtil.assertEquals("GET Link Action",
                // resData.get("Result.Links[0].Action"), "GET", "Get Link");
                // sa = assertionUtil.assertEquals("GET Link Rel",
                // resData.get("Result.Links[1].Rel"), "peek", "Get Link");
                // sa = assertionUtil.assertEquals("GET Link Action",
                // resData.get("Result.Links[1].Action"), "POST", "Get Link");

                sa.assertAll();

        }

        @Epic(" Sleep Arch Full App Journey")
        @Description("EasyAPI Application Peek")
        @Test(priority = 2, groups = { "EasyAPI" }, description = "EasyAPI Application Peek")
        // @Story("HFD-12356 : Authenticate Users to V2 API")
        public void easyAPIPeek() throws Exception {
                String service;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "597");
                testcases.put("TestSuiteID", "502");

                service = apiConfiguration().easyApiPeek();

                json = easyapipeek.easyAPIPeek(dataInput.get("ReferenceID"), startToken);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getPOSTReponseWithContentTypeTextApplicationJson(json.toString(), service,
                                envparam.get("baseEnv"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath resData = response.jsonPath();

                // JSONArray jsonArray = resData.get("Result.Session.Application.Applicants");

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa = assertionUtil.assertEquals("Reference ID", resData.get("Result.Session.ReferenceId"),
                                dataInput.get("ReferenceID"), "ReferenceID");
                // System.out.println("The Solution is: " + ApplicantsData);
                sa.assertAll();

        }

}
