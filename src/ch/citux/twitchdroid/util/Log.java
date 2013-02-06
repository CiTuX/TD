package ch.citux.twitchdroid.util;

import com.yixia.zi.utils.StringUtils;

public class Log {

    public static void d(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.d(tag, message);
        }
    }

}
