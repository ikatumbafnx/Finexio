package com.finexio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class UnderwrittingRequest {

    @Step("Underwritting the Application with Applicant with ID  {0}")
    public String underwriteApplication(String applicationID){

        String json;
        json = "/"+applicationID+"/underwriting";
        return json;
    }

}
