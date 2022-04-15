package com.finixio.pages.opsPortal.Applications;

import com.finixio.driver.DriverManager;
import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
