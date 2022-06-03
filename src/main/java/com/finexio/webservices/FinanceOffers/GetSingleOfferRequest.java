package com.finexio.webservices.FinanceOffers;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class GetSingleOfferRequest {

    @Step("Underwritting the Application with Applicant with ID  {0}")
    public String getSingleOffer(String applicationID) {

        String json;
        json = "/" + applicationID + "/offers";
        return json;
    }

}
