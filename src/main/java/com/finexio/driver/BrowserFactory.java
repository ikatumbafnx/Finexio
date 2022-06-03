/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:47:09
 * @modify date 2022-06-03 15:47:09
 * @desc [description]
 */


package com.finexio.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.finexio.exceptions.HeadlessNotSupportedException;

import static com.finexio.config.ConfigurationManager.configuration;
import static java.lang.Boolean.TRUE;

public enum BrowserFactory {

    CHROME {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

            return new ChromeDriver(getOptions());
        }

        @Override
        public ChromeOptions getOptions() {
            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-setuid-sandbox");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments(START_MAXIMIZED);
            chromeOptions.addArguments("--disable-infobars");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("javascript.enabled");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--proxy-server='direct://'");
            chromeOptions.addArguments("--proxy-bypass-list=*");
            chromeOptions.addArguments("--disable-gpu");            
            chromeOptions.addArguments("--ignore-certificate-errors");
            

            chromeOptions.setHeadless(configuration().headless());

            return chromeOptions;
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();

            return new FirefoxDriver(getOptions());
        }

        @Override
        public FirefoxOptions getOptions() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments(START_MAXIMIZED);
            firefoxOptions.setHeadless(configuration().headless());

            return firefoxOptions;
        }
    },
    EDGE {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.EDGE).setup();

            return new EdgeDriver(getOptions());
        }

        @Override
        public EdgeOptions getOptions() {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments(START_MAXIMIZED);
            edgeOptions.setHeadless(configuration().headless());

            return edgeOptions;
        }
    },
    SAFARI {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();

            return new SafariDriver(getOptions());
        }

        @Override
        public SafariOptions getOptions() {
            SafariOptions safariOptions = new SafariOptions();
            safariOptions.setAutomaticInspection(false);

            if (TRUE.equals(configuration().headless()))
                throw new HeadlessNotSupportedException(safariOptions.getBrowserName());

            return safariOptions;
        }
    },
    OPERA {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.OPERA).setup();

            return new OperaDriver(getOptions());
        }

        @Override
        public OperaOptions getOptions() {
            OperaOptions operaOptions = new OperaOptions();
            operaOptions.addArguments(START_MAXIMIZED);
            operaOptions.addArguments("--disable-infobars");
            operaOptions.addArguments("--disable-notifications");

            if (TRUE.equals(configuration().headless()))
                throw new HeadlessNotSupportedException(operaOptions.getBrowserName());

            return operaOptions;
        }
    },
    IE {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();

            return new InternetExplorerDriver(getOptions());
        }

        @Override
        public InternetExplorerOptions getOptions() {
            InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
            internetExplorerOptions.ignoreZoomSettings();
            internetExplorerOptions.takeFullPageScreenshot();
            internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();

            if (TRUE.equals(configuration().headless()))
                throw new HeadlessNotSupportedException(internetExplorerOptions.getBrowserName());

            return internetExplorerOptions;
        }
    };

    private static final String START_MAXIMIZED = "--start-maximized";

    public abstract WebDriver createDriver();

    public abstract AbstractDriverOptions<?> getOptions();
}
