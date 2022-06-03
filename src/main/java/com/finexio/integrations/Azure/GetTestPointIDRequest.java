package com.finexio.integrations.Azure;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/23/2021
 * @project QAAutomation
 */

public class GetTestPointIDRequest {

    @Step("Create a Test Point ID for Testcase  {0}")
    public String GetTestPointID(String testplan, String testsuite, String testcase) {
        String json;
        json = String.format("%s/suites/%s/points?testCaseId=%s&api-version=5.0", testplan, testsuite, testcase);
        return json;

    }

}
