package com.finexio.config;

import org.aeonbits.owner.Config;

/**
 * @author Ivan Katumba on 10/20/2021
 * @project HFD-Automation
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:apis.properties" })
public interface ApiConfiguration extends Config {

    

    
}
