package com.finexio.integrations.Azure;

import org.json.JSONArray;
import org.json.JSONObject;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/23/2021
 * @project QAAutomation
 */

public class CreateTestRunRequest {

    @Step("Create a Test Run for Testcase  {0}")
    public Object createTestRun(String planID, String pointID) {

        JSONObject createTestRunObject = new JSONObject();
        JSONObject planObject = new JSONObject();
        createTestRunObject.put("name", "Automated Tests");
        createTestRunObject.put("pointIds", pointID);
        planObject.put("id", planID);

        JSONArray pointIDS = new JSONArray();
        pointIDS.put(pointID);

        createTestRunObject.put("plan", planObject);
        createTestRunObject.put("pointIds", pointIDS);
        return createTestRunObject;
    }

}
