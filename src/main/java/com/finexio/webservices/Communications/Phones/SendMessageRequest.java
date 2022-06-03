package com.finexio.webservices.Communications.Phones;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class SendMessageRequest {

    // @Step("Sending a message to {0}")
    // public String SendMessageSMS(String phonenumber, String message, String
    // compaign, String companyID) {
    // String json;

    // if (compaign == null && companyID == null) {

    // json = String.format("?To=%s&Message=%s", phonenumber, message);

    // } else {

    // json = String.format("?To=%s&Message=%s&Campaign=%sCompanyId=%s",
    // phonenumber, message, compaign,
    // companyID);
    // }
    // return json;

    // }

    @Step("Confirming Email")
    public Object SendMessageSMS(String phonenumber, String message, String compaign, String companyID) {

        JSONObject sendSMSObj = new JSONObject();
        sendSMSObj.put("To", phonenumber);
        sendSMSObj.put("Message", message);
        sendSMSObj.put("Campaign", compaign);
        sendSMSObj.put("CompanyId", companyID);

        return sendSMSObj;

    }

    @Step("Confirming Email")
    public Object ConfirmMail(JSONObject sendSMSObj) {

        JSONObject json;
        json = sendSMSObj;
        return json;

    }

}
