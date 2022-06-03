package com.finexio.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:apis.properties" })
public interface ApiConfiguration extends Config {

    @Key("v3.api.endpoint")
    String v3Serviceurl();

    @Key("v3.api.endpoint.dev")
    String v3DevServiceurl();

    @Key("v3.api.authurl")
    String v3Authurl();

    @Key("payapi.authurl.dev")
    String payApiDevAuthurl();

    @Key("v3.api.authurl.dev")
    String v3DevAuthurl();

    @Key("v3.service.auth")
    String v3Authenticate();

    @Key("v3.service.createapplication")
    String v3createapplication();

    @Key("v3.service.getApplicationDetails")
    String v3getApplicationDetails();

    @Key("v3.service.createPaymentMethods")
    String v3createPaymentMethods();

    @Key("v3.service.getAgreement")
    String v3getAgreement();

    @Key("v3.service.eSignAgreement")
    String v3eSignAgreement();

    @Key("v3.service.emailAgreement")
    String v3emailAgreement();

    @Key("v3.service.activateApplication")
    String v3activateApplication();

    @Key("v3.service.underwritting")
    String v3Underwritting();

    @Key("ClientId_QA_15357")
    String clientID_QA_15357();

    @Key("ClientSecret_QA_15357")
    String clientSecret_QA_15357();

    @Key("ClientId_QA_15356")
    String clientID_QA_15356();

    @Key("ClientSecret_QA_15356")
    String clientSecret_QA_15356();

    // DEV Credential for QA
    @Key("ClientId_QADEV_15357")
    String clientID_QADEV_15357();

    @Key("ClientSecret_QADEV_15357")
    String clientSecret_QADEV_15357();

    @Key("ClientId_QADEV_15356")
    String clientID_QADEV_15356();

    @Key("ClientSecret_QADEV_15356")
    String clientSecret_QADEV_15356();

    @Key("ClientId_Waterfall")
    String clientIDWaterFall();

    @Key("ClientSecret_Waterfall")
    String clientSecretWaterFall();

    @Key("clientID.Dev")
    String DevclientID();

    @Key("clientSecret.Dev")
    String DevclientSecret();

    @Key("local-Canada")
    String locale_canada();

    @Key("locale-US")
    String locale_us();

    @Key("azure.getTestPoint")
    String azureGetTestPointID();

    @Key("azure.api.endpoint")
    String getAzureApiUrl();

    @Key("azure.api.createTestRun")
    String azureCreateTestRun();

    @Key("azure.api.createTestResult")
    String azureCreateTestResults();

    @Key("azure.api.updateTestCase")
    String azureUpdateTestCase();

    @Key("azure.api.token")
    String getAzureToken();

    @Key("azure.testplan.id")
    String getAzureTestPlanID();

    @Key("azure.testsuite.id")
    String getAzureTestSuiteID();

    @Key("communication.api.retrievephone")
    String communicationAPIGetPhoneInfo();

    @Key("communication.api.verifyphone")
    String communicationVerifyPhone();

    @Key("communication.api.confirmphone")
    String communicationConfirmPhone();

    @Key("communication.api.sendsms")
    String communicationSendSMS();

    @Key("communication.api.sendemail")
    String communicationSendEmail();

    @Key("communication.api.verifyemail")
    String communicationVerifyEmail();

    @Key("communication.api.confirmemail")
    String communicationConfirmEmail();

    @Key("communications.endpoint.v0")
    String communicationAPIEndpointV0();

    @Key("grantType.communication")
    String communicationgrantType();

    @Key("clientID.communication")
    String communicationclientID();

    @Key("clientSecret.communication")
    String communicationClientScreet();

    // Easy Api properties
    @Key("grantType.SleepArch")
    String sleepArchgrantType();

    @Key("clientID.SleepArch")
    String sleepArchclientID();//

    @Key("sandboxclientID.SleepArch")
    String sleepArchclientIDSandBox();

    @Key("sandboxgrantType.SleepArch")
    String sleepArchclientIDStage();

    @Key("clientSecret.SleepArch")
    String SsleepArchClientScreet();

    @Key("sandboxclientSecret.SleepArch")
    String SsleepArchClientScreetStage();

    @Key("easyapi.start.url")

    String easyApiStart();

    @Key("easyapi.peek.url")
    String easyApiPeek();

    // Finance Offers

    @Key("grantType.finance")
    String financegrantType();

    @Key("clientID.finance")
    String financeclientID();

    @Key("clientSecret.finance")
    String financeClientScreet();

    // Pay Apis

    @Key("payapi.service.gateway")
    String getGateways();

    @Key("payapi.service.payment")
    String payApimakePayment();

    @Key("payapi.dev.clientID")
    String payApiDevClientID();

    @Key("payapi.dev.clietSecret")
    String payApiDevClientSecret();

    @Key("payapi.dev.grantype")
    String payApiDevgrantType();

    @Key("payapi.dev.baseurl")
    String payApiDevBaseUrl();

    @Key("payapi.stage.baseurl")
    String payApiStageBaseUrl();

    @Key("payapi.authurl.stage")
    String payApiStageAuthUrl();

    @Key("v2.1.stage.baseurl")
    String v2OneStageBaseurl();

}
