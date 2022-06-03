package com.finexio.web.base;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.finexio.config.ConfigurationManager;
import com.finexio.driver.DriverManager;
import com.finexio.pages.AbstractPageObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BaseMethods extends AbstractPageObject {

    Logger logger = LogManager.getLogger(getClass().getName());
    private int timeout = ConfigurationManager.configuration().timeout();

    public enum ESelectBy {
        selectByIndex, selectByValue, selectByVisibleText
    }

    public enum EClickType {
        clickByActions, clickByJS, clickBySelenium
    }

    // Class Constants
    private final String _DropdownAttribute = "value";
    private final String _DateFormat = "MM/dd/yyyy";
    private String _classAttribute = "class";
    private String _areaCheckedAttribute = "aria-checked";
    private String _Checked = "-checked";
    private String _true = "true";
    private String _yes = "Yes";
    public final int _ImplicitWait = 1500;

    /**
     * Enter text in input field by display key.
     *
     * @param fieldLabelName
     * @throws Exception
     */
    @Step("Entering {2} into  {0} field ")
    public void enterText(String fieldLabelName, WebElement element, String input) throws Exception {
        // DriverManager.waitUntilElementIsPresent(timeout,element);
        element.click();
        element.clear();
        element.sendKeys(input);
        logger.info(fieldLabelName + " entered is : " + input);
    }

    /**
     * Enter text in input field by index.
     *
     * @param labelName
     * @throws Exception
     */
    public void enterTextByIndex(String labelName, String input, int index) throws Exception {

    }

    /**
     * Enter drop down for GW apps version9
     *
     * @param labelName
     * @param input
     * @throws Exception
     */
    public void enterDropdown(String labelName, String input) throws Exception {

    }

    /**
     * select drop down by element
     *
     * @param selectBy
     * @param element
     * @param input
     */
    protected void selectDropDownByElement(ESelectBy selectBy, WebElement element, String input) {
        switch (selectBy) {
            case selectByIndex:
                selectOptionUsingIndex(element, Integer.parseInt(input));
                break;
            case selectByValue:
                selectOptionUsingValue(element, input);
                break;
            case selectByVisibleText:
                selectOptionUsingVisibleText(element, input);
                break;
        }
    }

    /**
     * select option in a drop down implemented with <select> tags ,using value
     *
     * @param element
     * @param option
     */

    public void selectOptionUsingValue(WebElement element, String option) {
        Select dropDown = new Select(element);
        dropDown.selectByValue(option);
    }

    /**
     * select option in a drop down implemented with <select> tags ,using visible
     * text
     *
     * @param element
     * @param visibleText
     */

    public void selectOptionUsingVisibleText(WebElement element, String visibleText) {
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(visibleText);
    }

    /**
     * select option in a drop down implemented with <select> tags ,using index
     *
     * @param element
     * @param index
     */

    public void selectOptionUsingIndex(WebElement element, int index) {
        Select dropDown = new Select(element);
        dropDown.selectByIndex(index);
    }

    /**
     * get selected option
     *
     * @param element
     * @return
     */

    public String getSelectedOption(WebElement element) {
        Select dropDown = new Select(element);
        return dropDown.getFirstSelectedOption().getText();
    }

    /**
     * Click button
     *
     * @param
     * @throws Exception
     */
    @Step("User Clicking on {1} ")
    public void clickButton(WebElement element, String LabelName) throws Exception {
        logger.info("Clicking on : " + LabelName);
        if (element.isDisplayed() && element.isEnabled()) {
            element.click();
            logger.info(LabelName + " Button or Link was Clicked");
        }

    }

    @Step("Scrollong Down on the Application")
    public void scrollDown() {
        // to perform scroll on an application using Selenium

        WebDriver driver = DriverManager.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

    }

    @Step("Setting the Focus to the Window thats Active")
    public void setFocusToWindow() {
        // to perform scroll on an application using Selenium

        WebDriver driver = DriverManager.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.focus();");

    }

    @Step("Returning to default window")
    public void returnToMainWindow() {
        WebDriver driver = DriverManager.getDriver();
        driver.switchTo().defaultContent();

    }

    public void clickOutside() {
        WebDriver driver = DriverManager.getDriver();
        Actions action = new Actions(driver);

        action.moveByOffset(0, 0).click().build().perform();
    }

    public void moveToParticularElement(WebElement element) {

        WebDriver driver = DriverManager.getDriver();
        Actions action = new Actions(driver);
        action.moveToElement(element).click();
    }

    /**
     * Select check box
     *
     * @param labelName
     * @throws Exception
     */
    public void selectCheckBox(String labelName, boolean select) throws Exception {

    }

    /**
     * Click search Icon
     *
     * @param labelName
     * @throws Exception
     */
    public void clickSearchIcon(String labelName) throws Exception {

    }

    /**
     * Get field value
     *
     * @param labelName
     * @return
     * @throws Exception
     */
    public String getFieldValue(String labelName) throws Exception {
        WebElement element;
        String value = "";

        return value;
    }

    /**
     * Get field value attribute
     *
     * @param labelName
     * @return
     * @throws Exception
     */
    public String getFieldAttributeValue(String labelName) throws Exception {
        WebElement element;
        String value = "";

        return value;
    }

    /**
     * Get drop down default selected value
     *
     * @param labelName
     * @return
     * @throws Exception
     */
    public String getDropDownDefaultValue(String labelName) throws Exception {
        WebElement element;
        String defaultValue = "";

        return defaultValue;
    }

    /**
     * Is page loaded
     *
     * @param pageTitle
     */
    public void isPageLoaded(String pageTitle) {

    }

    /**
     * Get text
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String getText(WebElement element) throws Exception {
        String text = element.getText();
        return text;
    }

    /**
     * Get list of text
     *
     * @param labelName
     * @return
     * @throws Exception
     */
    public ArrayList<String> getTextList(String labelName) throws Exception {

        ArrayList<String> values = new ArrayList<>();

        return values;
    }

    /**
     * Get Dropdown values
     *
     * @param labelName
     * @return
     * @throws Exception
     */
    public ArrayList<String> getDropDownList(String labelName) throws Exception {
        ArrayList<String> dropDownList = new ArrayList<>();

        return dropDownList;
    }

    /**
     * Select radio button.
     *
     * @param labelName
     * @throws Exception
     */
    public void selectRadioButton(String labelName, String input) throws Exception {

    }

    /**
     * Double Click button
     *
     * @param labelName
     * @throws Exception
     */
    public void doubleClick(String labelName) throws Exception {

    }

    /**
     * Get error message in page
     */
    public String getErrorMessage() {
        String message = "";

        return message;
    }

    /**
     * check element is disabled or not
     *
     * @param labelName
     * @return
     */
    public boolean isElementDisabled(String labelName) throws Exception {
        return true;
    }

    /**
     * check element is enabled or not
     *
     * @param * @return
     */
    public boolean isElementEnabled(WebElement element) throws Exception {

        try {

            return element.isEnabled();
        } catch (Exception e) {
            try {

                return element.isEnabled();
            } catch (Exception ex) {

                return false;
            }
        }
    }

    /**
     * entering text in input field
     *
     * @param element
     * @param input
     * @throws Exception
     */
    private void inputText(WebElement element, String input) throws Exception {

    }

    /**
     * Input drop down
     *
     * @param element
     * @param input
     * @throws Exception
     */
    protected void inputDropdown(WebElement element, String input) throws Exception {
        if (!input.equals("")) {
            String uiValue;
            try {
                uiValue = element.getAttribute(_DropdownAttribute);
            } catch (Exception e) {
                uiValue = "";
            }
            boolean matched = false;
            try {
                matched = uiValue.equalsIgnoreCase(input);
            } catch (Exception e) {
                matched = false;
            }

        }
    }

    /**
     * Clear text in input field.
     *
     * @param labelName
     * @throws Exception
     */
    public void clearTextField(WebElement element, String labelName) throws Exception {
        if (element.isDisplayed()) {
            element.clear();

        }

    }

    /**
     * waits for element to be visible
     *
     * @param labelName
     */
    @Step("Waiting until {0} is present on the page")
    public void waitForElementToBePresent(String labelName, WebElement element) {

        DriverManager.waitUntilElementIsPresent(25, element);

    }

    public void waitForElementToBeDisplayedAndEnabledThenClick(WebElement element) {
        logger.info("Waiting for the element to click on to load");
        DriverManager.waitUntilElementIsPresent(timeout, element);
        element.click();
        logger.info("Element Loaded and was succesfully clicked on");

    }

    /**
     * Implicit wait
     * 
     * @param timeout
     */
    public void implicitWait(int timeout) {
        DriverManager.getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.MILLISECONDS);
    }

}
