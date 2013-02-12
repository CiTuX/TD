package ch.citux.twitchdroid.data.model;

import java.util.HashMap;

public class StreamPlayList extends Base {

    public static final StreamQuality QUALITY_LOW = new StreamQuality("iphonelow", "low", 0);
    public static final StreamQuality QUALITY_HIGH = new StreamQuality("iphonehigh", "high", 1);
    public static final StreamQuality QUALITY_240P = new StreamQuality("240p", 2);
    public static final StreamQuality QUALITY_360P = new StreamQuality("360p", 3);
    public static final StreamQuality QUALITY_480P = new StreamQuality("480p", 4);
    public static final StreamQuality QUALITY_720P = new StreamQuality("720p", 5);

    public static final StreamQuality[] SUPPORTED_QUALITIES = {
            QUALITY_LOW,
            QUALITY_HIGH,
            QUALITY_240P,
            QUALITY_360P,
            QUALITY_480P,
            QUALITY_720P
    };

    private HashMap<StreamQuality, String> streams;

    public static StreamQuality parseQuality(String name) {
        for (StreamQuality quality : SUPPORTED_QUALITIES) {
            if (quality.getKey().equals(name)) {
                return quality;
            }
        }
        return null;
    }

    public String getStream(StreamQuality quality) {
        return streams.get(quality);
    }

    public String getBestStream() {
        StreamQuality best = new StreamQuality("", -1);

        for (StreamQuality quality : streams.keySet()) {
            if (best.getValue() < quality.getValue()) {
                best = quality;
            }
        }

        if (best.getValue() > -1) {
            return getStream(best);
        }
        return null;
    }

    public HashMap<StreamQuality, String> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<StreamQuality, String> streams) {
        this.streams = streams;
    }
}
