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
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

}
