/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:53:25
 * @modify date 2022-06-03 15:53:25
 * @desc [description]
 */
package com.finexio.utils;

import java.util.Random;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;


public class StringUtil {

    public static String randomNameFor(String prefix) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return prefix + "_" + generatedString;
    }

    /**
     * Get number in given String
     * 
     * @param input
     * @return
     */
    public static String getNumberFromString(String input) {
        return input.replaceAll("[^0-9]+", "");
    }
    // No change

    /**
     * Remove particular subString from input String
     * 
     * @param input
     * @param removeString
     * @return
     */
    public static String removeFromString(String input, String removeString) {
        return input.replaceAll(removeString, "");
    }

    /**
     * Split the String according to delimiter and return the array of String.
     * 
     * @param input
     * @param delimeter
     * @return
     */
    public static String[] splitString(String input, String delimeter) {
        String[] strings = input.split(delimeter);
        return strings;
    }

    /**
     * Replace only first occurrence of passed char with desired char
     * 
     * @param input
     * @param oldString
     * @param newString
     * @return
     */
    public static String replaceFirstOccurrence(String input, String oldString, String newString) {
        String replacedString = input.replaceFirst(oldString, newString);
        return replacedString;
    }

    /**
     * Random Number generation
     * 
     * @param minimum
     * @param maximum
     * @return
     * @throws Exception
     */
    public static String generateRandomNumber(int minimum, int maximum) throws Exception {
        String randomNum = String.valueOf(minimum + (int) (Math.random() * maximum));
        return randomNum;
    }

    /**
     * Replace all occurrences of the given string with desired string
     * 
     * @param input
     * @param oldString
     * @param newString
     * @return
     */
    public static String replaceAllOccurrences(String input, String oldString, String newString) {
        String replacedString = input.replaceAll(oldString, newString);
        return replacedString;
    }

    /**
     * Replace Boolean values to Pass or Fail
     * 
     * @param string
     * @return
     */
    public static String replaceBooleanToPassFail(String string) {
        return string.trim().replaceAll("true", "PASS").replaceAll("false", "FAIL")
                .replaceAll("TRUE", "PASS").replaceAll("FALSE", "FAIL");
    }

    /**
     * Converts string to hasmap seperated by given delimiter
     * EG: If input string = 'x=1,2;y=3,4' and regex = ';'
     * 
     * @return HashMap (x-> 1,2 and y->3,4)
     * @param input
     * @param delimiter
     * @throws IOException
     */
    public static HashMap stringToHashMap(String input, String delimiter) throws IOException {
        HashMap map = new HashMap();
        String propFormat = input.replaceAll(delimiter, "\n");
        Properties properties = new Properties();
        properties.load(new StringReader(propFormat));
        map = new HashMap<>(properties);
        return map;
    }

    /**
     * Coverts string to arraylist seperated by given delimiter
     * EG: if input String is '1,2,3' and delimiter is ','. This will be converted
     * to a List.
     * 
     * @param input
     * @param delimiter
     * @return
     */
    public static ArrayList stringToArrayList(String input, String delimiter) {
        ArrayList arrayList = new ArrayList(Arrays.asList(input.split("\\s*" + delimiter + "\\s*")));
        return arrayList;
    }

    /**
     * Convert HashMap<String, ArrayList> to HashMap<String, String> by providing
     * the ArrayList index
     * 
     * @param map
     * @param index
     * @return
     */
    public static HashMap<String, String> hashmapConversion(HashMap<String, ArrayList> map, int index) {
        HashMap<String, String> data = new HashMap<>();
        ArrayList<String> columns = new ArrayList<>(map.keySet());
        for (String s : columns) {
            data.put(s, String.valueOf(map.get(s).get((index - 1))));
        }
        return data;
    }

    /**
     * Convert Dollar Amount to String
     * ex : $1,000.00 -> 1000
     * 
     * @param input
     * @return
     */
    public static String convertDollarAmountToString(String input) {
        String amount = input.substring(0, input.indexOf("."));
        amount = getNumberFromString(amount);
        return amount;
    }

    /**
     * Convert String to Dollar Amount
     * ex: 1000 -> $1,000.00
     * 
     * @param input
     * @return
     */
    public static String convertStringToDollarAmount(String input) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(new BigDecimal(input));
    }

}
