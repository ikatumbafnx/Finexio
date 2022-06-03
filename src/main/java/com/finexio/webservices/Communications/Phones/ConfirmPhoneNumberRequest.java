package com.finexio.webservices.Communications.Phones;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class ConfirmPhoneNumberRequest {

    @Step("Confirming the phone Number:  {0} using the verification code : {1}")
    public String ConfirmPhoneNumberString(String phonenumber, String verificationCode) {
        String json;
        json = String.format("%s/%s", phonenumber, verificationCode);
        return json;

    }

    @Step("Verifiying Email Address {0}")
    public Object ConfirmPhoneNumber(String phonenumber, String verificationCode, String Identifier) {

        JSONObject confirmPhoneObj = new JSONObject();
        confirmPhoneObj.put("PhoneNumber", phonenumber);
        confirmPhoneObj.put("Code", verificationCode);
        confirmPhoneObj.put("Identifier", Identifier);

        return confirmPhoneObj;

    }

}
