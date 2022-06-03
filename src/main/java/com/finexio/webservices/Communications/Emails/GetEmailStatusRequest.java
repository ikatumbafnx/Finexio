package com.finexio.webservices.Communications.Emails;

import io.qameta.allure.Step;

public class GetEmailStatusRequest {

    @Step("Getting the Email status {0} using the verification code : {1}")
    public String getEmailStatus(String emailIdentificationID) {
        String json;
        json = String.format("?Id=%s", emailIdentificationID);
        return json;

    }

}
