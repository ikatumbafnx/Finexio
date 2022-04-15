package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class EmailAgreementRequest {

    @Step("Emailing Agreement to Patient with Application ID : {0} ")
    public Object emailAgreement(String applicationID) {

        JSONObject emailAgreementObject = new JSONObject();
        emailAgreementObject.put("ApplicationID", applicationID);
        return emailAgreementObject;
    }

    @Step("Emailing Agreement to Patient with Application ID :")
    public Object emailAgreementOnDemand(JSONObject emailObject) {

        JSONObject json;
        json = emailObject;
        return json;

    }

}
