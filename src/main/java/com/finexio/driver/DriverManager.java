/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:47:28
 * @modify date 2022-06-03 15:47:28
 * @desc [description]
 */


package com.finexio.driver;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public DriverManager() {
    }

    public static WebDriver getDriver() {

        return driver.get();
    }

    public static void setDriver(WebDriver driver) {

        DriverManager.driver.set(driver);

    }

    public static void quit() {
        DriverManager.driver.get().quit();
        driver.remove();
    }

    public static String getInfo() {
        Capabilities cap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
        String browserName = cap.getBrowserName();
        String platform = cap.getPlatformName().toString();
        String version = cap.getBrowserVersion();
        return String.format("browser: %s v: %s platform: %s", browserName, version, platform);
    }

    public static void startDriver(String env) {

        DriverManager.driver.get().get(env);

    }

    public static void waitUntilElementIsPresent(long timeInSeconds, WebElement element) {
        WebDriverWait wait = new WebDriverWait(DriverManager.driver.get(), Duration.ofSeconds(timeInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    /**
     * Wait for Page state to be ready in DOM
     */
    public static void waitForPageToLoad(long timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(DriverManager.driver.get(), Duration.ofSeconds(timeInSeconds));
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    Boolean pageReadyState = (Boolean) (((JavascriptExecutor) driver)
                            .executeScript("return document.readyState=='complete';"));
                    return pageReadyState;
                }
            });
        } catch (Exception e) {
            // System.err.println(e.getLocalizedMessage());
        }
    }

    public static void waitUntilElementIsPresentBy(int timeInSeconds, By cssSelector) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(cssSelector));

    }
}
