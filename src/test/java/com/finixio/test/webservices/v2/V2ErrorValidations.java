package com.finixio.test.webservices.v2;

import static com.finixio.config.ConfigurationManager.configuration;
import static com.finixio.config.ConfigurationManager.errorDictionary;

import java.util.HashMap;
import java.util.Map;

import com.finixio.BaseApi;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.DateUtils;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;
import com.finixio.webservices.v2.ActivateAccountRequest;
import com.finixio.webservices.v2.CreateAddendumRequest;
import com.finixio.webservices.v2.CreateAgreementRequest;
import com.finixio.webservices.v2.CreateApplicationRequest;
import com.finixio.webservices.v2.ESignAddendumRequest;
import com.finixio.webservices.v2.ESignAgreementRequest;
import com.finixio.webservices.v2.EmailAgreementRequest;
import com.finixio.webservices.v2.GetAccountDetails;
import com.finixio.webservices.v2.GetTokenRequest;
import com.finixio.webservices.v2.UpdateApplicationRequest;
import com.github.javafaker.Faker;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
@Feature("Smile Direct")
public class V2ErrorValidations extends BaseApi {
        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        GetTokenRequest getTokenRequest = new GetTokenRequest();
        CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
        UpdateApplicationRequest updateApplicationRequest = new UpdateApplicationRequest();
        CreateAgreementRequest createAgreementRequest = new CreateAgreementRequest();
        ESignAgreementRequest eSignAgreementRequest = new ESignAgreementRequest();
        EmailAgreementRequest emailAgreementRequest = new EmailAgreementRequest();
        CreateAddendumRequest createAddendumRequest = new CreateAddendumRequest();
        ESignAddendumRequest eSignAddendumRequest = new ESignAddendumRequest();
        ActivateAccountRequest activateAccountRequest = new ActivateAccountRequest();
        GetAccountDetails getAccountDetails = new GetAccountDetails();
        DateUtils dateUtils = new DateUtils();

        AssertionUtil assertionUtil = new AssertionUtil();

        private String token;
        private String AppilicationID;
        Faker faker = new Faker();
        Map<String, String> applicationData = getApplicationData();
        Map<Object, Object> accountDetailsData = new HashMap<>();

        @Epic("V2 Full App Journey Regression")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "v2" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-2100 : Authenticate Users to V2 API")
        public void generateToken() throws Exception {
                String service;
                String json;
                SoftAssert sa;
                testcases = new HashMap<>();
                service = configuration().authenticate();
                json = getTokenRequest.GetToken("password", "SmileDirectClubStage@hfd.com", "A8B-8PsZrs3mK3y", "v2");
                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service,
                                envparam.get("baseEnv"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Creating a Patient Application for Financing")
        @Test(priority = 1, groups = { "v2" }, description = "Creating a Patient Application for Financing")
        @Story("HFD-2102 : Create Account V2")
        public void createApplication() throws Exception {
                String service;
                Object json;
                service = configuration().createapplication();
                SoftAssert sa;
                json = createApplicationRequest.CreateApplication(applicationData.get("FirstName"),
                                applicationData.get("LastName"), applicationData.get("Email"), "17777777777",
                                "1999-11-08", 666946673, null, null, applicationData.get("Street"),
                                applicationData.get("CityName"), applicationData.get("State"),
                                applicationData.get("ZipCode"), "US", 1349.00, 0.00, 12, "Credit", "John Wayne",
                                "4111111111111111", 12, 2022, null, null, true, 4357);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");

                AppilicationID = accountData.get("ApplicationId").toString();
                sa.assertAll();
                accountDetailsData.put("DOB", "1999-11-08");
                accountDetailsData.put("NIN", "666946673");
                accountDetailsData.put("PersonReceivingServicesFirstName", applicationData.get("FirstName"));
                accountDetailsData.put("PersonReceivingServicesLastName", applicationData.get("LastName"));

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Creating  Patient Agreement  for Financing")
        @Test(priority = 2, groups = { "v2" }, description = "Creating  Patient Agreement for Financing")
        @Story("HFD-2105 : Create Agreement for patient")
        public void createAgreement() throws Exception {
                String service;
                Object json;
                service = configuration().createAgreement();
                SoftAssert sa;
                json = createAgreementRequest.createAgreement(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("ESigning  Patient Agreement  for Financing")
        @Test(priority = 3, groups = { "v2" }, description = "Patient ESigining the Agreement for Financing")
        @Story("HFD-21001 : Create Agreement for patient")
        public void eSignAgreement() throws Exception {
                String service;
                Object json;
                service = configuration().eSignAgreement();
                SoftAssert sa;
                json = eSignAgreementRequest.eSignAgreement(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Emailing signed Agreement to the Patient")
        @Test(priority = 4, groups = { "v2" }, description = "Emailing signed Agreement to the Patient")
        @Story("HFD-210023 : Email Agreement to patient")
        public void emailAgreement() throws Exception {
                String service;
                Object json;
                service = configuration().emailAgreement();
                SoftAssert sa;
                json = emailAgreementRequest.emailAgreement(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Creating an Addendum for the Patient to Sign")
        @Test(priority = 5, groups = { "v2" }, description = "Creating an Addendum for the Patient to Sign")
        @Story("HFD-210032 : Create an Addendum")
        public void createAddendum() throws Exception {
                String service, financeDate, firstPaymentDueDate;
                Object json;
                SoftAssert sa;
                service = configuration().createAddendum();
                financeDate = dateUtils.getDate("YYYY-MM-dd");
                firstPaymentDueDate = dateUtils.convertDateToPattern(
                                dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"), DateUtils.EChangeFormat.Day, +10),
                                "MM/dd/YYYY", "YYYY-MM-dd");
                accountDetailsData.put("FinanceDate",
                                dateUtils.convertDateToPattern(financeDate, "YYYY-MM-dd", "M/dd/YYYY"));
                accountDetailsData.put("FirstPaymentDueDate",
                                dateUtils.convertDateToPattern(firstPaymentDueDate, "YYYY-MM-dd", "M/dd/YYYY"));

                json = createAddendumRequest.createAddendum(AppilicationID, financeDate, firstPaymentDueDate);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Patient Signing the  Addendum")
        @Test(priority = 6, groups = { "v2" }, description = "Patient Signing the  Addendum")
        @Story("HFD-2100654 : Ability for Patient to sign an addendum")
        public void eSignAddendum() throws Exception {
                String service;
                Object json;
                service = configuration().eSignAddendum();
                SoftAssert sa;
                json = eSignAddendumRequest.eSignAddendum(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa.assertAll();

        }

        @Epic("V2 Full App Journey Regression")
        @Description("Activate Patient Account")
        @Test(priority = 7, groups = { "v2" }, description = "Activate Patient Account")
        @Story("HFD-1023612 : Ability to Activate Patient Account")
        public void activateAccount() throws Exception {
                String service;
                Object json;
                service = configuration().activateAccount();
                SoftAssert sa;
                json = activateAccountRequest.activateAccount(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("AppStatus.ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa = assertionUtil.assertEquals("Application Status", accountData.get("AppStatus.Status"), "CONF",
                                "The Application Status is invalid ");
                sa.assertAll();

        }

        /*****
         * Validations of Error Messages for the above API Calls After a Succesfull Run
         * 
         * 
         */

        @Epic("V2 Full App Journey Regression")
        @Description("Create Agreement Error Validation ")
        @Test(priority = 8, groups = { "v2" }, description = "Create Agreement Error Validation")
        @Story("HFD-395785 : Create Agreement for patient")
        public void createAgreementErrorValidations() throws Exception {
                String service;
                Object json;
                service = configuration().createAgreement();
                SoftAssert sa;
                json = createAgreementRequest.createAgreement(AppilicationID);

                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthentPOSTReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "400",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Error Code", accountData.get("ErrorCode.Code").toString(), "1065",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Error Message", accountData.get("ErrorCode.Message").toString(),
                                errorDictionary().Err1065(),
                                "The Error Code Message ");

                sa.assertAll();

        }

        // @Epic("V2 Error Validations")
        // @Description("ESigning API Error Validations")
        // @Test(priority = 9, groups = { "v2" }, description = "ESigning API Error
        // Validations")
        // @Story("HFD-5986 : ESigning API Error Validations")
        // public void eSignAgreementErrorValidation() throws Exception {
        // String service;
        // Object json;
        // service = configuration().eSignAgreement();
        // SoftAssert sa;
        // json = eSignAgreementRequest.eSignAgreement(AppilicationID);

        // jsonFormatter.printFormattedJson(json.toString(), "request", service);
        // Response response = restUtils.getAuthentPOSTReponse(json, service,
        // configuration().apiendpoint(),
        // "Bearer " + token);
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        // JsonPath accountData = response.jsonPath();
        // sa = assertionUtil.assertEquals("Status",
        // accountData.get("Status").toString(), "400",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Error Code",
        // accountData.get("ErrorCode.Code").toString(), "1065",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Error Message",
        // accountData.get("ErrorCode.Message").toString(),
        // "New agreements cannot be created after an agreement has already been signed.
        // Create an addendum to reflect any changes.",
        // "The Error Code Message ");

        // sa.assertAll();

        // }

        // @Epic("V2 Error Validations")
        // @Description("Send Email Error Validations")
        // @Test(priority = 10, groups = { "v2" }, description = "Send Email Error
        // Validations")
        // @Story("HFD-5236 : Send Email Error Validations")
        // public void emailAgreementErrorValidation() throws Exception {
        // String service;
        // Object json;
        // service = configuration().emailAgreement();
        // SoftAssert sa;
        // json = emailAgreementRequest.emailAgreement(AppilicationID);

        // jsonFormatter.printFormattedJson(json.toString(), "request", service);
        // Response response = restUtils.getAuthentPOSTReponse(json, service,
        // configuration().apiendpoint(),
        // "Bearer " + token);
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        // JsonPath accountData = response.jsonPath();
        // sa = assertionUtil.assertEquals("Status",
        // accountData.get("Status").toString(), "200",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Application ID",
        // accountData.get("ApplicationId"),
        // Integer.parseInt(AppilicationID), "The Application ID ");
        // sa.assertAll();

        // }

        // @Epic("V2 Error Validations")
        // @Description("Create Addendum Error Validation")
        // @Test(priority = 11, groups = { "v2" }, description = "Create Addendum Error
        // Validation")
        // @Story("HFD-9001 : Create Addendum Error Validation")
        // public void createAddendumErrorValidation() throws Exception {
        // String service, financeDate, firstPaymentDueDate;
        // Object json;
        // SoftAssert sa;
        // service = configuration().createAddendum();
        // financeDate = dateUtils.getDate("YYYY-MM-dd");
        // firstPaymentDueDate = dateUtils.convertDateToPattern(
        // dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"),
        // DateUtils.EChangeFormat.Day, +10),
        // "MM/dd/YYYY", "YYYY-MM-dd");
        // accountDetailsData.put("FinanceDate",
        // dateUtils.convertDateToPattern(financeDate, "YYYY-MM-dd", "M/dd/YYYY"));
        // accountDetailsData.put("FirstPaymentDueDate",
        // dateUtils.convertDateToPattern(firstPaymentDueDate, "YYYY-MM-dd",
        // "M/dd/YYYY"));

        // json = createAddendumRequest.createAddendum(AppilicationID, financeDate,
        // firstPaymentDueDate);

        // jsonFormatter.printFormattedJson(json.toString(), "request", service);
        // Response response = restUtils.getAuthentPOSTReponse(json, service,
        // configuration().apiendpoint(),
        // "Bearer " + token);
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        // JsonPath accountData = response.jsonPath();
        // sa = assertionUtil.assertEquals("Status",
        // accountData.get("Status").toString(), "200",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Application ID",
        // accountData.get("ApplicationId"),
        // Integer.parseInt(AppilicationID), "The Application ID ");
        // sa.assertAll();

        // }

        // @Epic("V2 Error Validations")
        // @Description("Esign Addendum Error Validations")
        // @Test(priority = 12, groups = { "v2" }, description = "Esign Addendum Error
        // Validations")
        // @Story("HFD-9401 : Esign Addendum Error Validations")
        // public void eSignAddendumErrorValidations() throws Exception {
        // String service;
        // Object json;
        // service = configuration().eSignAddendum();
        // SoftAssert sa;
        // json = eSignAddendumRequest.eSignAddendum(AppilicationID);

        // jsonFormatter.printFormattedJson(json.toString(), "request", service);
        // Response response = restUtils.getAuthentPOSTReponse(json, service,
        // configuration().apiendpoint(),
        // "Bearer " + token);
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        // JsonPath accountData = response.jsonPath();
        // sa = assertionUtil.assertEquals("Status",
        // accountData.get("Status").toString(), "200",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Application ID",
        // accountData.get("ApplicationId"),
        // Integer.parseInt(AppilicationID), "The Application ID ");
        // sa.assertAll();

        // }

        // @Epic("V2 Error Validations")
        // @Description("Activate Account Error Valiodations")
        // @Test(priority = 13, groups = { "v2" }, description = "Activate Account Error
        // Valiodations")
        // @Story("HFD-10236 : Activate Account Error Valiodations")
        // public void activateAccountErrorValidation() throws Exception {
        // String service;
        // Object json;
        // service = configuration().activateAccount();
        // SoftAssert sa;
        // json = activateAccountRequest.activateAccount(AppilicationID);

        // jsonFormatter.printFormattedJson(json.toString(), "request", service);
        // Response response = restUtils.getAuthentPOSTReponse(json, service,
        // configuration().apiendpoint(),
        // "Bearer " + token);
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        // JsonPath accountData = response.jsonPath();
        // sa = assertionUtil.assertEquals("Status",
        // accountData.get("Status").toString(), "200",
        // "The Status Code ");
        // sa = assertionUtil.assertEquals("Application ID",
        // accountData.get("AppStatus.ApplicationId"),
        // Integer.parseInt(AppilicationID), "The Application ID ");
        // sa = assertionUtil.assertEquals("Application Status",
        // accountData.get("AppStatus.Status"), "CONF",
        // "The Application Status is invalid ");
        // sa.assertAll();

        // }

}
