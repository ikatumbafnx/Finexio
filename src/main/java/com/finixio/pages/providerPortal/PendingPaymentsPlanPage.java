package com.finixio.pages.providerPortal;

import io.qameta.allure.Step;

import com.finixio.driver.DriverManager;
import com.finixio.web.base.BaseMethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class PendingPaymentsPlanPage extends BaseMethods {

  Logger logger = LogManager.getLogger(getClass().getName());

  @FindBy(id = "ctl00_ContentPlaceHolder1_gridConfirmations_DXFREditorcol1_I")
  private WebElement ApplicationID_Input;

  @FindBy(xpath = "(//input[contains(@value,'Activate')])[10]")
  private WebElement Activate_Btn;

  @FindBy(id = "ctl00_ContentPlaceHolder1_popupPatientId_tbPatientID_I")
  private WebElement enterPatientID_Input;

  @FindBy(id = "ctl00_ContentPlaceHolder1_popupPatientId_btnActivate")
  private WebElement activateAccountOnOneMoreStepPopup_Btn;

  @FindBy(xpath = "//td[normalize-space()='4930503']")
  private WebElement idnumber;

  @Step("Searching or find the pending Payment Plan ID : {0}")
  public void findPendingApplicationsReadyForActivation(String applicationID) throws Exception {

    // enterText("ID",ApplicationID_Input,applicationID);
    DriverManager.waitForPageToLoad(30);
    implicitWait(20);
    // logger.info("The Application ID to Activate is : "+ getText(idnumber) );
    // moveToParticularElement(Activate_Btn);
    // clickButton(idnumber,"ID Number");
    DriverManager.waitUntilElementIsPresent(10, Activate_Btn);
    // clickButton(Activate_Btn,"Activate");
    // moveToParticularElement(Activate_Btn);
    // clickOutside();
    // Actions act = new Actions(DriverManager.getDriver());
    // act.moveToElement(Activate_Btn).click().build().perform();
    // clickButton(Activate_Btn,"Activate");
    // DriverManager.waitForPageToLoad(20);
    DriverManager.waitUntilElementIsPresent(25, enterPatientID_Input);
    // DriverManager.waitUntilElementIsPresent(25,Activate_Btn);

    // enterText("",enterPatientID_Input,"1254");
    // clickButton(activateAccountOnOneMoreStepPopup_Btn,"Activate");

  }

}
