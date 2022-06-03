package com.finexio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class ActivateApplicationRequest {

    @Step("Activating Applicant with ID  {0} ")
    public String activateAccount(String applicationID){

        String json;
        json = "/"+applicationID+"/activation";
        return json;
    }
}
