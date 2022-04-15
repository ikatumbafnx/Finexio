package com.finixio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class EmailAgreementRequest {

    @Step("EMailing Signed Agreement to  Applicant with ID  {0} for review")
    public String eMailAgreement(String applicationID){

        String json;
        json = "/"+applicationID+"/agreement/email";
        return json;
    }
}
