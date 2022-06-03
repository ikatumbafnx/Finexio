package com.finexio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class ESignAgreementRequest {

    @Step("Applicant is ESigning Agreement ready for  Applicant with ID  {0} to read and sign")
    public String eSignAgreement(String applicationID){

        String json;
        json = "/"+applicationID+"/esignature";
        return json;
    }


}
