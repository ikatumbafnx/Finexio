package com.finexio.webservices.FinanceOffers;

import java.util.Map;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class GetOffersRequest {

    @Step("Retrieving all Offers for Application ID : {0} ")
    public Object getOffers(String applicationID, Map<String, Object> offerInput) {

        JSONObject offers = new JSONObject();
        offers.put("ServiceCost", offerInput.get("ServiceCost"));
        offers.put("DownPaymentAmount", offerInput.get("DownPaymentAmount"));
        offers.put("TermMonths", offerInput.get("TermMonths"));
        offers.put("InterestRate", offerInput.get("InterestRate"));
        offers.put("FinanceDate", offerInput.get("FinanceDate"));
        offers.put("FirstPaymentDueDate", offerInput.get("FirstPaymentDueDate"));
        return offers;
    }

    // Validation
    @Step("Retrieving all Offers for Application ID : {0} ")
    public Object getOffersOnDemand(String applicationID, JSONObject getOffersMainObject) {

        JSONObject json;
        json = getOffersMainObject;
        return json;
    }

}
