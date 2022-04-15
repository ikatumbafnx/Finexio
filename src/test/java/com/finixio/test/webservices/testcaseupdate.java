package com.finixio.test.webservices;

import static com.finixio.config.ConfigurationManager.apiConfiguration;

import java.util.HashMap;

import com.finixio.BaseApi;
import com.finixio.integrations.Azure.CreateTestRunRequest;
import com.finixio.integrations.Azure.GetTestPointIDRequest;
import com.finixio.integrations.Azure.GetTestResultsIDRequest;
import com.finixio.integrations.Azure.UpdateTestCaseResultsRequest;
import com.finixio.utils.AssertionUtil;
import com.finixio.utils.azureTestCaseUtil;
import com.finixio.utils.api.JsonFormatter;
import com.finixio.utils.api.RestUtils;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class testcaseupdate extends BaseApi {

    RestUtils restUtils = new RestUtils();
    JsonFormatter jsonFormatter = new JsonFormatter();
    AssertionUtil assertionUtil = new AssertionUtil();
    public String TestPointID, runID, resultID;

    @Test(priority = 0)
    public void getTestPointID() throws Exception {
        String service;
        String json, endpointurl;
        SoftAssert sa;
        testcases.put("TestCaseID", "23");
        GetTestPointIDRequest getTestPointIDRequest = new GetTestPointIDRequest();
        RestUtils restUtils = new RestUtils();

        service = apiConfiguration().azureGetTestPointID();

        json = getTestPointIDRequest.GetTestPointID("10", "11", "23");
        endpointurl = service + json;

        System.out.println("The url is: " + endpointurl);

        Response response = restUtils.getAuthenticatedGETReponseWithNoBody(endpointurl,
                apiConfiguration().getAzureApiUrl(), apiConfiguration().getAzureToken());
        jsonFormatter.printFormattedJson(response.asString(), "response", service);

        TestPointID = response.jsonPath().getString("value[0].id").toString();
        System.out.println("The ID is : " + TestPointID);
        sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200", "Call returned Error");

        sa.assertAll();

    }

    @Test(priority = 1)
    public void createTestRun() throws Exception {
        String service;
        String endpointurl;
        Object json;
        SoftAssert sa;
        testcases = new HashMap<>();
        CreateTestRunRequest createTestRunRequest = new CreateTestRunRequest();
        RestUtils restUtils = new RestUtils();

        service = apiConfiguration().azureCreateTestRun();

        json = createTestRunRequest.createTestRun("10", TestPointID);
        endpointurl = apiConfiguration().getAzureApiUrl();

        System.out.println("The url is: " + endpointurl);
        jsonFormatter.printFormattedJson(json.toString(), "response", service);
        Response response = restUtils.getAuthenticatedPOSTReponseBody(json, service, endpointurl,
                apiConfiguration().getAzureToken());
        jsonFormatter.printFormattedJson(response.asString(), "response", service);

        runID = response.jsonPath().getString("id");
        System.out.println("The Run ID Created is : " + runID);
        sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200", "Call returned Error");

        sa.assertAll();

    }

    @Test(priority = 2)
    public void getTestResultID() throws Exception {
        String service;
        String json, endpointurl;
        SoftAssert sa;
        testcases = new HashMap<>();
        GetTestResultsIDRequest getTestResultsIDRequest = new GetTestResultsIDRequest();
        RestUtils restUtils = new RestUtils();

        service = apiConfiguration().azureCreateTestResults();

        json = getTestResultsIDRequest.GetTestResultID(runID);
        endpointurl = service + json;

        System.out.println("The url is: " + endpointurl);

        Response response = restUtils.getAuthenticatedGETReponseWithNoBody(endpointurl,
                apiConfiguration().getAzureApiUrl(), apiConfiguration().getAzureToken());
        jsonFormatter.printFormattedJson(response.asString(), "response", service);

        resultID = response.jsonPath().getString("value[0].id");
        System.out.println("The Results ID is : " + resultID);
        sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200", "Call returned Error");

        sa.assertAll();

        // testcases.put("TestCaseID", "23");

    }

    @Test(priority = 3)
    public void updateTestCase() throws Exception {
        String service;
        String endpointurl;
        Object json;
        SoftAssert sa;
        testcases = new HashMap<>();
        UpdateTestCaseResultsRequest updateTestCaseResultsRequest = new UpdateTestCaseResultsRequest();
        RestUtils restUtils = new RestUtils();

        service = apiConfiguration().azureUpdateTestCase() + runID + "/results?api-version=3.0-preview";

        json = updateTestCaseResultsRequest.updateTestCase(resultID, "PASSED");
        endpointurl = apiConfiguration().getAzureApiUrl();

        System.out.println("The url is: " + endpointurl);
        jsonFormatter.printFormattedJson(json.toString(), "response", service);
        Response response = restUtils.getAuthenticatedPATCHReponseBody(json, service, endpointurl,
                apiConfiguration().getAzureToken());
        jsonFormatter.printFormattedJson(response.asString(), "response", service);

        String outcome = response.jsonPath().getString("value[0].outcome");
        System.out.println("The TestCase was Run with a Status of  : " + outcome);
        sa = assertionUtil.assertEquals("Status", String.valueOf(response.statusCode()), "200", "Call returned Error");

        sa.assertAll();

    }

    @Test()
    public void fixTestCase() throws Exception {

        azureTestCaseUtil azureTestCaseUtil = new azureTestCaseUtil();
        azureTestCaseUtil.updateTestCaseResults("FAILED", "10", "11", "23");

    }

}
