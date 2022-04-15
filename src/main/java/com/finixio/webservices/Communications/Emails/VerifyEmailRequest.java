package com.finixio.webservices.Communications.Emails;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class VerifyEmailRequest {

    @Step("Verifiying Email Address {0}")
    public Object VerifyEmail(String RecipientEmail, String RecipientName, String CCEmail, String CCName,
            String BCCEmail, String BCCName, String EmailBody, String FromEmail, String EmailSubject, String Compaign,
            String CompanyID, String AttachementData, String AttachementName, String AttachementType, String Meta) {

        JSONObject verifyEmailObj = new JSONObject();
        verifyEmailObj.put("To", EmailBody);
        verifyEmailObj.put("Subject", FromEmail);
        verifyEmailObj.put("From", EmailSubject);
        verifyEmailObj.put("TemplateName", Compaign);
        return verifyEmailObj;

    }

    @Step("Verifying an email send to user")
    public Object verifyEmail(JSONObject verifyEmailJsonObject) {

        JSONObject json;
        json = verifyEmailJsonObject;
        return json;

    }

}
