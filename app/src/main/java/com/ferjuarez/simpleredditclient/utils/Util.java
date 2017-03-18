package com.ferjuarez.simpleredditclient.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ferjuarez on 3/18/17.
 */

public class Util {

    public static String getTimeAgo(long time){
        try {
            String timeParsed = epoch2DateString(time, "yyyy-mm-dd hh:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date past = format.parse(timeParsed);
            Date now = new Date();

            System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
            System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
            System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
        }
        catch (Exception j){
            j.printStackTrace();
        }
        return "";
    }

    public static String epoch2DateString(long epochSeconds, String formatString) {
        Date updatedate = new Date(epochSeconds * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(updatedate);
    }

}
