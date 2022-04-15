package com.finixio.integrations.Azure;

import org.json.JSONArray;
import org.json.JSONObject;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/23/2021
 * @project QAAutomation
 */
public class UpdateTestCaseResultsRequest {

    @Step("Updating Testcase ID  {0}")
    public Object updateTestCase(String resultID, String testOutCome) {

        JSONArray mainBody = new JSONArray();
        JSONObject updateTestCaseObject = new JSONObject();
        updateTestCaseObject.put("id", resultID);
        updateTestCaseObject.put("outcome", testOutCome);
        updateTestCaseObject.put("state", "completed");
        updateTestCaseObject.put("comment", "Test Automation Execution Successful");

        mainBody.put(updateTestCaseObject);
        return mainBody;
    }

}
