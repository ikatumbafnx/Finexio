package com.finexio.data;

import com.github.javafaker.Faker;

import static com.finexio.config.ConfigurationManager.configuration;

import java.util.Locale;
import java.util.Random;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class EnvDataFactory {

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static String randomEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }


}
