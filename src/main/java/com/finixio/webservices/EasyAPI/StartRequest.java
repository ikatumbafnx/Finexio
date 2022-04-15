package com.finixio.webservices.EasyAPI;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 01/17/2022
 * @project HFD-Automation
 */

public class StartRequest {

    @Step("Easy Api Start App  ")
    public Object startapp(Map<String, Object> dataInput) {

        JSONObject mainObject = new JSONObject();
        JSONObject application = new JSONObject();
        JSONObject optionsObject = new JSONObject();
        JSONObject optionsElements = new JSONObject();
        JSONObject treatmentObject = new JSONObject();
        JSONArray dispatchTypeArray = new JSONArray();

        JSONArray applicantsArray = new JSONArray();
        JSONObject applicantsArrayElements = new JSONObject(); // treatment.TotalCost
        JSONObject address = new JSONObject();

        JSONArray communicationsArray = new JSONArray();
        JSONObject communicationsArrayElements = new JSONObject();
        JSONObject communicationsArrayElements2 = new JSONObject();

        optionsElements.put("IsForPrequalification", dataInput.get("IsForPrequalification"));

        dispatchTypeArray.put("SMS");
        dispatchTypeArray.put("Email");
        optionsObject.put("IsTest", dataInput.get("IsForPrequalification"));
        optionsObject.put("LinkExpirationInDays", dataInput.get("options.LinkExpirationInDays"));
        optionsObject.put("ShortenUrl", dataInput.get("options.ShortenUrl"));

        optionsObject.put("DispatchTypes", dispatchTypeArray);
        mainObject.put("ReferenceId", dataInput.get("ReferenceID"));
        mainObject.put("ProviderId", dataInput.get("ProviderId"));

        applicantsArrayElements.put("Type", dataInput.get("applicant.type"));
        applicantsArrayElements.put("FirstName", dataInput.get("applicant.firstname"));
        applicantsArrayElements.put("LastName", dataInput.get("applicant.lastname"));
        applicantsArrayElements.put("DOB", dataInput.get("applicant.dob"));
        applicantsArrayElements.put("NationalId", dataInput.get("applicant.nationalID"));
        applicantsArrayElements.put("MonthlyIncome", dataInput.get("applicant.monthlyIncome"));

        address.put("StreetLine1", dataInput.get("address.StreetLine1"));
        address.put("StreetLine2", dataInput.get("address.StreetLine2"));
        address.put("City", dataInput.get("address.City"));
        address.put("StatoId", dataInput.get("address.StatoId"));
        address.put("PostalCode", dataInput.get("address.PostalCode"));
        address.put("CountryCode", dataInput.get("address.CountryCode"));

        communicationsArrayElements.put("Value", dataInput.get("communication.value"));
        communicationsArrayElements.put("Type", dataInput.get("communication.type"));
        communicationsArrayElements.put("Priority", dataInput.get("communication.priority"));

        communicationsArrayElements2.put("Value", dataInput.get("communication2.value"));
        communicationsArrayElements2.put("Type", dataInput.get("communication2.type"));
        communicationsArrayElements2.put("Priority", dataInput.get("communication2.priority"));

        communicationsArray.put(communicationsArrayElements);
        communicationsArray.put(communicationsArrayElements2);

        applicantsArrayElements.put("Address", address);
        applicantsArrayElements.put("Communications", communicationsArray);
        applicantsArray.put(applicantsArrayElements);
        // applicantsArray.put(communicationsArray);

        // Add the TreatMant Cost Object

        treatmentObject.put("TotalCost", dataInput.get("treatment.TotalCost"));

        application.put("Treatment", treatmentObject);
        application.put("Options", optionsElements);
        application.put("Applicants", applicantsArray);

        mainObject.put("Application", application);
        mainObject.put("Options", optionsObject);

        return mainObject;
    }

    @Step("Easy Api Start App  ")
    public Object startappOnDemend(JSONObject startObject) {

        JSONObject json;
        json = startObject;
        return json;
    }

}
