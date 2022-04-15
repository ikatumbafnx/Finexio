package com.finixio.test.webservices.Communications;

import static com.finixio.config.ConfigurationManager.apiConfiguration;
import static com.finixio.config.ConfigurationManager.configuration;

import java.util.HashMap;

import com.finixio.BaseApi;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.NumberUtils;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;
import com.finixio.webservices.Communications.Emails.ConfirmEmailRequest;
import com.finixio.webservices.Communications.Emails.SendEmailRequest;
import com.finixio.webservices.Communications.Emails.VerifyEmailRequest;
import com.finixio.webservices.Communications.Phones.ConfirmPhoneNumberRequest;
import com.finixio.webservices.Communications.Phones.GetPhoneInformationRequest;
import com.finixio.webservices.Communications.Phones.SendMessageRequest;
import com.finixio.webservices.Communications.Phones.VerifyPhoneNumberRequest;
import com.finixio.webservices.EasyAPI.GetAuthTokenRequest;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CommunicationsPhoneApisSmoke extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        ConfirmEmailRequest confirmEmailRequest = new ConfirmEmailRequest();
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        VerifyEmailRequest verifyEmailRequest = new VerifyEmailRequest();
        ConfirmPhoneNumberRequest confirmPhoneNumberRequest = new ConfirmPhoneNumberRequest();
        GetPhoneInformationRequest getPhoneInformationRequest = new GetPhoneInformationRequest();
        GetAuthTokenRequest getAuthToken = new GetAuthTokenRequest();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        VerifyPhoneNumberRequest verifyPhoneNumberRequest = new VerifyPhoneNumberRequest();
        AssertionUtil assertionUtil = new AssertionUtil();
        public String customCode, Identifier;
        public String Message;
        public String TestNumber = "2405517923";
        public String TestMessage = "Thank you for Subscribing";

        private String token, startToken;
        private String AppilicationID;

        @Epic("Communications Phone APIs Smoke Run")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "EasyAPI" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-42369 : Authenticate Users to V2 API")
        public void generateToken() throws Exception {
                String service;
                String json;
                SoftAssert sa;

                testcases.put("TestCaseID", "585");
                testcases.put("TestSuiteID", "499");

                service = configuration().easyApiAuthService();
                json = getAuthToken.getAuthenticationToken(
                                apiConfiguration().communicationgrantType(),
                                apiConfiguration().communicationclientID(),
                                apiConfiguration().communicationClientScreet());

                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service,
                                configuration().devAuthcommunicationsAPIEndpoint());
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("Communications Phone APIs Smoke Run")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 1, groups = { "Communications" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-456321 : Communications API Webservices")
        public void retrievephonelookup() throws Exception {

                testcases.put("TestCaseID", "586");
                testcases.put("TestSuiteID", "499");

                String service;
                String json;
                SoftAssert sa;
                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationAPIGetPhoneInfo();
                json = getPhoneInformationRequest.RetrievePhoneInformation(TestNumber);
                Response response = restUtils.getAuthenticatedGETReponseWithNoContentTypeWithBearer(json, service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath phoneData = response.jsonPath();
                System.out.println("The Country code is: " + phoneData.getString("Result.CountryCode"));
                // System.out.println("The Phone Number is: " +
                // numberUtils.getFirstOrLastFromANumber(phoneData.get("Result.PhoneNumber"),
                // "last", 10));
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("Communications Phone APIs Smoke Run")
        @Description("Verifying the Phone Number Used")
        @Test(priority = 2, groups = { "Communications" }, description = "Verifying the Phone Number Used")
        @Story("HFD-432165 : Verifying the Phone Number Used")
        public void phoneNumberVerification() throws Exception {

                testcases.put("TestCaseID", "587");
                testcases.put("TestSuiteID", "499");

                String service;
                String json;
                SoftAssert sa;
                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationVerifyPhone();
                json = verifyPhoneNumberRequest.VerifyPhoneNumber(TestNumber);
                Response response = restUtils.getAuthentPOSTReponseWithNoContentTypeWithBearer(json, service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath phoneData = response.jsonPath();

                customCode = phoneData.getString("Result.CustomCode");
                Identifier = phoneData.getString("Result.Identifier");
                // System.out.println("The Country code is: " +
                // phoneData.getString("Result.CountryCode"));
                // System.out.println("The Phone Number is: " +
                // numberUtils.getFirstOrLastFromANumber(phoneData.get("Result.PhoneNumber"),
                // "last", 10));
                sa = assertionUtil.assertEquals("Status",
                                String.valueOf(response.statusCode()), "200", "Call returned Error");
                sa.assertAll();

        }

        @Epic("Communications Phone APIs Smoke Run")
        @Description("Confirm the Phone Number with the Verfification Code Recieved")
        @Test(priority = 3, groups = {
                        "Communications" }, description = "Confirm the Phone Number with the Verfification Code Recieved")
        @Story("HFD-40002 : Confirm the Phone Number with the Verfification Code Recieved")
        public void phoneNumberConfirmation() throws Exception {

                testcases.put("TestCaseID", "588");
                testcases.put("TestSuiteID", "499");

                String service;
                Object json;
                SoftAssert sa;
                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationConfirmPhone();
                json = confirmPhoneNumberRequest.ConfirmPhoneNumber(TestNumber, customCode, Identifier);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json.toString(), service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                // JsonPath phoneData = response.jsonPath();
                // System.out.println("The Country code is: " +
                // phoneData.getString("Result.CountryCode"));
                // System.out.println("The Phone Number is: " +
                // numberUtils.getFirstOrLastFromANumber(phoneData.get("Result.PhoneNumber"),
                // "last", 10));
                // sa = assertionUtil.assertEquals("Status",
                // String.valueOf(response.statusCode()), "200", "Call returned Error");
                // sa.assertAll();

        }

        @Epic("Communications Phone APIs Smoke Run")
        @Description("Sending SMS to Provided Phone Number")
        @Test(priority = 4, groups = { "Communications" }, description = "Sending SMS to Provided Phone Number")
        @Story("HFD-4001 : Sending SMS to Provided Phone Number")
        public void sendSMSToPhone() throws Exception {

                testcases.put("TestCaseID", "589");
                testcases.put("TestSuiteID", "499");

                String service;
                Object json;
                SoftAssert sa;
                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationSendSMS();
                json = sendMessageRequest.SendMessageSMS(TestNumber, TestMessage, "", "");
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json.toString(), service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                // JsonPath phoneData = response.jsonPath();
                // System.out.println("The Country code is: " +
                // phoneData.getString("Result.CountryCode"));
                // System.out.println("The Phone Number is: " +
                // numberUtils.getFirstOrLastFromANumber(phoneData.get("Result.PhoneNumber"),
                // "last", 10));
                // sa = assertionUtil.assertEquals("Status",
                // String.valueOf(response.statusCode()), "200", "Call returned Error");
                // sa.assertAll();

        }

}
