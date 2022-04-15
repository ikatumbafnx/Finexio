package com.finixio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ESignAddendumRequest {

    @Step("ESigning Addendum for  patient with Application ID : {0} ")
    public Object eSignAddendum(String applicationID) {

        JSONObject esignaddendumObject = new JSONObject();
        esignaddendumObject.put("ApplicationID", applicationID);
        return esignaddendumObject;
    }

    @Step("ESigning Addendum for  patient with Application ID")
    public Object eSignAddendumOnDemend(JSONObject eSignAddendumObject) {

        JSONObject json;
        json = eSignAddendumObject;
        return json;
    }

}
