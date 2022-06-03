package com.finexio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class GetAgreementRequest {

    @Step("Getting the Agreement ready for  Applicant with ID  {0} to read and sign")
    public String getAgreement(String applicationID){

        String json;
        json = "/"+applicationID+"/agreement";
        return json;
    }
}
