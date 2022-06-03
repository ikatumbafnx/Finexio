package com.finexio.utils;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
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
