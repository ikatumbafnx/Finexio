package com.finexio.integrations.Azure;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/23/2021
 * @project QAAutomation
 */
public class GetTestResultsIDRequest {

    @Step("Create a Test Result ID Testcase  {0}")
    public String GetTestResultID(String runID) {
        String json;
        json = String.format("%s/results?api-version=6.0-preview.6", runID);
        return json;

    }

}
