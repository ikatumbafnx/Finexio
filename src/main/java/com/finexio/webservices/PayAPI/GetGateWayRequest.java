package com.finexio.webservices.PayAPI;

import io.qameta.allure.Step;

public class GetGateWayRequest {

    @Step("Get All GateWays for Payments")
    public String getGateways() {

        String json;
        json = "/api/gateways";
        return json;
    }

}
