package com.finixio.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:errorcodes.properties" })
public interface ErrorDictionary extends Config {

    @Key("1065")
    String Err1065();

}
