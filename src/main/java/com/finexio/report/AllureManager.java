package com.finexio.report;

import com.finexio.driver.DriverManager;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Attachment;
import org.openqa.selenium.TakesScreenshot;
import static com.finexio.config.ConfigurationManager.configuration;
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
