package com.finixio.webservices.EasyAPI;

import org.json.JSONObject;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 01/17/2022
 * @project HFD-Automation
 */
public class PeekRequest {

    @Step("Sending a Peek for Applicant ID : {0} ")
    public Object easyAPIPeek(Object applicationID, String token) {

        JSONObject mainObj = new JSONObject();
        mainObj.put("token", token);
        return mainObj;
    }

}
