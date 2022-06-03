package com.finexio.pages.opsPortal.Applications;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.driver.DriverManager;
import com.finexio.web.base.BaseMethods;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/10/2021
 * @project QAAutomation
 */
public class ManageApplications extends BaseMethods {

    @FindBy(css = "#ctl00_MainContent_myApplications_ASPxGridView1_DXFREditorcol1_I")
    private WebElement procIDSearchInput;

    @FindBy(css = "tbody tr:nth-child(2) td:nth-child(1) a:nth-child(1)")
    private WebElement serverLink;

    @Step("Searching for Application {0} to manage")
    public void searchForTheAccountID(String ApplnID) throws Exception {

        waitForElementToBePresent("Providers ID Search Input", procIDSearchInput);
        enterText("Providers Application ID", procIDSearchInput, ApplnID);
        clickOutside();
        DriverManager.waitForPageToLoad(30);

    }

    @Step("View Summary of Application {0} to manage")
    public void viewApplicationSummary(String ApplnID) throws Exception {

        String app = ".dxeHyperlink_MetropolisBlue[href='AppLife.aspx?pid=" + ApplnID + "&summary=1']";
        String path = "//a[contains(text(),'" + ApplnID + "')]";
        By by = new By.ByCssSelector(app);
        By xp = new By.ByXPath(path);

        try {
            DriverManager.waitForPageToLoad(30);
            DriverManager.waitUntilElementIsPresentBy(25, by);
            // WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
            // Duration.ofSeconds(30));
            // wait.until(ExpectedConditions.presenceOfElementLocated(by));

            WebElement applicationIDSummaryLink = DriverManager.getDriver().findElement(by);
            // DriverManager.waitUntilElementIsPresent(25, applicationIDSummaryLink);
            waitForElementToBePresent("Application ID to Display in Search Results",
                    applicationIDSummaryLink);
            DriverManager.waitForPageToLoad(30);

            clickButton(applicationIDSummaryLink, "Viewing summary for Application ID" + ApplnID);
        } catch (NoSuchElementException | StaleElementReferenceException | ElementClickInterceptedException e) {
            // TODO: handle exception
            DriverManager.waitForPageToLoad(30);
            DriverManager.waitUntilElementIsPresentBy(25, by);
            WebElement applicationIDSummaryLink = DriverManager.getDriver().findElement(by);
            DriverManager.waitUntilElementIsPresent(25, applicationIDSummaryLink);
            waitForElementToBePresent("Application ID to Display in Search Results",
                    applicationIDSummaryLink);
            clickButton(applicationIDSummaryLink, "Viewing summary for Application ID" + ApplnID);
            DriverManager.waitForPageToLoad(30);
        }

    }

    @Step("Verify that you are on the Borrowers summary screen for Application {0} ")
    public String verifyThatYouAreAtTheManageListPage() throws Exception {
        DriverManager.waitForPageToLoad(30);
        waitForElementToBePresent("ServerID", serverLink);
        String confirmation = getText(serverLink);
        return confirmation;
    }

}
