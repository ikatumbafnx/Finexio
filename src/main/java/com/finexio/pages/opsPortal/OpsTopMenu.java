package com.finexio.pages.opsPortal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.finexio.driver.DriverManager;
import com.finexio.web.base.BaseMethods;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 11/9/2021
 * @project QAAutomation
 */
public class OpsTopMenu extends BaseMethods {

    // Systems Menu elements

    @FindBy(xpath = "//span[normalize-space()='System']")
    private WebElement systemMainLink_lnk;

    @FindBy(xpath = "//span[normalize-space()='Manage API Tokens']")
    private WebElement manageTokens_lnk;

    @FindBy(xpath = "//span[normalize-space()='Email Settings']")
    private WebElement emailSettings_lnk;

    @Step("Accessing the {1} from the {0} Main Menu on the OPS partal")
    public void goToSpecificMenu(String mainMenu, String menuItem, Object subMenu) throws Exception {

        if (subMenu == null) {
            WebElement mainMenuElement = DriverManager.getDriver()
                    .findElement(By.xpath("//span[normalize-space()='" + mainMenu + "']"));
            WebElement subMenuElement = DriverManager.getDriver()
                    .findElement(By.xpath("//span[normalize-space()='" + menuItem + "']"));

            waitForElementToBePresent(mainMenu, mainMenuElement);
            clickButton(mainMenuElement, mainMenu);
            waitForElementToBePresent(menuItem, subMenuElement);
            clickButton(subMenuElement, menuItem);

        } else {

            WebElement mainMenuElement = DriverManager.getDriver()
                    .findElement(By.xpath("//span[normalize-space()='" + mainMenu + "']"));
            WebElement subMenuElement = DriverManager.getDriver()
                    .findElement(By.xpath("//span[normalize-space()='" + menuItem + "']"));

            WebElement lastMenuElement = DriverManager.getDriver()
                    .findElement(By.xpath("//span[normalize-space()='" + subMenu.toString() + "']"));

            waitForElementToBePresent(mainMenu, mainMenuElement);
            clickButton(mainMenuElement, mainMenu);
            waitForElementToBePresent(menuItem, subMenuElement);
            clickButton(subMenuElement, menuItem);

            waitForElementToBePresent(subMenu.toString(), lastMenuElement);
            clickButton(lastMenuElement, subMenu.toString());

        }

    }

}
