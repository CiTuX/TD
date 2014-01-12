package ch.citux.twitchdroid;

import android.content.Context;

import org.holoeverywhere.app.Application;

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
