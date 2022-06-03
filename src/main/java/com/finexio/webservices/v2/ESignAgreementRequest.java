package com.finexio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ESignAgreementRequest {

    @Step("Patient with Application ID : {0} ESigning the agreement ")
    public Object eSignAgreement(String applicationID) {

        JSONObject eSignAgreementObject = new JSONObject();
        eSignAgreementObject.put("ApplicationID", applicationID);
        return eSignAgreementObject;
    }

    @Step("ESigning Agreement for  patient with Application ID")
    public Object eSignAgreementOnDemend(JSONObject eSignAgreementObject) {

        JSONObject json;
        json = eSignAgreementObject;
        return json;
    }

}
