package ch.citux.twitchdroid.data.model;

import java.util.HashMap;

public class StreamPlayList extends Base {

    public static final StreamQuality QUALITY_LOW = new StreamQuality("iphonelow", "low", 0);
    public static final StreamQuality QUALITY_HIGH = new StreamQuality("iphonehigh", "high", 1);
    public static final StreamQuality QUALITY_240P = new StreamQuality("240p", 2);
    public static final StreamQuality QUALITY_360P = new StreamQuality("360p", 3);
    public static final StreamQuality QUALITY_480P = new StreamQuality("480p", 4);
    public static final StreamQuality QUALITY_720P = new StreamQuality("720p", 5);

    public static final String[] SUPPORTED_QUALITIES = {
            QUALITY_LOW.getKey(),
            QUALITY_HIGH.getKey(),
            QUALITY_240P.getKey(),
            QUALITY_360P.getKey(),
            QUALITY_480P.getKey(),
            QUALITY_720P.getKey()
    };

    private HashMap<String, String> streams;

    public String getStream(StreamQuality quality) {
        return streams.get(quality.getKey());
    }

    public HashMap<String, String> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<String, String> streams) {
        this.streams = streams;
    }
}
