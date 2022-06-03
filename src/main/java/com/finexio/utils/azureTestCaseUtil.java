package com.finexio.utils;

import static com.finexio.config.ConfigurationManager.apiConfiguration;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.finexio.integrations.Azure.CreateTestRunRequest;
import com.finexio.integrations.Azure.GetTestPointIDRequest;
import com.finexio.integrations.Azure.GetTestResultsIDRequest;
import com.finexio.integrations.Azure.UpdateTestCaseResultsRequest;
import com.finexio.utils.api.JsonFormatter;
import com.finexio.utils.api.RestUtils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class azureTestCaseUtil {

    static RestUtils restUtils = new RestUtils();
    JsonFormatter jsonFormatter = new JsonFormatter();
    AssertionUtil assertionUtil = new AssertionUtil();
    public static String TestPointID, runID, resultID;

    public static String getTestPointID(String testplanID, String testsuiteID, String testcaseID) throws Exception {
        String service;
        String json, endpointurl;
        GetTestPointIDRequest getTestPointIDRequest = new GetTestPointIDRequest();
        service = apiConfiguration().azureGetTestPointID();
        json = getTestPointIDRequest.GetTestPointID(testplanID, testsuiteID, testcaseID);
        endpointurl = service + json;
        System.out.println("The url is: " + endpointurl);
        Response response = restUtils.getAuthenticatedGETReponseWithNoBody(endpointurl,
                apiConfiguration().getAzureApiUrl(), apiConfiguration().getAzureToken());
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);

        TestPointID = response.jsonPath().getString("value[0].id").toString();
        System.out.println("The ID is : " + TestPointID);
        return TestPointID;

    }

    public static String createTestRun(String planID, String TestPointID) throws Exception {
        String service;
        String endpointurl;
        Object json;
        CreateTestRunRequest createTestRunRequest = new CreateTestRunRequest();
        RestUtils restUtils = new RestUtils();
        service = apiConfiguration().azureCreateTestRun();
        json = createTestRunRequest.createTestRun(planID, TestPointID);
        endpointurl = apiConfiguration().getAzureApiUrl();
        System.out.println("The url is: " + endpointurl);
        // jsonFormatter.printFormattedJson(json.toString(), "response", service);
        Response response = restUtils.getAuthenticatedPOSTReponseBody(json, service, endpointurl,
                apiConfiguration().getAzureToken());
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        runID = response.jsonPath().getString("id");
        System.out.println("The Run ID Created is : " + runID);
        return runID;

    }

    public static String getTestResultID(String runID) throws Exception {
        String service;
        String json, endpointurl;
        SoftAssert sa;
        GetTestResultsIDRequest getTestResultsIDRequest = new GetTestResultsIDRequest();
        RestUtils restUtils = new RestUtils();
        service = apiConfiguration().azureCreateTestResults();
        json = getTestResultsIDRequest.GetTestResultID(runID);
        endpointurl = service + json;
        System.out.println("The url is: " + endpointurl);
        Response response = restUtils.getAuthenticatedGETReponseWithNoBody(endpointurl,
                apiConfiguration().getAzureApiUrl(), apiConfiguration().getAzureToken());
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        resultID = response.jsonPath().getString("value[0].id");
        System.out.println("The Results ID is : " + resultID);
        return resultID;

    }

    public static void updateTestCase(String runID, String resultID, String OutCome) throws Exception {
        String service;
        String endpointurl;
        Object json;
        SoftAssert sa;
        UpdateTestCaseResultsRequest updateTestCaseResultsRequest = new UpdateTestCaseResultsRequest();
        RestUtils restUtils = new RestUtils();
        service = apiConfiguration().azureUpdateTestCase() + runID + "/results?api-version=3.0-preview";
        json = updateTestCaseResultsRequest.updateTestCase(resultID, OutCome);
        endpointurl = apiConfiguration().getAzureApiUrl();
        System.out.println("The url is: " + endpointurl);
        // jsonFormatter.printFormattedJson(json.toString(), "response", service);
        Response response = restUtils.getAuthenticatedPATCHReponseBody(json, service, endpointurl,
                apiConfiguration().getAzureToken());
        // jsonFormatter.printFormattedJson(response.asString(), "response", service);
        String outcome = response.jsonPath().getString("value[0].outcome");
        System.out.println("The TestCase was Run with a Status of  : " + outcome);

    }

    public void updateTestCaseResults(String TestCaseVerdict, String testplanID, String testsuiteID,
            String testcaseID)
            throws Exception {

        String TestPointID, runID, resultID;
        TestPointID = getTestPointID(testplanID, testsuiteID, testcaseID);
        runID = createTestRun(testplanID, TestPointID);
        resultID = getTestResultID(runID);
        updateTestCase(runID, resultID, TestCaseVerdict);

    }

    @Step("Reading all the TestCase Data to update Azure TestCases")
    @DataProvider(name = "testCaseData")
    String[][] getTestCaseData() throws IOException {

        String Path = "src/test/resources/files/testcaseData/TestCaseList.xlsx";
        int rowNum = ExcelUtils.getRowCount(Path, "TestCase Data");
        int colCount = ExcelUtils.getCellCount(Path, "TestCase Data", 1);
        String testCaseData[][] = new String[rowNum][colCount];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colCount; j++) {

                testCaseData[i - 1][j] = ExcelUtils.getCellData(Path, "TestCase Data", i, j);
            }

        }
        return testCaseData;

    }

}
