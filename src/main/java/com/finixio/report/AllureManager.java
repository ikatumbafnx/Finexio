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

package com.finixio.report;

import com.finixio.driver.DriverManager;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Attachment;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import static com.finixio.config.ConfigurationManager.apiConfiguration;
import static com.finixio.config.ConfigurationManager.configuration;
import static org.openqa.selenium.OutputType.BYTES;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class AllureManager {

    private AllureManager() {
    }

    public static void setAllureEnvironmentInformation() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().put("Test URL", configuration().url())
                        .put("Target execution", configuration().target())
                        .put("Global timeout", String.valueOf(configuration().timeout()))
                        .put("Headless mode", String.valueOf(configuration().headless()))
                        .put("Faker locale", configuration().faker()).put("Local browser", configuration().browser())
                        .put("Grid URL", configuration().gridUrl()).put("Grid port", configuration().gridPort())
                        .build());
    }

    public static void setAllureWebservicesEnvironmentInformation() {
        String Env = System.getProperty("environment");
        String suite = System.getProperty("suite");
        String ver = System.getProperty("version");
        String url = "";
        String xnull = null;

        if (Env == null) {

            Env = "stage";
        }

        try {

            switch (Env) {

                case "stage":

                    switch (String.valueOf(ver)) {

                        case "v2-1":
                            url = apiConfiguration().v2OneStageBaseurl();
                            break;

                        case "null":
                            url = configuration().apiendpoint();
                            break;

                    }

                    // if (ver.equalsIgnoreCase("v2-1")) {

                    // url = apiConfiguration().v2OneStageBaseurl();

                    // } else {

                    // url = configuration().apiendpoint();

                    // }

                    break;

                case "easystage":

                    url = configuration().easyApiEndPoint();

                    break;
                case "easydev":

                    url = configuration().easyApiEndPointDev();

                    break;

                case "payapidev":

                    url = apiConfiguration().payApiDevBaseUrl();

                    break;

                case "payapistage":

                    url = apiConfiguration().payApiStageBaseUrl();

                    break;

                case "dev":
                    url = apiConfiguration().v3DevServiceurl();

                    break;

                case "sandbox":
                    url = apiConfiguration().v3Serviceurl();

                    break;

                case "prod":
                    url = configuration().apiendpoint();
                    break;

                default:
                    url = configuration().apiendpoint();

            }
        } catch (Exception e) {

            e.printStackTrace();
            Assert.assertFalse(true, "No Environment selected to use");
            return;

        }
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Test URL", url)
                        .put("Target execution", configuration().target())
                        // .put("Suite Executed", suite)
                        .put("Environment", Env)
                        .put("Global timeout", String.valueOf(configuration().timeout())).build());

    }

    @Attachment(value = "Failed test screenshot", type = "image/png")
    public static byte[] takeScreenshotToAttachOnAllureReport() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(BYTES);
    }

    @Attachment(value = "Browser information", type = "text/plain")
    public static String addBrowserInformationOnAllureReport() {
        return DriverManager.getInfo();
    }

}
