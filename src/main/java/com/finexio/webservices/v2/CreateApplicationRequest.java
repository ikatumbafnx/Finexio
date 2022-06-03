package com.finexio.webservices.v2;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class CreateApplicationRequest {

    public Object CreateApplicationOnDemand(JSONObject createApplicationMainObject) {

        JSONObject json;
        json = createApplicationMainObject;
        return json;
    }

    @Step("Creating an Application for {1}, {0} ")
    public Object CreateApplication(String FirstName,
            String LastName,
            String Email,
            String Phone,
            String DOB,
            Integer NIN,
            String Employment,
            String BillingAddress,
            String Street,
            String City,
            String Statoid,
            String Zip,
            String CountryCode,
            Double FinanceAmount,
            Double DownPaymentAmount,
            Integer TermMonths,
            String PrimaryPaymentMethodType,
            String NameOnCard,
            String CardNumber,
            Integer ExpirationMonth,
            Integer ExpirationYear,
            String CoApplicant,
            String PersonReceivingServices,
            Boolean IsTest,
            Integer ServiceId) {
        JSONObject createApplicationObj = new JSONObject();
        createApplicationObj.put("CoApplicant", CoApplicant);
        createApplicationObj.put("PersonReceivingServices", PersonReceivingServices);
        createApplicationObj.put("IsTest", IsTest);
        createApplicationObj.put("ServiceId", ServiceId);

        JSONObject applicant = new JSONObject();
        applicant.put("FirstName", FirstName);
        applicant.put("LastName", LastName);
        applicant.put("Email", Email);
        applicant.put("Phone", Phone);
        applicant.put("DOB", DOB);
        applicant.put("NIN", NIN);
        applicant.put("Employment", Employment);
        applicant.put("BillingAddress", BillingAddress);

        JSONObject address = new JSONObject();
        address.put("Street", Street);
        address.put("City", City);
        address.put("Statoid", Statoid);
        address.put("Zip", Zip);
        address.put("CountryCode", CountryCode);

        applicant.put("Address", address);

        JSONObject terms = new JSONObject();

        terms.put("FinanceAmount", FinanceAmount);
        terms.put("DownPaymentAmount", DownPaymentAmount);
        terms.put("TermMonths", TermMonths);
        terms.put("Statoid", Statoid);

        JSONObject payment = new JSONObject();
        JSONObject primaryPaymentMethod = new JSONObject();
        primaryPaymentMethod.put("Type", PrimaryPaymentMethodType);

        JSONObject card = new JSONObject();
        card.put("NameOnCard", NameOnCard);
        card.put("CardNumber", CardNumber);
        card.put("ExpirationMonth", ExpirationMonth);
        card.put("ExpirationYear", ExpirationYear);

        primaryPaymentMethod.put("Card", card);
        payment.put("PrimaryPaymentMethod", primaryPaymentMethod);
        createApplicationObj.put("Applicant", applicant);
        createApplicationObj.put("Terms", terms);
        createApplicationObj.put("Payment", payment);

        return createApplicationObj;

    }

}
