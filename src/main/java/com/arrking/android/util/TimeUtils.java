package com.arrking.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     *  string ——>Date
     */
    public static Date strToDate(String str,SimpleDateFormat dateFormat){
        try {
            return dateFormat.parse(str);
        }catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     *  string ——>Date
     */
    public static Date strToDate(String str){
        try {
            return DEFAULT_DATE_FORMAT.parse(str);
        }catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     *  date ——>string
     */
    public static String dateToStr(Date date){
        return DEFAULT_DATE_FORMAT.format(date);
    }

    /**
     *  date ——>string
     */
    public static String dateToStr(Date date,SimpleDateFormat dateFormat){
        return dateFormat.format(date);
    }

}
