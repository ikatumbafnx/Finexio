package com.finixio.test.webservices.Communications;

import static com.finixio.config.ConfigurationManager.apiConfiguration;
import static com.finixio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finixio.BaseApi;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.EmailUtil;
import com.finixio.utils.NumberUtils;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;
import com.finixio.webservices.Communications.Emails.ConfirmEmailRequest;
import com.finixio.webservices.Communications.Emails.GetEmailStatusRequest;
import com.finixio.webservices.Communications.Emails.SendEmailRequest;
import com.finixio.webservices.Communications.Emails.VerifyEmailRequest;
import com.finixio.webservices.Communications.Phones.ConfirmPhoneNumberRequest;
import com.finixio.webservices.Communications.Phones.GetPhoneInformationRequest;
import com.finixio.webservices.Communications.Phones.SendMessageRequest;
import com.finixio.webservices.Communications.Phones.VerifyPhoneNumberRequest;
import com.finixio.webservices.EasyAPI.GetAuthTokenRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CommunicationsEmailsApisSmoke extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        ConfirmEmailRequest confirmEmailRequest = new ConfirmEmailRequest();
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        VerifyEmailRequest verifyEmailRequest = new VerifyEmailRequest();
        ConfirmPhoneNumberRequest confirmPhoneNumberRequest = new ConfirmPhoneNumberRequest();
        GetPhoneInformationRequest getPhoneInformationRequest = new GetPhoneInformationRequest();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        VerifyPhoneNumberRequest verifyPhoneNumberRequest = new VerifyPhoneNumberRequest();
        GetEmailStatusRequest getEmailStatusRequest = new GetEmailStatusRequest();
        GetAuthTokenRequest getAuthToken = new GetAuthTokenRequest();
        AssertionUtil assertionUtil = new AssertionUtil();
        EmailUtil emailUtil = new EmailUtil();

        String username = "ikatumba@healthcarefinancedirect.com";
        String password = "Katlynhfd-0830";
        public String emailIdentifier, TempToken;

        private String token, startToken;
        private String AppilicationID;

        @Epic("Communications Email APIs Smoke Run")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "EasyAPI" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-30001 : Authenticate Users to V2 API")
        public void generateToken() throws Exception {
                String service;
                String json;
                SoftAssert sa;
                testcases.put("TestCaseID", "590");
                testcases.put("TestSuiteID", "498");

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

        @Epic("Communications Email APIs Smoke Run")
        @Description("Send an Email")
        @Test(priority = 1, groups = { "Communications", "Email" }, description = "Sending an email")
        @Story("HFD-30002 : Email Communications API Webservices")
        public void sendemail() throws Exception {
                String service;
                String json;
                SoftAssert sa;

                testcases.put("TestCaseID", "591");
                testcases.put("TestSuiteID", "498");

                String value = null;
                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationSendEmail();
                Map<String, String> emailContent = new HashMap<>();
                String Subject = "Automation Testing";
                String From = "donotreply@healthcarefinancedirect.com";
                String Body = "Automation Testing Email";
                String ToAddress = "qaautomation@healthcarefinancedirect.com";
                String ToName = "QA Automation";

                JSONObject emailMainObj = new JSONObject();

                emailMainObj.put("Body", Body);
                emailMainObj.put("From", From);
                emailMainObj.put("Subject", Subject);
                emailMainObj.put("Campaign", "");
                emailMainObj.put("CompanyId", "");
                emailMainObj.put("Meta", value == null ? JSONObject.NULL : value);

                JSONArray RecipientsArray = new JSONArray();
                JSONObject recipientArrayElements = new JSONObject();
                recipientArrayElements.put("Address", ToAddress);
                recipientArrayElements.put("Name", ToName);

                RecipientsArray.put(recipientArrayElements);

                JSONArray CCsArray = new JSONArray();
                // JSONObject CCsArrayElements = new JSONObject();
                // CCsArrayElements.put("Address", CCEmail);
                // CCsArrayElements.put("Name", CCName);

                JSONArray BCCsArray = new JSONArray();
                // JSONObject BCCsArrayElements = new JSONObject();
                // BCCsArrayElements.put("Address", BCCEmail);
                // BCCsArrayElements.put("Name", BCCName);

                JSONArray AttachmentsArray = new JSONArray();
                // JSONObject AttachmentsArrayElements = new JSONObject();
                // AttachmentsArrayElements.put("Address", BCCEmail);
                // AttachmentsArrayElements.put("Name", BCCName);

                // AttachmentsArray.put(AttachmentsArrayElements);

                emailMainObj.put("Recipients", RecipientsArray);
                emailMainObj.put("Attachments", AttachmentsArray);
                // CCsArray.put(CCsArrayElements);
                emailMainObj.put("CC", CCsArray);
                // BCCsArray.put(BCCsArrayElements);
                emailMainObj.put("BCC", BCCsArray);

                json = emailMainObj.toString();// sendEmailRequest.SendMail(emailMainObj);

                jsonFormatter.printFormattedJson(json, "Request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json, service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath emailData = response.jsonPath();

                // emailContent = emailUtil.check(username, password);

                // sa = assertionUtil.assertEquals("Status",
                // emailData.getString("Result.Status"), "Sent",
                // "The Email was not succefully sent");

                // sa = assertionUtil.assertEquals("Verifying that email subject",
                // emailContent.get("Subject"), Subject,
                // "The Email Subject Does Not Match");

                // sa = assertionUtil.assertEquals("Verifying that email was sent from",
                // emailContent.get("From"), From,
                // "The Email Sent From Address Does Not Match");

                // sa = assertionUtil.assertEquals("Verifying that email content sent was",
                // emailContent.get("Text"), Body,
                // "The Email Sent From Address Does Not Match");

                // sa.assertAll();

        }

        @Epic("Communications Email APIs Smoke Run")
        @Description("Start email verification process. This will send a templated email to the provided email address")
        @Test(priority = 2, groups = {
                        "Communications",
                        "Email" }, description = "Start email verification process. This will send a templated email to the provided email address")
        @Story("HFD-300056 : Email Verification Communications API Webservices")
        public void VerifyEmailAddress() throws Exception {
                SoftAssert sa;
                Object json;

                testcases.put("TestCaseID", "592");
                testcases.put("TestSuiteID", "498");

                JSONObject emailMainObj = new JSONObject();
                String service = apiConfiguration().communicationVerifyEmail();

                emailMainObj.put("To", "ikatumba@healthcarefinancedirect.com");
                emailMainObj.put("From", "donotreply@healthcarefinancedirect.com");
                emailMainObj.put("Subject", "Verify your Email Address");
                emailMainObj.put("Template", "");
                emailMainObj.put("TemplateName", "provider-standard-verification.html");
                emailMainObj.put("TemplateUri", "");

                jsonFormatter.printFormattedJson(emailMainObj.toString(), "Request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(emailMainObj.toString(),
                                service, configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                sa = assertionUtil.assertEquals("Status", response.getStatusCode(), 200,
                                "The Email was not succefully sent");
                emailIdentifier = response.jsonPath().getString("Result.Identifier");
                TempToken = response.jsonPath().getString("Result.TestToken");
                sa.assertAll();

        }

        @Epic("Communications Email APIs Smoke Run")
        @Description(" Gets the status of an email verification given a provided")
        @Test(priority = 3, groups = {
                        "Communications",
                        "Email" }, description = " Gets the status of an email verification given a provided")
        @Story("HFD-30001455 :  Gets the status of an email verification given a provided")
        public void getStatusOfEmailAddress() throws Exception {

                String service;
                String json;
                SoftAssert sa;

                testcases.put("TestCaseID", "593");
                testcases.put("TestSuiteID", "498");

                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationVerifyEmail();
                json = getEmailStatusRequest.getEmailStatus(emailIdentifier);
                Response response = restUtils.getAuthenticatedGETReponseWithNoContentTypeWithBearer(json, service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                sa = assertionUtil.assertEquals("Status", response.getStatusCode(), 200,
                                "The Status Code of the Response");
                sa = assertionUtil.assertEquals("Verifying that ID", response.jsonPath().getString("Result.Id"),
                                emailIdentifier, "The Email was not succefully sent");
                sa.assertAll();

        }

        @Epic("Communications Email APIs Smoke Run")
        @Description("Confirm email with a provided token")
        @Test(priority = 4, groups = { "Communications",
                        "Email" }, description = " Confirm email with a provided token")
        @Story("HFD-30456 :  Confirm email with a provided token")
        public void confirmEmailAddress() throws Exception {

                String service;
                Object json;
                SoftAssert sa;

                testcases.put("TestCaseID", "594");
                testcases.put("TestSuiteID", "498");

                NumberUtils numberUtils = new NumberUtils();
                service = apiConfiguration().communicationConfirmEmail();
                json = confirmEmailRequest.ConfirmEmail(emailIdentifier, TempToken, "");
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json.toString(), service,
                                configuration().devcommunicationsAPIEndpoint(), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                sa = assertionUtil.assertEquals("Status", response.getStatusCode(), 200,
                                "The Status Code of the Response");
                // sa = assertionUtil.assertEquals("Verifying that ID",
                // response.jsonPath().get("Result.Id"),
                // "61b11f595c2fb8757f21a8a1", "The Email was not succefully sent");
                sa.assertAll();

        }

}
