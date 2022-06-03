package com.finexio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class GetApplicationDetailsRequest {

    @Step("Getting Application Information for Applicant with ID  {0}")
    public String getAccountDetails(String applicationID){

        String json;
        json = "/"+applicationID;
        return json;
    }
}
