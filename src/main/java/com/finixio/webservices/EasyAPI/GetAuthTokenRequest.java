package com.finixio.webservices.EasyAPI;

import org.json.JSONObject;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 01/17/2022
 * @project HFD-Automation
 */
public class GetAuthTokenRequest {

    @Step("Retrieving an Authentication for  : {0} ")
    public String getAuthenticationToken(String grantType, String clientID, String clientSecret) {

        String json;
        json = String.format("grant_type=%s&client_id=%s&client_secret=%s", grantType, clientID, clientSecret);
        return json;

    }
}
