package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ActivateAccountRequest {

    @Step("Activating patient account with Application ID : {0} ")
    public Object activateAccount(String applicationID) {

        JSONObject activateAccountObject = new JSONObject();
        activateAccountObject.put("ApplicationID", applicationID);
        return activateAccountObject;
    }

    @Step("Activating patient account with Application ID")
    public Object activateAccountOnDemend(JSONObject activateObject) {

        JSONObject json;
        json = activateObject;
        return json;
    }

}
