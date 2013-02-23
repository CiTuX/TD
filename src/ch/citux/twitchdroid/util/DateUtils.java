package ch.citux.twitchdroid.util;

import android.text.format.DateFormat;
import ch.citux.twitchdroid.TDApplication;

import java.util.Date;

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
