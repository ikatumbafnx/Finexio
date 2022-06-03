

package com.finexio.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */

@LoadPolicy(LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:general.properties", "classpath:local.properties",
        "classpath:grid.properties", "classpath:application.properties" })
public interface Configuration extends Config {

    @Key("target")
    String target();

    @Key("environment")
    String environment();

    @Key("db.server")
    String dbserver();

    @Key("sandbox.db.server")
    String dbsandboxserver();

    @Key("db.dbName")
    String dbname();

    @Key("db.username")
    String dbuser();

    @Key("db.password")
    String dbpsswd();

    @Key("db.authType")
    String dbauthtype();

    @Key("browser")
    String browser();

    @Key("headless")
    Boolean headless();

    @Key("url.base")
    String url();

    @Key("timeout")
    int timeout();

    @Key("grid.url")
    String gridUrl();

    @Key("grid.port")
    String gridPort();

    @Key("faker.locale")
    String faker();

    @Key("provider.url")
    String providerportal();

    @Key("customer.url")
    String customerportal();

    @Key("ops.url")
    String opsportal();

    @Key("ops.userName")
    String opsusername();

    @Key("ops.password")
    String opspassword();

    @Key("origination.url")
    String originationportal();

    @Key("provider.userName")
    String providerusername();

    @Key("provider.password")
    String providerpassword();

    @Key("api.endpoint")
    String apiendpoint();

    @Key("v2.service.auth")
    String authenticate();

    @Key("v2.service.createapplication")
    String createapplication();

    @Key("v2.service.updateeapplication")
    String updateapplication();

    @Key("v2.service.createAgreement")
    String createAgreement();

    @Key("v2.service.eSignAgreement")
    String eSignAgreement();

    @Key("v2.service.emailAgreement")
    String emailAgreement();

    @Key("v2.service.createAddendum")
    String createAddendum();

    @Key("v2.service.eSignAddendum")
    String eSignAddendum();

    @Key("v2.service.activateAccount")
    String activateAccount();

    @Key("v2.service.getaccountdetails")
    String getaccountdetails();

    @Key("customer.verification.url")
    String customeraccountverification();

    @Key("api.communications.endpoint")
    String communicationsAPIEndpoint();

    @Key("easyapi.service.auth")
    String easyApiAuthService();

    @Key("api.easyapi.auth.endpoint")
    String easyApiAuthEndPoint();

    @Key("api.easyapi.sandbox.auth.endpoint")
    String easyApiAuthEndPointSadbox();

    @Key("api.easyapi.endpoint")
    String easyApiEndPoint();

    @Key("sandbox.api.easyapi.endpoint")
    String easyApiEndPointSandBox();

    @Key("dev.api.easyapi.auth.endpoint")
    String easyApiAuthEndPointDev();

    @Key("dev.api.easyapi.endpoint")
    String easyApiEndPointDev();

    @Key("api.dev.communications.endpoint")
    String devcommunicationsAPIEndpoint();

    @Key("api.dev.communications.auth.endpoint")
    String devAuthcommunicationsAPIEndpoint();

}
