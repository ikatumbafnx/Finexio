/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:56:25
 * @modify date 2022-06-03 15:56:25
 * @desc [description]
 */


package com.finexio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.finexio.report.AllureManager;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        // empty
        logger.info("****** Test Starting: "+ result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // empty
        logger.info("*************Test ending"+ result.getName());
        AllureManager.takeScreenshotToAttachOnAllureReport();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        failTest(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.error(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // empty
    }

    @Override
    public void onStart(ITestContext context) {
        // empty
    }

    @Override
    public void onFinish(ITestContext context) {
        // empty
    }

    private void failTest(ITestResult iTestResult) {
        logger.info("***********Test Ending :"+ iTestResult.getName());
        logger.error("******Test Failing: "+iTestResult.getTestClass().getName() +" with throwable:"
                + iTestResult.getThrowable());

        AllureManager.takeScreenshotToAttachOnAllureReport();
    }
}
