package com.finexio.webservices.Communications.Emails;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class ConfirmEmailRequest {

    @Step("Confirming Email")
    public Object ConfirmEmail(String emailIdentification, String token, String Code) {

        JSONObject confirmEmailObj = new JSONObject();
        confirmEmailObj.put("Id", emailIdentification);
        confirmEmailObj.put("Token", token);
        confirmEmailObj.put("Subject", Code);

        return confirmEmailObj;

    }

    @Step("Confirming Email")
    public Object ConfirmMail(JSONObject confirmEmailObj) {

        JSONObject json;
        json = confirmEmailObj;
        return json;

    }

}
