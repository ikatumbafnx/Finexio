package com.finixio.pages.providerPortal;

import java.util.List;

import com.finixio.driver.DriverManager;
import com.finixio.web.base.BaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ApplicationsPage extends BaseMethods{

    @FindBy(xpath = "//h4//small")
    private WebElement Info_Icn;

     /**
     * Filter actions
     */
    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_btnFilter")
    private WebElement Filter_Btn;

    @FindBy(xpath = "//h4[text()='Apply Filters']")
    private WebElement ApplyFilter_Filter_Title;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxCBReportType_I")
    private WebElement FilterCriteria_Filter_Input;

    @FindBy(xpath = "//input[@id='ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxBtnApplyQuickFilter']")
    private WebElement ApplyFilter_Filter_Submit;

    @FindBy(xpath = "//div[@id='AppFiltersModal']/div[1]/div/div[3]/button")
    private WebElement CloseFilter_Filter_Btn;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxDateEditBegin_I")
    private WebElement StartDate_Filter_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxDateEditEnd_I")
    private WebElement EndDate_Filter_Input;

    /**
     * New Application
     */
    @FindBy(linkText = "New Application")
    private WebElement NewApplication_Btn;

    @FindBy(xpath = "//div[6]/div[1]/div[1]/div[1]/div[2]/div[1]/button")
    private WebElement NewApplicationDropDown_Drp;
    
    /**
     * Actions
     */
    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_divCustomize")
    private WebElement Actions_Btn;

    @FindBy(xpath = "//div/a[@id='editColumns']")
    private WebElement EditColumns_Actions_Option;

    @FindBy(xpath = "//div/a[@id='ctl00_ContentPlaceHolder1_UCProviderClientReports1_exportBtn']")
    private WebElement Export_Actions_Option;

    @FindBy(xpath = "//div[@id='modalEditColumns']//div[@id='ctl00_ContentPlaceHolder1_UCProviderClientReports1_btnHideFields_CD']")
    private WebElement UpdateFields_Actions_Columns;

    @FindBy(xpath = "//h5[normalize-space()='Choose which columns you see']")
    private WebElement EditColumns_Actions_Title;

    /**
     * Table Actions
     */
    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol1_I")
    private WebElement AppId_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol4_I")
    private WebElement FirstName_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol5_I")
    private WebElement LastName_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol6_I")
    private WebElement Email_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol11_I")
    private WebElement StatusDropDown_Drp;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol13_I")
    private WebElement PaymentStatusDropDown_Drp;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol17_I")
    private WebElement PrincipalBalance_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol10_I")
    private WebElement SubmittedOnDropDown_Drp;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol8_I")
    private WebElement State_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol2_I")
    private WebElement Provider_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol3_I")
    private WebElement PatientId_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol19_I")
    private WebElement Source_Input;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFREditorcol20_I")
    private WebElement PhoneNumber_Input;

    @FindBy(css = ".dxgvDataRow_MaterialCompact")
    private List<WebElement> TableDataRows_Tr;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXPagerBottom_PBN")
    private WebElement NextPage_Table_Btn;

    @FindBy(id = "ctl00_ContentPlaceHolder1_UCProviderClientReports1_ASPxGridView1_DXFilterBar_CBFilterEnabled_S_D")
    private WebElement CheckGreen_Table_Icon;

    @Step
    private void sendFilter(WebElement element) {   
        element.sendKeys(Keys.ENTER);
        DriverManager.waitUntilElementIsPresent(10, this.CheckGreen_Table_Icon);
    }

    @Step("Edit column missing")
    public void editColumnMissing(String filter) throws Exception {
        clickButton(Actions_Btn, "Actions");
        clickButton(EditColumns_Actions_Option, "Edit Columns");

        DriverManager.waitUntilElementIsPresent(10, EditColumns_Actions_Title);
        WebElement columnToSelect = DriverManager.getDriver()
            .findElement(By.xpath("//td[2][normalize-space()='" + filter + "']"));

        if(!(columnToSelect.getAttribute("class").toString().contains("dxeListBoxItemSelected_Material")))
            clickButton(columnToSelect, filter);

        clickButton(UpdateFields_Actions_Columns, "Update Fields"); 
    }
    
    @Step("Verifying that the Provider is on the Applications page")
    public Boolean verifyPresenceOfApplicationsPage() throws Exception {
        return isElementEnabled(Info_Icn);
    }

    @Step("Verifying that the column table is present")
    public void verifyColumnsTable(String filter) throws Exception  {
        DriverManager.waitUntilElementIsPresent(10, this.Info_Icn);
        editColumnMissing(filter);
    }

    @Step("Filtering applications by filter {0}")
    public void filteringApplicationsBy(String filter, String criteria) throws Exception {  
        switch (filter.toUpperCase()) {
            case "APP ID":
                enterText("App ID", AppId_Input, criteria);
                sendFilter(AppId_Input);
                break; 
            case "FIRST NAME":
                enterText("First Name", FirstName_Input, criteria);
                sendFilter(FirstName_Input);
                break;
            case "LAST NAME":
                enterText("Last Name", LastName_Input, criteria);
                sendFilter(LastName_Input);
                break;
            case "EMAIL":
                verifyColumnsTable(filter);
                enterText("Email", Email_Input, criteria);
                sendFilter(Email_Input);
                break;
            case "STATUS":
                enterText("Status", StatusDropDown_Drp, criteria);
                sendFilter(StatusDropDown_Drp);
                break;
            case "PAYMENT STATUS":
                enterText("Payment Status", PaymentStatusDropDown_Drp, criteria);
                sendFilter(PaymentStatusDropDown_Drp);
                break;
            case "PRINCIPAL BALANCE":
                verifyColumnsTable(filter);
                enterText("Principal Balance", PrincipalBalance_Input, criteria);
                sendFilter(PrincipalBalance_Input);
                break;
            case "SUBMITTED ON":
                enterText("Submitted On", SubmittedOnDropDown_Drp, criteria);
                sendFilter(SubmittedOnDropDown_Drp);
                break;
            case "STATE":
                verifyColumnsTable(filter);
                enterText("State", State_Input, criteria);
                sendFilter(State_Input);
                break;
            case "PROVIDER ID":
                verifyColumnsTable(filter);
                enterText("Provider", Provider_Input, criteria);
                sendFilter(Provider_Input);
                break;       
            case "PATIENT ID":
                verifyColumnsTable(filter);
                enterText("Patient ID", PatientId_Input, criteria);
                sendFilter(PatientId_Input);
                break;           
            case "SOURCE":
                verifyColumnsTable(filter);
                enterText("Source", Source_Input, criteria);
                sendFilter(Source_Input);
                break;
            case "PHONE NUMBER":
                verifyColumnsTable(filter);
                enterText("Phone Number", PhoneNumber_Input, criteria);
                sendFilter(PhoneNumber_Input);
                break;   
            default:
                break;
        }
    }

    @Step("Verifying that the Applications list filtered by specific criteria is correct")
    public String verifyApplicationsListedByCriteria(String filter, String criteria) {

        WebElement tableColumn;
        String tableRowStatus = new String();
    
        if(filter.toUpperCase().equals("PRINCIPAL BALANCE")){
            
            tableColumn =  this.TableDataRows_Tr.get(0).findElement(By.xpath("//td[@align='right']"));
            tableRowStatus = tableColumn.getText();

            return tableRowStatus.replace("$", "");
        }
        tableColumn =  this.TableDataRows_Tr.get(0).findElement(By.xpath("//td[normalize-space()='" + criteria + "']"));
        tableRowStatus = tableColumn.getText();
        
        return tableRowStatus;
    }
}
