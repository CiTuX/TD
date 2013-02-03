package ch.citux.twitchdroid.data.model;

import java.util.HashMap;

public class StreamPlayList extends Base {

    public static final String QUALITY_240P = "240p";
    public static final String QUALITY_360P = "360p";
    public static final String QUALITY_480P = "480p";
    public static final String QUALITY_720P = "720p";
    public static final String QUALITY_BEST = "BEST";

    private HashMap<String,String> streams;

    public HashMap<String, String> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<String, String> streams) {
        this.streams = streams;
    }
}
