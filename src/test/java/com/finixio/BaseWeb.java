/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.finixio;

import com.finixio.driver.DriverManager;
import com.finixio.driver.TargetFactory;
import com.finixio.pages.customerPortal.CustomerLinkVerificationPage;
import com.finixio.report.AllureManager;
import com.finixio.utils.azureTestCaseUtil;
import com.finixio.web.base.BaseMethods;
import com.finixio.web.base.Waiter;
import com.github.javafaker.Faker;

import helpers.DBHelpers;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static com.finixio.config.ConfigurationManager.apiConfiguration;
import static com.finixio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Listeners({ TestListener.class })
public abstract class BaseWeb extends BaseMethods {

    Logger logger = LogManager.getLogger(getClass().getName());
    Faker faker = new Faker();
    DBHelpers dbHelpers = new DBHelpers();
    public Map<String, String> testcases = new HashMap<>();
    private Waiter waiter = new Waiter();

    @BeforeClass
    public Map<String, String> getApplicationData() {

        Map<String, String> applicationData = new HashMap<>();
        applicationData.put("FirstName", faker.name().firstName());
        applicationData.put("LastName", faker.name().lastName());
        applicationData.put("Email", faker.internet().emailAddress());
        applicationData.put("Street", faker.address().streetAddress());
        applicationData.put("CityName", faker.address().cityName());
        applicationData.put("Country", faker.address().country());
        applicationData.put("State", faker.address().stateAbbr());
        applicationData.put("ZipCode", faker.address().zipCode());
        applicationData.put("Phone", faker.phoneNumber().subscriberNumber(11));
        applicationData.put("SSN", "222222222");
        applicationData.put("PatientID", faker.number().digits(4));
        applicationData.put("CardNo", "4111111111111111");
        applicationData.put("CVV", "123");
        applicationData.put("******", "password");
        applicationData.put("BusinessName", faker.company().name());
        applicationData.put("CompanyID", faker.idNumber().valid());

        return applicationData;
    }

    public enum CardTypes {
        CREDDIT,
        DEBIT,
        HSA;
    }

    @Step("Opening the providers portal")
    protected void startprovidersportal() {

        waiter.get(configuration().providerportal(), DriverManager.getDriver());

        logger.info("Application is opening " + configuration().providerportal());
    }

    @Step("Opening the OPS portal")
    protected void startOPSportal() {

        DriverManager.startDriver(configuration().opsportal());
        logger.info("Application is opening " + configuration().opsportal());
    }

    @Step("Opening the providers portal")
    protected void startMainWebsite() {

        DriverManager.startDriver(configuration().url());
        logger.info("Application is opening " + configuration().url());
    }

    @Step("Opening the providers portal")
    protected void startOriginationPortal() {

        DriverManager.startDriver(configuration().originationportal());
        logger.info("Application is opening " + configuration().originationportal());
    }

    @Step("Opening the customers portal")
    protected void startcustomerportal() {

        waiter.get(configuration().customerportal(), DriverManager.getDriver());
        // DriverManager.startDriver(configuration().customerportal());
        logger.info("Application is opening " + configuration().customerportal());
    }

    @Step("{0} Verifying their Online Account from Email sent to {1}")
    protected void verifyaccountviaemail(String firstName, String emailAdress) throws Exception {

        List<String> userid = dbHelpers.getValidationUserID(emailAdress);
        String verificationLink = configuration().customeraccountverification() + userid.get(0).toLowerCase();
        DriverManager.startDriver(verificationLink);
        logger.info("Customer has succesfully verified their account at : " + verificationLink);
    }

    @BeforeSuite
    public void beforeSuite() {

        AllureManager.setAllureEnvironmentInformation();

    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser) {
        WebDriver driver = new TargetFactory().createInstance(browser);
        DriverManager.setDriver(driver);
        System.out.println("Collecting all Testcases to Update");

    }

    @AfterMethod(alwaysRun = true)
    public void postCondition(ITestResult result) {
        // try {
        // AllureManager.takeScreenshotToAttachOnAllureReport();
        // DriverManager.quit();
        // } catch (Exception e) {
        // System.out.println("****************************** POST CONDITION ERROR: " +
        // e);

        // }

        azureTestCaseUtil azureTestCaseUtil = new azureTestCaseUtil();

        try {
            AllureManager.takeScreenshotToAttachOnAllureReport();
            DriverManager.quit();
            if (result.getStatus() == ITestResult.SUCCESS) {

                // Do something here
                System.out.println("passed **********");
                testcases.put("outcome", "PASSED");
            }

            else if (result.getStatus() == ITestResult.FAILURE) {
                // Do something here
                System.out.println("Failed ***********");
                testcases.put("outcome", "FAILED");

            }

            else if (result.getStatus() == ITestResult.SKIP) {

                System.out.println("Skiped***********");
                testcases.put("outcome", "FAILED");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (testcases.get("TestCaseID") != null) {
                azureTestCaseUtil.updateTestCaseResults(testcases.get("outcome"),
                        apiConfiguration().getAzureTestPlanID(), apiConfiguration().getAzureTestSuiteID(),
                        testcases.get("TestCaseID"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        testcases.clear();

    }

}
