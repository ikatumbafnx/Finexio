package com.finexio.pages.home;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends MenuLinks{



    @Step("Accessing the Providers Login Portal from the Home Page")
    public  void providerPortal(){
      accessPatientPortal();

  }

    @Step("Accessing the Patients Login Portal from the Home Page")
    public  void patientsPortal(){
        accessPatientPortal();

    }

}
