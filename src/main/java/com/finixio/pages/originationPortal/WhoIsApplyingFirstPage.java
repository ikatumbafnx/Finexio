package com.finixio.pages.originationPortal;

import io.qameta.allure.Step;

import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Ivan Katumba on 10/14/2021
 * @project HFD-Automation
 */
public class WhoIsApplyingFirstPage extends BaseMethods {

    @FindBy(id = "formFirstName")
    private WebElement firstName_Input;

    @FindBy(id = "formLastName")
    private WebElement lastName_Input;

    @FindBy(id = "formNIN")
    private WebElement SSN_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/div[1]/input[1]")
    private WebElement dobMonth_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/div[2]/input[1]")
    private WebElement dobDay_Input;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/div[3]/input[1]")
    private WebElement dobYear_Input;

    @FindBy(xpath = "//span[normalize-space()='Yes']")
    private WebElement isAlsopatientYes;

//    @FindBy(className = "yes-no-checkbox yes")
//    private WebElement isAlsopatientNo;
//
//    @FindBy(xpath = "//button[normalize-space()='Add Co-Applicant']")
//    private WebElement addCoApplicant_Btn;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/button[1]")
    private WebElement continue_Btn;

//    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/h4[1]")
//    private WebElement whoisapplyingpagetitle_Text;

    @Step("Adding Who is applying for financing")
    public void addDetailofwhoisApplyingForFinancing(String month,String day, String year) throws Exception {

        enterText("DOB Month",dobMonth_Input,month);
        enterText("DOB Day",dobDay_Input,day);
        enterText("DOB Year",dobYear_Input,year);
        clickButton(continue_Btn,"Continue");

    }

//    @Step("Verifying that Patient is on Who is applying for financing ")
//    public String confirmOnWhoisApplyingPage() throws Exception {
//
//        String pageTitle =  getText(whoisapplyingpagetitle_Text);
//        return pageTitle;
//
//    }


}
