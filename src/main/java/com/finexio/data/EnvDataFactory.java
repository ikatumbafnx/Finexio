/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:46:35
 * @modify date 2022-06-03 15:46:35
 * @desc 
 */
package com.finexio.data;

import java.util.Random;

import com.github.javafaker.Faker;

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
