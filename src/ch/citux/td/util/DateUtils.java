package ch.citux.td.util;

import android.text.format.DateFormat;

import java.util.Date;

import ch.citux.td.TDApplication;

public class DateUtils {

    public static String formateDate(Date date) {
        StringBuilder result = new StringBuilder();
        result.append(DateFormat.getDateFormat(TDApplication.getContext()).format(date));
        result.append(" ");
        result.append(DateFormat.getTimeFormat(TDApplication.getContext()).format(date));
        return result.toString();
    }

    public static String formatTime(long duration) {
        long minutes = duration / 60;
        long seconds = duration - (minutes * 60);
        return String.format("%d:%02d", minutes, seconds);
    }

}
