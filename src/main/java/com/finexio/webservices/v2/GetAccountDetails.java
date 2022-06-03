package com.finexio.webservices.v2;

import io.qameta.allure.Step;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class GetAccountDetails {

    @Step("Getting Account Information for Applicant with ID  {0}")
    public String getAccountDetails(String accountID){

        String json;
        json = "/"+accountID;
        return json;
    }
}
