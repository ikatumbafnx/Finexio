package com.finexio.utils;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class PropertyUtils {

    //Class constants
    private String _value = "";

    protected static Properties applicationProperties = new Properties();
    private static String filePath = "src/main/resources/application.properties";

    /**
     * Set properties file path
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read value from properties file
     * @param key
     * @return
     * @throws IOException
     */
    public String getProperty(String key) throws IOException {
        applicationProperties.load(new FileInputStream(filePath));
        try {
            _value =  applicationProperties.getProperty(key);
            if(_value.equalsIgnoreCase("")){
                _value="";
            }
        } catch (Exception e) {
            _value="";
        }
        return _value;
    }

    /**
     * Set value to properties file
     * @param key
     * @param value
     * @throws Exception
     */
    public void setProperty(String key, String value) throws Exception {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder(PropertiesConfiguration.class).configure(new FileBasedBuilderParametersImpl()
                        .setFile(new File(filePath)));
        PropertiesConfiguration config = builder.getConfiguration();
        config.setProperty(key, value);
        builder.save();
    }
}
