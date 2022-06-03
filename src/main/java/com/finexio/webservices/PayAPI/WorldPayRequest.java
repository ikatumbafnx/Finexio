package com.finexio.webservices.PayAPI;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.qameta.allure.Step;

public class WorldPayRequest {

    @Step("Pay Api World Pay  ")
    public Object payWithWorldPay(Map<String, Object> dataInput) {

        JSONObject mainObject = new JSONObject();
        JSONObject amount = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();
        JSONObject address = new JSONObject();

        mainObject.put("referenceId", dataInput.get("ReferenceID"));
        mainObject.put("merchantId", dataInput.get("merchantId"));
        mainObject.put("nameOnAccount", dataInput.get("nameOnAccount"));

        amount.put("total", dataInput.get("amount.total"));
        amount.put("currencyCode", dataInput.get("amount.currencyCode"));

        paymentMethod.put("type", 0);
        paymentMethod.put("priority", 1);
        paymentMethod.put("id", "string");
        paymentMethod.put("bankInfo", dataInput.get("bankInfo"));

        creditCard.put("nameOnCard", dataInput.get("nameOnCard"));
        creditCard.put("number", dataInput.get("cardnumber"));
        creditCard.put("cardExpiration", dataInput.get("cardExpiration"));
        creditCard.put("bin", dataInput.get("cardbin"));
        creditCard.put("lastFour", dataInput.get("cardlastFour"));
        creditCard.put("token", dataInput.get("cardtoken"));
        creditCard.put("postalCode", dataInput.get("postalCode"));

        address.put("address", dataInput.get("address"));
        address.put("city", dataInput.get("city"));
        address.put("state", dataInput.get("state"));
        address.put("zipCode", dataInput.get("zipCode"));
        address.put("countryCode", dataInput.get("countryCode"));

        paymentMethod.put("creditCard", creditCard);
        mainObject.put("paymentMethod", paymentMethod);
        mainObject.put("amount", amount);
        mainObject.put("address", address);

        return mainObject;
    }

    @Step("Pay Api with WorldPay")
    public Object payWithWorldPayOnDemand(JSONObject startObject) {

        JSONObject json;
        json = startObject;
        return json;
    }

}
