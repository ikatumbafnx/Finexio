package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class GetTokenRequest {

    @Step("Retrieving an access token for {1}")
    public String GetToken(String grant_type,
            String username,
            String password, String ver) {
        String json;
        if (ver.equalsIgnoreCase("v2-1")) {
            json = String.format("grant_type=%s&username=%s&password=%s&is_waterfall=%s", grant_type, username,
                    password, true);
        } else {

            json = String.format("grant_type=%s&username=%s&password=%s", grant_type, username, password);
        }

        return json;

    }

}
