package com.finexio;

import static com.finexio.config.ConfigurationManager.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.finexio.report.AllureManager;
import com.finexio.utils.ExcelUtils;
import com.finexio.utils.api.LogListerner;
import com.github.javafaker.Faker;

import helpers.DBHelpers;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
@Listeners(LogListerner.class)
public class BaseApi {
    Faker faker = new Faker();
    public Map<String, String> testcases = new HashMap<>();
    public Map<String, String> envparam = new HashMap<>();
    ArrayList<List<String>> testcaseslist = new ArrayList<List<String>>();
    ArrayList<List<String>> allList = new ArrayList<List<String>>();
    public String Env, ver;
    protected DBHelpers dbhelper = new DBHelpers();
    public String[][] all;
    public Map<String, String> generaltestcases = new HashMap<>();
    ExcelUtils excelUtils = new ExcelUtils();
    List<String> data = new ArrayList<>();

    @BeforeSuite
    public void beforeSuite() {

        AllureManager.setAllureWebservicesEnvironmentInformation();
        // testcases = new HashMap<>();
        System.out.println("Collecting all Testcases to Update");
        // testcases.put("PlanID", "10");
        // testcases.put("SuiteID", "11");
    }

    @BeforeClass
    public Map<String, String> getApplicationData() {

        excelUtils.DeleteTestCaseFile("TestCaseList");
        excelUtils.createExcelSheet("TestCaseList");

        Map<String, String> applicationData = new HashMap<>();
        applicationData.put("FirstName", faker.name().firstName());
        applicationData.put("LastName", faker.name().lastName());
        applicationData.put("Email", faker.internet().emailAddress());
        applicationData.put("Street", faker.address().streetAddress());
        applicationData.put("CityName", faker.address().cityName());
        applicationData.put("State", "CA");
        applicationData.put("ZipCode", faker.address().zipCode());

        return applicationData;
    }

    @BeforeMethod
    public void beforeMethod() {

        System.out.println("Collecting all Testcases to Update");
        Env = System.getProperty("environment");
        ver = System.getProperty("version");

        if (Env == null) {

            Env = "dev";

        }

        try {
            String baseEnv = "";
            String clientID = "";
            String clientSecret = "";
            String grantType = "";

            switch (Env) {

                case "stage":

                    envparam.put("baseEnv", configuration().apiendpoint());
                    envparam.put("version", ver);
                    // envparam.put("financeOfferFlag", dbhelper.getUseFinancePlanFlag("15357"));

                    // envparam.put("clientID", configuration().apiendpoint());
                    // envparam.put("clientSecret", configuration().apiendpoint());
                    // envparam.put("grantType", configuration().apiendpoint());
                    // baseEnv = configuration().apiendpoint();
                    break;

                
            }
        } catch (Exception e) {

            e.printStackTrace();
            Assert.assertFalse(true, "No Environment selected to use");
            return;

        }

        envparam.put("env", Env);

    }

    @AfterMethod
    public void afterMethod(ITestResult result) {

       

        try {
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

        List<String> data = new ArrayList<>();

        if (testcases.get("TestCaseID") != null && testcases.get("TestSuiteID") != null
                && testcases.get("outcome") != null) {
            data.add(testcases.get("TestSuiteID"));
            data.add(testcases.get("TestCaseID"));
            data.add(testcases.get("outcome"));

            testcaseslist.add(data);

        }

        testcases.clear();

    }

    @AfterClass
    public void AfterClass(ITestContext context) {

       

        int failedtest = context.getFailedTests().getAllResults().size();
        if (failedtest < 1) {
            generaltestcases.put("outcome", "PASSED");

            System.out.println("All the Testcases Passed");
            if (generaltestcases.get("TestCaseID") != null && generaltestcases.get("TestSuiteID") != null
                    && generaltestcases.get("outcome") != null) {

                data.add(generaltestcases.get("TestSuiteID"));
                data.add(generaltestcases.get("TestCaseID"));
                data.add(generaltestcases.get("outcome"));

                testcaseslist.add(data);
            }

        } else {
            generaltestcases.put("outcome", "FAILED");

            if (generaltestcases.get("TestCaseID") != null && generaltestcases.get("TestSuiteID") != null
                    && generaltestcases.get("outcome") != null) {

                data.add(generaltestcases.get("TestSuiteID"));
                data.add(generaltestcases.get("TestCaseID"));
                data.add(generaltestcases.get("outcome"));

                testcaseslist.add(data);
            }

            System.out.println(failedtest + "Testcases Failed");
        }

        System.out.println("All the Testcases to update are: " + testcaseslist);

        

      

    }

}
