package com.finixio.test;

import com.finixio.BaseWeb;
import com.finixio.pages.home.HomePage;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class PatientLoginTest extends BaseWeb {

    @Description("Test to search wikipedia site for a term and validate the search result heading")
    @Test (description = "TC-2000 ::: Search Wikipedia For Search Term")
    public void patientLogin() throws Throwable {
        HomePage homePage = new HomePage();
        homePage.accessPatientPortal();

    }
}
