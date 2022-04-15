package com.finixio.test.webservices.PayAPI;

import static com.finixio.config.ConfigurationManager.apiConfiguration;
import static com.finixio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finixio.BaseApi;
import com.finixio.test.dataproviders.PayAPICards;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.DateUtils;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;
import com.finixio.webservices.PayAPI.GetGateWayRequest;
import com.finixio.webservices.PayAPI.WorldPayRequest;
import com.finixio.webservices.v3.AuthenticateRequest;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PayAPIRegression extends BaseApi {

        RestUtils restUtils = new RestUtils();
        JsonFormatter jsonFormatter = new JsonFormatter();
        AuthenticateRequest v3GetToken = new AuthenticateRequest();
        DateUtils dateUtils = new DateUtils();
        GetGateWayRequest getGateWayRequest = new GetGateWayRequest();
        WorldPayRequest payRequest = new WorldPayRequest();
        Map<String, Object> paydataInput = new HashMap<>();

        public String token, merchantID;

        @Epic("PayAPI Application Regression")
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

        @Epic("PayAPI Application Regression")
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
                merchantID = accountData.getString("result.gatewayStatuses[1].merchantsSupported[0]");
                System.out.println("The Marchannt ID is: " + merchantID);

        }

        @Epic("PayAPI Application Regression")
        @Description("Verifying Error Messages for using invalid Credit Card to trigger decline responses")
        @Test(priority = 2, dataProvider = "cardData", dataProviderClass = PayAPICards.class, groups = {
                        "v3" }, description = "Verifying Error Messages for using invalid Credit Card to trigger decline responses")

        public void declineMessageTriggers(String cardNo, String Msg, String Code, String isAproved) throws Exception {

                AllureLifecycle lifecycle = Allure.getLifecycle();
                lifecycle.updateTestCase(testResult -> testResult.setName("Verifying validation Message " + Msg
                                + " Is returned for using  Invalid Credit Card Number: " + cardNo));

                String service, link;
                Object json;
                AssertionUtil assertionUtil = new AssertionUtil();
                SoftAssert sa;
                String bankinfo = null;
                testcases = new HashMap<>();
                service = apiConfiguration().payApimakePayment();

                paydataInput.put("ReferenceID", "3212312");
                paydataInput.put("merchantId", "010049641");
                paydataInput.put("nameOnAccount", "Joe Doe");
                paydataInput.put("amount.total", 5000);
                paydataInput.put("amount.currencyCode", 145);
                paydataInput.put("nameOnCard", "Joe Doe");
                paydataInput.put("cardnumber", cardNo);
                paydataInput.put("cardExpiration", "0349");
                paydataInput.put("cardbin", "ThisShouldBeApproved");
                paydataInput.put("cardlastFour", "0009");
                paydataInput.put("cardtoken", "RandomTakenGenerated");
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

                sa = assertionUtil.assertEquals("Failure Code",
                                paymentDaPath.get("result.code.code"), Code,
                                "Failure Code");

                sa = assertionUtil.assertEquals("The currencyCode",
                                paymentDaPath.get("result.amount.currencyCode"),
                                paydataInput.get("amount.currencyCode"),
                                "The currencyCode");

                // sa = assertionUtil.assertEquals("The merchantId",
                // paymentDaPath.get("result.merchantId"), paydataInput.get("merchantId"),
                // "The merchantId");

                sa = assertionUtil.assertEquals("The Payment Code Description",
                                paymentDaPath.get("result.code.description"), Msg,
                                "The Payment Code Description");

                sa = assertionUtil.assertEquals("Payment Status",
                                paymentDaPath.get("result.isApproved").toString(), isAproved.toString(),
                                "Payment Status");

                sa.assertAll();

        }

        // @Test(dataProvider = "cardData", dataProviderClass = PayAPICards.class)
        // public void data(String cardNo, String Msg, String Code) {
        // System.out.println("The Code are: " + cardNo + " :" + Msg + " :" + Code);

        // }

        @Test()
        public void bold() {
                System.out.println(String.format("<b>%s</b>", "test"));
        }

}
