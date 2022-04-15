package com.finixio.utils;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class AssertionUtil {

    // Class constants

    SoftAssert softAssert = new SoftAssert();
    Logger logger = LogManager.getLogger(getClass().getName());

    /**
     * Assert two strings
     *
     * @param actual
     * @param expected
     * @param message
     * @param
     * @return
     */
    @Step("Verifying that the value returned for {0} is {2}")
    public SoftAssert assertEquals(String fieldNametobeVerified, Object actual, Object expected, String message)
            throws Exception {

        try {
            Assert.assertEquals(actual, expected, message);
            logger.info("verified that the " + fieldNametobeVerified + " " + expected + " is returned");

        } catch (AssertionError | Exception e) {
            softAssert.assertEquals(actual, expected, message);

        }
        return softAssert;
    }

    /**
     * Assert two integers
     *
     * @param actual
     * @param expected
     * @param message
     * @param
     * @return
     */
    public SoftAssert assertIntegersEquals(int actual, int expected, String message) throws Exception {

        try {
            Assert.assertEquals(actual, expected, message);

        } catch (AssertionError | Exception e) {
            softAssert.assertEquals(actual, expected, message);

        }
        return softAssert;
    }

    /**
     * Assert not equals two strings
     *
     * @param actual
     * @param expected
     * @param message
     * @param
     * @return
     */
    public SoftAssert assertNotEquals(String actual, String expected, String message) throws Exception {

        try {
            Assert.assertNotEquals(actual, expected, message);

        } catch (AssertionError | Exception e) {
            softAssert.assertNotEquals(actual, expected, message);

        }
        return softAssert;
    }

    /**
     * Assert not equals two integers
     *
     * @param actual
     * @param expected
     * @param message
     * @param
     * @return
     */
    public SoftAssert assertNotEquals(int actual, int expected, String message) throws Exception {

        try {
            Assert.assertNotEquals(actual, expected, message);

        } catch (AssertionError | Exception e) {
            softAssert.assertNotEquals(actual, expected, message);

        }
        return softAssert;
    }

    /**
     * Assert condition
     *
     * @param condition
     * @param message
     * @param
     * @return
     */
    public SoftAssert assertTrue(boolean condition, String message) throws Exception {

        try {
            Assert.assertTrue(condition, message);

        } catch (AssertionError e) {
            softAssert.assertTrue(condition, message);

        }
        return softAssert;
    }

    /**
     * Assert fail
     *
     * @param message
     * @param
     * @return
     */
    public SoftAssert assertFail(String message) throws Exception {

        softAssert.fail(message);

        return softAssert;
    }

    public void assertAll(SoftAssert softAssert) {
        softAssert.assertAll();
    }

}
