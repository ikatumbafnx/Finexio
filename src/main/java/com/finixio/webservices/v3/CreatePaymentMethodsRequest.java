package com.finixio.webservices.v3;

import io.qameta.allure.Step;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
public class CreatePaymentMethodsRequest {

    @Step("Adding Payment Methods to Applicant {0}")
    public Object AddPaymentMethods(String PaymentMethodType,
                                    String CardNumber,
                                    String CardHoldersName,
                                    Integer expirationMonth,
                                    Integer expirationYear,
                                    String postalCode,
                                    Boolean isPrimary){

        JSONObject paymentMethodsObj = new JSONObject();
        paymentMethodsObj.put("paymentMethodType",PaymentMethodType);
        paymentMethodsObj.put("cardNumber",CardNumber);
        paymentMethodsObj.put("cardholderName",CardHoldersName);
        paymentMethodsObj.put("expirationMonth",expirationMonth);
        paymentMethodsObj.put("expirationYear",expirationYear);
        paymentMethodsObj.put("postalCode",postalCode);
        paymentMethodsObj.put("isPrimary",isPrimary);

        return paymentMethodsObj;

    }

}
