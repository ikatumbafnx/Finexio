package com.finexio.webservices.FinanceOffers;

import java.util.Map;

import org.json.JSONObject;

import io.qameta.allure.Step;

public class SetOfferRequest {

    @Step("Setting all Offers to Application ID : {0} ")
    public Object setOffers(String applicationID, Map<String, Object> setofferInput) {

        JSONObject offers = new JSONObject();

        offers.put("FinanceAmount", setofferInput.get("FinanceAmount"));
        offers.put("DownPaymentAmount", setofferInput.get("DownPaymentAmount"));
        offers.put("TermMonths", setofferInput.get("TermMonths"));
        offers.put("InterestRate", setofferInput.get("InterestRate"));
        offers.put("FinanceDate", setofferInput.get("FinanceDate"));
        offers.put("FirstPaymentDueDate", setofferInput.get("FirstPaymentDueDate"));
        return offers;
    }

}
