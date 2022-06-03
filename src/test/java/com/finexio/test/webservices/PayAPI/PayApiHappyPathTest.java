package com.finexio.test.webservices.PayAPI;

import static com.finexio.config.ConfigurationManager.apiConfiguration;
import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finexio.BaseApi;
import com.finexio.utils.AssertionUtil;
import com.finexio.utils.DateUtils;
import com.finexio.utils.api.JsonFormatter;
import com.finexio.utils.api.RestUtils;
import com.finexio.webservices.PayAPI.GetGateWayRequest;
import com.finexio.webservices.PayAPI.WorldPayRequest;
import com.finexio.webservices.v3.AuthenticateRequest;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import groovy.transform.ToString;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PayApiHappyPathTest extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        AuthenticateRequest v3GetToken = new AuthenticateRequest();
        DateUtils dateUtils = new DateUtils();
        GetGateWayRequest getGateWayRequest = new GetGateWayRequest();
        WorldPayRequest payRequest = new WorldPayRequest();
        Map<String, Object> paydataInput = new HashMap<>();

        public String token, merchantIDWP, merchantIDCS;

        @Epic("PayAPI Application Happy Path")
        @Description("Retrieve an Access Authorization Token")
        @Test(priority = 0, groups = { "v3" }, description = "Retrieve an Access Authorization Token")
        @Story("HFD-12356 : Authenticate Users to V3 API")
        public void generateToken() throws Exception {

                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                String json;
                testcases.put("TestCaseID", "598");
                testcases.put("TestSuiteID", "303");

                service = configuration().easyApiAuthService();
                json = v3GetToken.GetToken(envparam.get("grantType"), envparam.get("clientID"),
                                envparam.get("clientSecret"));
                Response response = restUtils.getPOSTReponseWithContentTypeText(json, service, envparam.get("authUrl"));
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath tokenData = response.jsonPath();
                token = tokenData.get("access_token");
                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");
                sa.assertAll();

        }

        @Epic("PayAPI Application Happy Path")
        @Description("Getting all Gateways  that are supported for World Pay ")
        @Test(priority = 1, groups = { "v3" }, description = "Getting all Gateways  that are supported for World Pay ")
        @Story("HFD-39 : Getting all Gateways  that are supported for World Pay ")
        public void getAllGateWays() throws Exception {
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String service;
                Object json;

                testcases.put("TestCaseID", "599");
                testcases.put("TestSuiteID", "303");

                service = apiConfiguration().getGateways();
                json = getGateWayRequest.getGateways();
                // jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedGETReponseWithNoContentTypeNoBodyWithBearer(service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath accountData = response.jsonPath();
                merchantIDWP = accountData.getString("result.gatewayStatuses[1].merchantsSupported[0]");
                merchantIDCS = accountData.getString("result.gatewayStatuses[0].merchantsSupported[0]");

                System.out.println("The different value of WP : " + merchantIDWP);
                System.out.println("The different value  CS: " + merchantIDCS);

        }

        @Epic("PayAPI Application Happy Path")
        @Description("Paying with WorldPay Merchant")
        @Test(priority = 2, groups = { "v3" }, description = "Paying with WorldPay Merchant")
        @Story("HFD-385 : Paying with WorldPay Merchant")
        public void payWithWorldPay() throws Exception {
                String service, link;
                Object json;
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String bankinfo = null;
                testcases.put("TestCaseID", "600");
                testcases.put("TestSuiteID", "303");

                service = apiConfiguration().payApimakePayment();

                paydataInput.put("ReferenceID", "3212312");
                paydataInput.put("merchantId", "010049641");
                paydataInput.put("nameOnAccount", "Joe Doe");
                paydataInput.put("amount.total", 5000);
                paydataInput.put("amount.currencyCode", 145);
                paydataInput.put("nameOnCard", "Joe Doe");
                paydataInput.put("cardnumber", "4457010000000009");
                paydataInput.put("cardExpiration", "0349");
                paydataInput.put("cardbin", "ThisShouldBeApproved");
                paydataInput.put("cardlastFour", "0009");
                paydataInput.put("cardtoken", "RandomTokenGenerated");
                paydataInput.put("postalCode", "90210");
                paydataInput.put("address", "123 Elm St");
                paydataInput.put("city", "Beverly Hills");
                paydataInput.put("state", "CA");
                paydataInput.put("bankInfo", bankinfo);

                paydataInput.put("zipCode", "90210");
                paydataInput.put("countryCode", "US");

                json = payRequest.payWithWorldPay(paydataInput);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath paymentDaPath = response.jsonPath();

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Paid Amount",
                                paymentDaPath.get("result.amount.total"), paydataInput.get("amount.total"),
                                "Paid Amount");

                sa = assertionUtil.assertEquals("The currencyCode",
                                paymentDaPath.get("result.amount.currencyCode"),
                                paydataInput.get("amount.currencyCode"),
                                "The currencyCode");

                // sa = assertionUtil.assertEquals("The merchantId",
                // paymentDaPath.get("result.merchantId"), paydataInput.get("merchantId"),
                // "The merchantId");

                sa = assertionUtil.assertEquals("The Payment Code Description",
                                paymentDaPath.get("result.code.description"), "Approved",
                                "The Payment Code Description");

                sa = assertionUtil.assertEquals("Payment Status",
                                paymentDaPath.get("result.isApproved"), true,
                                "Payment Status");

                sa.assertAll();

        }

        @Epic("PayAPI Application Happy Path")
        @Description("Paying with CyberSource Merchant")
        @Test(priority = 3, groups = { "v3" }, description = "Paying with CyberSource Merchant")
        @Story("HFD-385 : Paying with CyberSource Merchant")
        public void payWithCyberSource() throws Exception {
                String service;
                Object json;
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String bankinfo = null;
                testcases.put("TestCaseID", "601");
                testcases.put("TestSuiteID", "303");

                service = apiConfiguration().payApimakePayment();

                paydataInput.put("ReferenceID", "3212312");
                paydataInput.put("merchantId", "wfghfd4");
                paydataInput.put("nameOnAccount", "Joe Doe");
                paydataInput.put("amount.total", 5000.0);
                paydataInput.put("amount.currencyCode", 145);
                paydataInput.put("nameOnCard", "Joe Doe");
                paydataInput.put("cardnumber", "4457010000000009");
                paydataInput.put("cardExpiration", "0349");
                paydataInput.put("cardbin", "ThisShouldBeApproved");
                paydataInput.put("firstName", "Joe");
                paydataInput.put("lastName", "Doe");
                paydataInput.put("cardlastFour", "0009");
                paydataInput.put("cardtoken", "RandomTokenGenerated");
                paydataInput.put("postalCode", "90210");
                paydataInput.put("address", "123 Elm St");
                paydataInput.put("city", "Beverly Hills");
                paydataInput.put("state", "CA");
                paydataInput.put("bankInfo", bankinfo);

                paydataInput.put("zipCode", "90210");
                paydataInput.put("countryCode", "US");

                JSONObject mainObject = new JSONObject();
                JSONObject amount = new JSONObject();
                JSONObject paymentMethod = new JSONObject();
                JSONObject creditCard = new JSONObject();
                JSONObject address = new JSONObject();

                mainObject.put("referenceId", paydataInput.get("ReferenceID"));
                mainObject.put("merchantId", paydataInput.get("merchantId"));
                mainObject.put("nameOnAccount", paydataInput.get("nameOnAccount"));

                amount.put("total", paydataInput.get("amount.total"));
                amount.put("currencyCode", paydataInput.get("amount.currencyCode"));

                paymentMethod.put("type", 0);
                paymentMethod.put("priority", 1);
                paymentMethod.put("id", "string");
                paymentMethod.put("bankInfo", paydataInput.get("bankInfo"));

                creditCard.put("nameOnCard", paydataInput.get("nameOnCard"));
                creditCard.put("firstName", paydataInput.get("firstName"));
                creditCard.put("lastName", paydataInput.get("lastName"));
                creditCard.put("number", paydataInput.get("cardnumber"));
                creditCard.put("cardExpiration", paydataInput.get("cardExpiration"));
                creditCard.put("bin", paydataInput.get("cardbin"));
                creditCard.put("lastFour", paydataInput.get("cardlastFour"));
                creditCard.put("token", paydataInput.get("cardtoken"));
                creditCard.put("postalCode", paydataInput.get("postalCode"));

                address.put("address", paydataInput.get("address"));
                address.put("city", paydataInput.get("city"));
                address.put("state", paydataInput.get("state"));
                address.put("zipCode", paydataInput.get("zipCode"));
                address.put("countryCode", paydataInput.get("countryCode"));

                paymentMethod.put("creditCard", creditCard);
                mainObject.put("paymentMethod", paymentMethod);
                mainObject.put("amount", amount);
                mainObject.put("address", address);

                json = payRequest.payWithWorldPayOnDemand(mainObject);
                jsonFormatter.printFormattedJson(json.toString(), "request", service);
                Response response = restUtils.getAuthenticatedPOSTReponseBodyWithBearer(json,
                                service,
                                envparam.get("baseEnv"), token);
                jsonFormatter.printFormattedJson(response.asString(), "response", service);
                JsonPath paymentDaPath = response.jsonPath();

                sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200",
                                "Call returned Error");

                sa = assertionUtil.assertEquals("Paid Amount",
                                paymentDaPath.get("result.amount.total").toString(),
                                paydataInput.get("amount.total").toString(),
                                "Paid Amount");

                sa = assertionUtil.assertEquals("The currencyCode",
                                paymentDaPath.get("result.amount.currencyCode"),
                                paydataInput.get("amount.currencyCode"),
                                "The currencyCode");

                // sa = assertionUtil.assertEquals("The merchantId",
                // paymentDaPath.get("result.merchantId"), paydataInput.get("merchantId"),
                // "The merchantId");

                // sa = assertionUtil.assertEquals("The Payment Code Description",
                // paymentDaPath.get("result.code.description"), "Approved",
                // "The Payment Code Description");

                sa = assertionUtil.assertEquals("Payment Status",
                                paymentDaPath.get("result.isApproved"), true,
                                "Payment Status");

                sa.assertAll();

        }

        static class JsonObjectUtility {
                public static Object getJSONObjectValue(Object value) {
                        return value == null ? "null" : value;
                }
        }

}
