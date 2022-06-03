/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:41:50
 * @modify date 2022-06-03 15:41:50
 * @desc [description]
 */


package com.finexio.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;


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

    @Key("webpage.url")
    String webpageurl();

    
    @Key("api.endpoint")
    String apiendpoint();

    

}
