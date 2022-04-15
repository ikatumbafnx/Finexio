package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class CreateAgreementRequest {

    @Step("Creating an Agreement for  Application ID : {0} ")
    public Object createAgreement(String applicationID) {

        JSONObject agreementObject = new JSONObject();
        agreementObject.put("ApplicationID", applicationID);
        return agreementObject;
    }

    @Step("Creating an Agreement for  Application ID")
    public Object createAgreementOnDemend(JSONObject createAgreementbject) {

        JSONObject json;
        json = createAgreementbject;
        return json;
    }

}
