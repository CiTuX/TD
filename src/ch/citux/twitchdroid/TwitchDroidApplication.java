package ch.citux.twitchdroid;

import android.app.Application;
import android.content.Context;

public class TwitchDroidApplication extends Application {

    private static TwitchDroidApplication instance;

    public TwitchDroidApplication() {
        instance = this;
    }

    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        }
        return null;
    }

}
