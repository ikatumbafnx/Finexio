/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:52:00
 * @modify date 2022-06-03 15:52:00
 * @desc [description]
 */
package com.finexio.utils;


import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    public static String currentTimeInISO8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    public enum EChangeFormat {
        Day, Week, Quarter, Month, Year
    }

    public enum EChangeTimeTo {
        MilliSeconds, Seconds, Minutes, Hours
    }

    //Class Constants
    private final static String _dateFormat = "MM/dd/yyyy";

    private final static String _mdyyFormat = "M/d/yy";

    /**
     * Get local date in MM/dd/yyyy pattern
     *
     * @return
     */
    public static String getDate() {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(_dateFormat);
        LocalDate date = new LocalDate();
        return dateFormat.print(date);
    }

    /**
     * Get local date in given pattern
     *
     * @param pattern
     * @return
     */
    public  String getDate(String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(pattern);
        LocalDateTime date = new LocalDateTime();
        return dateFormat.print(date);
    }

    /**
     * Change date
     *
     * @param inputDate,     provide date to which you want to add or subtract from
     * @param eChangeFormat  Eg: EChangeFormat.Day
     * @param amountToChange Eg: +10, -5, 0
     * @return Date
     * @throws ParseException
     * @Sample - DateUtil.changeDate("07/02/2019", DateUtil.EChangeFormat.Day, +10); //Will add 10 days to given date
     */
    public  String changeDate(String inputDate, EChangeFormat eChangeFormat, int amountToChange) throws ParseException {
        Date dateTOChange = new SimpleDateFormat(_dateFormat).parse(inputDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTOChange);
        switch (eChangeFormat) {
            case Day:
                calendar.add(Calendar.DATE, amountToChange);
                break;
            case Week:
                calendar.add(Calendar.DATE, (7 * amountToChange));
                break;
            case Month:
                calendar.add(Calendar.MONTH, amountToChange);
                break;
            case Quarter:
                calendar.add(Calendar.MONTH, (3 * amountToChange));
                break;
            case Year:
                calendar.add(Calendar.YEAR, amountToChange);
                break;
            default:
                break;
        }
        Date newDate = calendar.getTime();
        DateFormat df = new SimpleDateFormat(_dateFormat);
        String Date = df.format(newDate);
        return Date;
    }

    /**
     * Get number of days between two dates
     *
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static int getNumberOfDays(String date1, String date2, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        LocalDate localDate1 = formatter.parseDateTime(date1).toLocalDate();
        LocalDate localDate2 = formatter.parseDateTime(date2).toLocalDate();
        int days = Days.daysBetween(localDate1, localDate2).getDays();
        return days < 0 ? -(days) : days;
    }

    /**
     * Get number of days between two dates
     *
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static int getNumberOfDaysDifference(String date1, String date2, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        LocalDate localDate1 = formatter.parseDateTime(date1).toLocalDate();
        LocalDate localDate2 = formatter.parseDateTime(date2).toLocalDate();
        int days = Days.daysBetween(localDate1, localDate2).getDays();
        return days;
    }

    /**
     * Get number of business days between two dates
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getNumberOfBusinessDays(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        return workDays;
    }

    /**
     * Get date as plain string without special characters
     *
     * @return
     */
    public static String getDateAsString() {
        return DateFormat.getInstance().format(new java.util.Date()).replaceAll("[/: ]", "");
    }

    /**
     * Get date and time stamp as plain string without special characters
     *
     * @return
     */
    public String getTimeStampAsString() {
        return getDate("MM/dd/yy HH:mm:ss").replaceAll("[/: ]", "");
    }

    /**
     * Convert date in given pattern to expected pattern
     *
     * @param date
     * @param currentPattern
     * @param expectedPattern
     * @return
     */
    public String convertDateToPattern(String date, String currentPattern, String expectedPattern) {
        DateTimeFormatter currentFormatter = DateTimeFormat.forPattern(currentPattern);
        DateTime dateTime = currentFormatter.parseDateTime(date);
        DateTimeFormatter expectedFormatter = DateTimeFormat.forPattern(expectedPattern);
        return expectedFormatter.print(dateTime);
    }



    /**
     * Get date as text (Eg: 02-January-2020)
     *
     * @return
     */
    public static String getDateAsText() {
        DateTime date = DateTime.now();
        return date.getYear() + "-" + date.monthOfYear().getAsText() + "-" + String.format("%02d", date.getDayOfMonth());
    }

    /**
     * Get local time in given pattern
     *
     * @param pattern
     * @return
     */
    public static String getTimeAsPattern(String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(pattern);
        LocalTime time = new LocalTime();
        return dateFormat.print(time);
    }


    /**
     * To convert Nanoseconds into other time units
     *
     * @param time
     * @param eChangeTimeTo
     * @return
     */
    public static Long convertNanoTimeTo(Long time, EChangeTimeTo eChangeTimeTo) {
        Long convertedTime = null;
        switch (eChangeTimeTo) {
            case Seconds:
                convertedTime = TimeUnit.NANOSECONDS.toSeconds(time);
                break;
            case MilliSeconds:
                convertedTime = TimeUnit.NANOSECONDS.toMillis(time);
                break;
            case Minutes:
                convertedTime = TimeUnit.NANOSECONDS.toMinutes(time);
                break;
            case Hours:
                convertedTime = TimeUnit.NANOSECONDS.toHours(time);
                break;
            default:
                break;
        }
        return convertedTime;
    }

    /**
     * This function will format M/d/yy date to MM/dd/yyyy
     * If the input string doesn't match M/d/yy format it will simply return the input value
     *
     * @param stringDate
     * @return
     */
    public static String formatDateString(String stringDate) throws ParseException {
        SimpleDateFormat currentFormat = new SimpleDateFormat(_mdyyFormat);
        if (stringDate.matches("([0-9]{1})/([0-9]{1})/([0-9]{2})")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(_dateFormat);
            return dateFormat.format(currentFormat.parse(stringDate));
        } else {
            return stringDate;
        }
    }

    /**
     * This function will return a boolean by checking if the date string is in expected format.
     *
     * @param date       Date should be string data type e.g. (01/01/2021 or 1-8-21, etc..)
     * @param dateFormat dateformat should be in steing format e.g. (mm-dd-yyyy or mm/dd/yyyy, etc..)
     * @return
     */
    public static boolean isDateValid(String date, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


}
