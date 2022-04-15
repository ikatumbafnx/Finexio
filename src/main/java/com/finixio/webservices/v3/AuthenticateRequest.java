package com.finixio.webservices.v3;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class AuthenticateRequest {

    @Step("Retrieving an access token for {1}")
    public String GetToken(String grant_type,
                           String client_id ,
                           String client_secret){
        String json;
        json = String.format("grant_type=%s&client_id=%s&client_secret=%s", grant_type,client_id,client_secret);
        return json;

    }
}
