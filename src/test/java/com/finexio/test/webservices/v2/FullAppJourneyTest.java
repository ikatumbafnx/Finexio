package com.finexio.test.webservices.v2;

import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finexio.BaseApi;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.DateUtils;
import com.finexio.utils.api.JsonFormatter;
import com.finexio.utils.api.RestUtils;
import com.finexio.webservices.v2.ActivateAccountRequest;
import com.finexio.webservices.v2.CreateAddendumRequest;
import com.finexio.webservices.v2.CreateAgreementRequest;
import com.finexio.webservices.v2.CreateApplicationRequest;
import com.finexio.webservices.v2.ESignAddendumRequest;
import com.finexio.webservices.v2.ESignAgreementRequest;
import com.finexio.webservices.v2.EmailAgreementRequest;
import com.finexio.webservices.v2.GetAccountDetails;
import com.finexio.webservices.v2.GetTokenRequest;
import com.finexio.webservices.v2.UpdateApplicationRequest;
import com.github.javafaker.Faker;

import org.json.JSONObject;
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
public class FullAppJourneyTest extends BaseApi {
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

        @Epic("Full App Journey v2")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "v2" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-00001 : Authenticate Users to V2 API")
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

        @Epic("Full App Journey v2")
        @Description("Creating a Patient Application for Financing")
        @Test(priority = 1, groups = { "v2" }, description = "Creating a Patient Application for Financing")
        @Story("HFD-8965 : Create Account V2")
        public void createApplication() throws Exception {
                String service, FirstName, LastName, CardName;
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

        @Epic("Full App Journey v2")
        @Description("Updating  Patient Application for Financing")
        @Test(priority = 2, groups = { "v2" }, description = "Updating Patient Application for Financing")
        @Story("HFD-00002 : Update Account Information")
        public void updateApplication() throws Exception {
                String service;
                Object json;
                service = configuration().updateapplication();
                SoftAssert sa;
                JSONObject updateApplicationObj = new JSONObject();
                updateApplicationObj.put("IsTest", true);
                updateApplicationObj.put("ApplicationID", AppilicationID);

                JSONObject applicant = new JSONObject();
                applicant.put("FirstName", "Jake");
                applicant.put("LastName", "Joseph");
                applicant.put("Email", faker.internet().emailAddress());
                applicant.put("Phone", "17777777777");

                /**
                 * Addind to the application Data
                 */
                accountDetailsData.put("FirstName", "Jake");
                accountDetailsData.put("LastName", "Joseph");
                accountDetailsData.put("Email", applicant.getString("Email"));
                accountDetailsData.put("Phone", applicant.getString("Phone"));

                accountDetailsData.put("CardFirstName", "Jake");
                accountDetailsData.put("CardLastName", "Joseph");
                accountDetailsData.put("NameOnCard", "Jake Joseph");

                JSONObject address = new JSONObject();
                address.put("Street", faker.address().streetName());
                address.put("City", faker.address().cityName());
                address.put("Statoid", applicationData.get("State"));
                address.put("Zip", "78737");
                address.put("CountryCode", "US");

                applicant.put("Address", address);
                accountDetailsData.put("Street", address.getString("Street"));
                accountDetailsData.put("City", address.getString("City"));
                accountDetailsData.put("Statoid", address.getString("Statoid"));
                accountDetailsData.put("Zip", "78737");
                accountDetailsData.put("CountryCode", address.getString("CountryCode"));

                JSONObject terms = new JSONObject();

                terms.put("FinanceAmount", 1349.00);
                terms.put("DownPaymentAmount", 0.00);
                terms.put("TermMonths", 12);
                terms.put("Statoid", applicationData.get("State"));

                accountDetailsData.put("Statoid", terms.getString("Statoid"));
                accountDetailsData.put("TermMonths", terms.get("TermMonths"));
                accountDetailsData.put("PrincipalAmount", terms.get("FinanceAmount"));

                JSONObject payment = new JSONObject();
                JSONObject primaryPaymentMethod = new JSONObject();
                primaryPaymentMethod.put("Type", "Credit");

                JSONObject card = new JSONObject();
                card.put("NameOnCard", "Jake Joseph");
                card.put("CardNumber", "4111111111111111");
                card.put("ExpirationMonth", 12);
                card.put("ExpirationYear", 2022);

                accountDetailsData.put("CardNumber", card.getString("CardNumber"));
                accountDetailsData.put("ExpirationMonth", card.get("ExpirationMonth"));
                accountDetailsData.put("ExpirationYear", card.get("ExpirationYear"));

                primaryPaymentMethod.put("Card", card);
                payment.put("PrimaryPaymentMethod", primaryPaymentMethod);
                updateApplicationObj.put("Applicant", applicant);
                updateApplicationObj.put("Terms", terms);
                updateApplicationObj.put("Payment", payment);

                json = updateApplicationRequest.UpdateApplicationOnDemand(updateApplicationObj);

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

        @Epic("Full App Journey v2")
        @Description("Creating  Patient Agreement  for Financing")
        @Test(priority = 3, groups = { "v2" }, description = "Creating  Patient Agreement for Financing")
        @Story("HFD-00004 : Create Agreement for patient")
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

        @Epic("Full App Journey v2")
        @Description("ESigning  Patient Agreement  for Financing")
        @Test(priority = 4, groups = { "v2" }, description = "Patient ESigining the Agreement for Financing")
        @Story("HFD-00005 : Create Agreement for patient")
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

        @Epic("Full App Journey v2")
        @Description("Emailing signed Agreement to the Patient")
        @Test(priority = 5, groups = { "v2" }, description = "Emailing signed Agreement to the Patient")
        @Story("HFD-00006 : Email Agreement to patient")
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

        @Epic("Full App Journey v2")
        @Description("Creating an Addendum for the Patient to Sign")
        @Test(priority = 6, groups = { "v2" }, description = "Creating an Addendum for the Patient to Sign")
        @Story("HFD-9001 : Create an Addendum")
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

        @Epic("Full App Journey v2")
        @Description("Patient Signing the  Addendum")
        @Test(priority = 7, groups = { "v2" }, description = "Patient Signing the  Addendum")
        @Story("HFD-9401 : Ability for Patient to sign an addendum")
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

        @Epic("Full App Journey v2")
        @Description("Activate Patient Account")
        @Test(priority = 8, groups = { "v2" }, description = "Activate Patient Account")
        @Story("HFD-10236 : Ability to Activate Patient Account")
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

        @Epic("Full App Journey v2")
        @Description("Get Account Details of the Patient")
        @Test(priority = 9, groups = { "v2" }, description = "Get Account Details of the Patient")
        @Story("HFD-102436 : Get Account Details of the Patient")

        public void getAccountDetails() throws Exception {
                String service;
                Object json;
                service = configuration().getaccountdetails();
                SoftAssert sa;
                json = getAccountDetails.getAccountDetails(AppilicationID);

                Response response = restUtils.getStringGETReponse(json, service, envparam.get("baseEnv"),
                                "Bearer " + token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                sa = assertionUtil.assertEquals("Status", accountData.get("Status").toString(), "200",
                                "The Status Code ");
                sa = assertionUtil.assertEquals("Application ID", accountData.get("ApplicationId"),
                                Integer.parseInt(AppilicationID), "The Application ID ");
                sa = assertionUtil.assertEquals("Applicant FirstName", accountData.get("Applicant.FirstName"),
                                accountDetailsData.get("FirstName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("Applicant LastName", accountData.get("Applicant.LastName"),
                                accountDetailsData.get("LastName"), "The Application FirstName  ");
                sa = assertionUtil.assertEquals("Applicant Email", accountData.get("Applicant.Email"),
                                accountDetailsData.get("Email"), "The Application Email  ");
                sa = assertionUtil.assertEquals("Applicant NIN", accountData.get("Applicant.NIN"),
                                accountDetailsData.get("NIN"), "The Application NIN  ");
                sa = assertionUtil.assertEquals("Applicant Phone", accountData.get("Applicant.Phone"),
                                accountDetailsData.get("Phone"), "The Application Phone  ");
                sa = assertionUtil.assertEquals("Applicant Street", accountData.get("Applicant.Address.Street"),
                                accountDetailsData.get("Street"), "The Applicant Street  ");
                sa = assertionUtil.assertEquals("Applicant Zip Code", accountData.get("Applicant.Address.Zip"),
                                accountDetailsData.get("Zip"), "The Applicant Zip  ");
                sa = assertionUtil.assertEquals("Applicant CountryCode",
                                accountData.get("Applicant.Address.CountryCode"), accountDetailsData.get("CountryCode"),
                                "The Applicant CountryCode  ");
                sa = assertionUtil.assertEquals("PersonReceivingServices FirstName",
                                accountData.get("PersonReceivingServices.FirstName"),
                                accountDetailsData.get("PersonReceivingServicesFirstName"),
                                "The PersonReceivingServicesFirstName  ");
                sa = assertionUtil.assertEquals("PersonReceivingServices LastName",
                                accountData.get("PersonReceivingServices.LastName"),
                                accountDetailsData.get("PersonReceivingServicesLastName"),
                                "The PersonReceivingServicesLastName  ");
                sa = assertionUtil.assertEquals("Applicant Principal Loan Amount",
                                accountData.get("Terms.PrincipalAmount").toString(),
                                accountDetailsData.get("PrincipalAmount").toString(),
                                "The Applicant PrincipalAmount  ");
                sa = assertionUtil.assertEquals("Applicant State", accountData.get("Terms.Statoid"),
                                accountDetailsData.get("Statoid"), "The Applicant Statoid  ");
                sa = assertionUtil.assertEquals("Applicant Payment TermMonths", accountData.get("Terms.TermMonths"),
                                accountDetailsData.get("TermMonths"), "The Applicant TermMonths  ");

                // sa = assertionUtil.assertEquals("Applicant Finance Date",
                // accountData.get("Terms.FinanceDate"),
                // accountDetailsData.get("FinanceDate"), "The Applicant FinanceDate ");
                // sa = assertionUtil.assertEquals("Applicant First Payment DueDate",
                // accountData.get("Terms.FirstPaymentDueDate"),
                // accountDetailsData.get("FirstPaymentDueDate"), "The Applicant
                // FirstPaymentDueDate ");

                sa = assertionUtil.assertEquals("Applicant NameOnCard",
                                accountData.get("PrimaryPaymentMethod.Card.NameOnCard"),
                                accountDetailsData.get("NameOnCard"), "The Applicant NameOnCard  ");
                sa = assertionUtil.assertEquals("Applicant CardFirstName",
                                accountData.get("PrimaryPaymentMethod.Card.CardFirstName"),
                                accountDetailsData.get("CardFirstName"), "The Applicant CardFirstName  ");
                sa = assertionUtil.assertEquals("Applicant CardLastName",
                                accountData.get("PrimaryPaymentMethod.Card.CardLastName"),
                                accountDetailsData.get("CardLastName"), "The Applicant CardLastName  ");
                sa = assertionUtil.assertEquals("Applicant CardNumber",
                                accountData.get("PrimaryPaymentMethod.Card.CardNumber"),
                                accountDetailsData.get("CardNumber"), "The Applicant CardNumber  ");
                sa = assertionUtil.assertEquals("Card ExpirationMonth",
                                accountData.get("PrimaryPaymentMethod.Card.ExpirationMonth"),
                                accountDetailsData.get("ExpirationMonth"), "The Card ExpirationMonth  ");
                sa = assertionUtil.assertEquals("Card ExpirationYear",
                                accountData.get("PrimaryPaymentMethod.Card.ExpirationYear"),
                                accountDetailsData.get("ExpirationYear"), "The Card ExpirationYear  ");

                sa.assertAll();

        }

        // public void test() throws ParseException {
        // DateUtils dateUtils = new DateUtils();
        // System.out.println(dateUtils.getDate("YYYY-MM-dd"));
        // String firstPaymentDueDate = dateUtils.convertDateToPattern(
        // dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"),
        // DateUtils.EChangeFormat.Day, +10),
        // "MM/dd/YYYY", "YYYY-MM-dd");
        // System.out.println(firstPaymentDueDate);
        // // System.out.println(dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"),
        // // DateUtils.EChangeFormat.Day, +10));
        // // String datenow = dateUtils.changeDate(dateUtils.getDate("MM/dd/YYYY"),
        // // DateUtils.EChangeFormat.Day, +10);
        // //
        // System.out.println(dateUtils.convertDateToPattern(datenow,"MM/dd/YYYY","YYYY-MM-dd"));
        // }

}
