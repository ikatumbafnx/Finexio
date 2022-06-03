package com.finexio.pages.opsPortal.Applications;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.driver.DriverManager;
import com.finexio.web.base.BaseMethods;

import io.qameta.allure.Step;

public class ApplicationSummaryPage extends BaseMethods {

    @FindBy(css = "#ctl00_MainContent_UCAppSummary1_xlabProcID")
    private WebElement ProcedureIDDisplayText;

    @Step("Verify that you are on the Application {0} summary screen ")
    public String verifyThatYouAreAtTheApplicantionsSummaryPage(String ApplnID) throws Exception {
        DriverManager.waitForPageToLoad(30);
        waitForElementToBePresent("Procedure ID", ProcedureIDDisplayText);
        String confirmation = getText(ProcedureIDDisplayText);
        return confirmation;

    }

}
