package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class UpdateApplicationRequest {

    @Step("Generating the Update Application Request")
    public Object UpdateApplicationOnDemand(JSONObject updateApplicationMainObject) {

        JSONObject json;
        json = updateApplicationMainObject;
        return json;
    }

}
