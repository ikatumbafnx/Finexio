package com.finexio.webservices.Communications.Phones;

import io.qameta.allure.Step;

public class VerifyPhoneNumberRequest {

    @Step("Submitting phone Number:  {0} for Verification.")
    public String VerifyPhoneNumber(String phonenumber) {
        String json;
        json = String.format("%s", phonenumber);
        return json;

    }

}
