package com.finixio.pages.originationPortal;

import io.qameta.allure.Step;

import static com.finixio.web.base.BaseMethods.ESelectBy.selectByVisibleText;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/14/2021
 * @project HFD-Automation
 */
public class AddressPage extends BaseMethods {

    @FindBy(id = "formCoApplicantAddress")
    private WebElement address_Input;

    @FindBy(id = "formCoApplicantZip")
    private WebElement zip_Input;

    @FindBy(id = "formCoApplicantCity")
    private WebElement cityN_Input;

    @FindBy(id = "formCoApplicantState")
    private WebElement state_dropDown;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/label[1]")
    private WebElement consentCheckBox;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/button[2]")
    private WebElement applyForFinancing;

//    @FindBy(className = "mb-4 step-header")
//    private WebElement pageTittle_Txt;

    @Step("Adding Contact Information for the Patient")
    public void addHowWeCanContactYou(
            String address,
            String zipcode,
            String City,
            String state ) throws Exception {

        enterText("Address",address_Input,address);
        enterText("Zip",zip_Input,zipcode);
        enterText("City",cityN_Input,City);
        selectDropDownByElement(selectByVisibleText,state_dropDown,state);
        clickButton(consentCheckBox,"Consent to Electronic Disclosure");
        clickButton(applyForFinancing,"Apply for Financing");

    }

//    @Step("Verifying that Patient is on How can we contact you Page ")
//    public String confirmOnAddressPage() throws Exception {
//
//        String pageTitle =  getText(pageTittle_Txt);
//        return pageTitle;
//
//    }
}
