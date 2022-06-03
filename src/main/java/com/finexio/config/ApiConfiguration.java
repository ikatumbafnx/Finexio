/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:40:37
 * @modify date 2022-06-03 15:40:37
 * @desc Load all the Api Configurations
 */
package com.finexio.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:apis.properties" })
public interface ApiConfiguration extends Config {

    

    
}
