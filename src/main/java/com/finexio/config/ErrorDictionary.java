/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:45:53
 * @modify date 2022-06-03 15:45:53
 * @desc Load All Error Configurations
 */
package com.finexio.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "system:properties", "classpath:errorcodes.properties" })
public interface ErrorDictionary extends Config {

   
}
