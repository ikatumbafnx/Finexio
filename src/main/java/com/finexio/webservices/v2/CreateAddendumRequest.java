package com.finexio.webservices.v2;

import io.qameta.allure.Step;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class CreateAddendumRequest {

    @Step("Creating an Addendum for Patient with Application ID : {0} ")
    public Object createAddendum(String applicationID, String financeDate, String firstPaymentDueDate) {

        JSONObject createAddendumObject = new JSONObject();

        createAddendumObject.put("ApplicationID", applicationID);
        createAddendumObject.put("FinanceDate", financeDate);
        createAddendumObject.put("FirstPaymentDueDate", firstPaymentDueDate);

        return createAddendumObject;
    }

    @Step("Creating an Addendum for Patient with Application ID")
    public Object createAddendumOnDemend(JSONObject createAddendumObject) {

        JSONObject json;
        json = createAddendumObject;
        return json;
    }
}
