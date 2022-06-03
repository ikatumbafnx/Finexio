package com.finexio.test;

import com.finexio.BaseWeb;
import com.finexio.pages.home.HomePage;

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
