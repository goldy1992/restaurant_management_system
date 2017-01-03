package com.mike.client.helpers;

/**
 * Created by Mike on 03/01/2017.
 */
public class TimeHelper {
    private static String sortTimeSyntax(int time) {
        if (time == 0) {
            return "00";
        } else if (time > 0 && time <= 9) {
            return "0" + time;
        } else {
            return time + "";
        }
    } // sortTimeSyntax

    public static String timeToString(int hours, int minutes)
    {
        return sortTimeSyntax(hours) + ":" + sortTimeSyntax(minutes);
    }
}
