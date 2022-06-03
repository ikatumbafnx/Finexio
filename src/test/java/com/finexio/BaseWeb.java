/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:56:17
 * @modify date 2022-06-03 15:56:17
 * @desc [description]
 */


package com.finexio;

import static com.finexio.config.ConfigurationManager.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.finexio.driver.DriverManager;
import com.finexio.driver.TargetFactory;
import com.finexio.report.AllureManager;
import com.finexio.web.base.BaseMethods;
import com.finexio.web.base.Waiter;
import com.github.javafaker.Faker;

import helpers.DBHelpers;
import io.qameta.allure.Step;

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

        waiter.get(configuration().webpageurl(), DriverManager.getDriver());

        logger.info("Application is opening " + configuration().webpageurl());
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

        

    }

}
