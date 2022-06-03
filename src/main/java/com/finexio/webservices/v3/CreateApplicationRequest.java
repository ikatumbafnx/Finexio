package com.finexio.webservices.v3;

import io.qameta.allure.Step;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class CreateApplicationRequest {

    public Object CreateApplicationOnDemand(JSONObject createApplicationMainObject) {

        JSONObject json;
        json = createApplicationMainObject;
        return json;
    }

    @Step("Creating an Application for {0}, {1} ")
    public Object CreateApplication(String FirstName,
            String LastName,
            String Email,
            String Phone,
            String DOB,
            String Street,
            String City,
            String region,
            String Zip,
            String CountryCode,
            String FinanceDate,
            String firstPaymentDueDate,
            Integer TermMonths,
            Integer serviceCost,
            String locale,
            String nationalIdNumber, String offerflag) {

        JSONObject createApplicationObj = new JSONObject();
        createApplicationObj.put("locale", locale);

        JSONObject applicant = new JSONObject();
        applicant.put("firstName", FirstName);
        applicant.put("lastName", LastName);
        applicant.put("email", Email);
        applicant.put("phone", Phone);
        applicant.put("dateOfBirth", DOB);
        applicant.put("nationalIdNumber", nationalIdNumber);

        JSONObject address = new JSONObject();
        address.put("address1", Street);
        address.put("city", City);
        address.put("region", region);
        address.put("postalCode", Zip);
        address.put("country", CountryCode);

        applicant.put("address", address);

        JSONObject personReceivingServices = new JSONObject();

        personReceivingServices.put("firstName", FirstName);
        personReceivingServices.put("lastName", LastName);

        JSONObject financeTerms = new JSONObject();
        financeTerms.put("financeDate", FinanceDate);
        financeTerms.put("firstPaymentDueDate", firstPaymentDueDate);
        financeTerms.put("termMonths", TermMonths);
        financeTerms.put("region", region);

        JSONArray services = new JSONArray();
        JSONObject servicesObject = new JSONObject();
        servicesObject.put("serviceCost", serviceCost);

        services.put(servicesObject);
        createApplicationObj.put("applicant", applicant);
        createApplicationObj.put("personReceivingServices", personReceivingServices);

        if (offerflag.equalsIgnoreCase("0")) {

            createApplicationObj.put("services", services);
            createApplicationObj.put("financeTerms", financeTerms);

        }

        return createApplicationObj;

    }

}
