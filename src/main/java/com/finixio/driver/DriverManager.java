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

package com.finixio.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

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
