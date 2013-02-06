package ch.citux.twitchdroid.util;

import ch.citux.twitchdroid.BuildConfig;
import com.yixia.zi.utils.StringUtils;

public class Log {

    public static void d(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.d(tag, message);
        }
    }

    public static void d(Object caller, String message) {
        String tag = caller.getClass().getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(Class caller, String message) {
        String tag = caller.getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(String tag, Exception exception) {
        if (exception != null && !StringUtils.isBlank(exception.getMessage())) {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            } else {
                android.util.Log.e(tag, exception.getMessage());
            }
        }
    }

    public static void e(Class caller, Exception exception) {
        String tag = caller.getSimpleName();
        if (exception != null && !StringUtils.isBlank(exception.getMessage())) {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            } else {
                android.util.Log.e(tag, exception.getMessage());
            }
        }
    }

}
