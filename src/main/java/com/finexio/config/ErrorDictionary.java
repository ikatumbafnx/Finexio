package com.finexio.config;

import org.aeonbits.owner.Config;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:errorcodes.properties" })
public interface ErrorDictionary extends Config {

   
}
