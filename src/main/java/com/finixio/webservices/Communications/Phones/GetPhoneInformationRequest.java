package com.finixio.webservices.Communications.Phones;

import io.qameta.allure.Step;

public class GetPhoneInformationRequest {

    @Step("Retrieving lookup information for phone Number:  {0}")
    public String RetrievePhoneInformation(String phonenumber) {
        String json;
        json = String.format("%s", phonenumber);
        return json;

    }

}
