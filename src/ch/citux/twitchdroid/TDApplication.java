package ch.citux.twitchdroid;

import android.app.Application;
import android.content.Context;

public class TDApplication extends Application {

    private static TDApplication instance;

    public TDApplication() {
        instance = this;
    }

    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        }
        return null;
    }

}
