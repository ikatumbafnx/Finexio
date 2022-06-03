/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:53:02
 * @modify date 2022-06-03 15:53:02
 * @desc [description]
 */
package com.finexio.utils;

import io.qameta.allure.Step;


public class NumberUtils {

    /**
     * Get the First or Last number from a Number
     */
    @Step("Getting or Extracting the {1} {2} from {0}")
    public int getFirstOrLastFromANumber(String number , String OPtionFirstOrLast, int CutOffDigits){

        int finalNum = 0;
        switch (OPtionFirstOrLast){

            case "first":
                String str = number;
                String firstNumbers = str.substring(0,CutOffDigits);
                finalNum =  Integer.parseInt(firstNumbers);
                break;

            case "last":
                String last = number;
                String lastNumbers = last.substring(last.length() - CutOffDigits );
                finalNum = Integer.parseInt(lastNumbers);
                break;

        }
        return finalNum;
    }
}
