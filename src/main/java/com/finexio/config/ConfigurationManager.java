

package com.finexio.config;

import org.aeonbits.owner.ConfigCache;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ConfigurationManager {

    private ConfigurationManager() {
    }

    public static Configuration configuration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }

    public static ApiConfiguration apiConfiguration() {
        return ConfigCache.getOrCreate(ApiConfiguration.class);
    }

    public static ErrorDictionary errorDictionary() {
        return ConfigCache.getOrCreate(ErrorDictionary.class);
    }

}