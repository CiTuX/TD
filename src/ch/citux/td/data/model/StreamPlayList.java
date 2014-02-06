package ch.citux.td.data.model;

import java.util.HashMap;

public class StreamPlayList extends Base {

    public static final StreamQuality QUALITY_MOBILE = new StreamQuality("Mobile", 1);
    public static final StreamQuality QUALITY_LOW = new StreamQuality("Low", 2);
    public static final StreamQuality QUALITY_MEDIUM = new StreamQuality("Medium", 3);
    public static final StreamQuality QUALITY_HIGH = new StreamQuality("High", 4);
    public static final StreamQuality QUALITY_SOURCE = new StreamQuality("Source", 5);

    public static final StreamQuality[] SUPPORTED_QUALITIES = {
            QUALITY_MOBILE,
            QUALITY_LOW,
            QUALITY_MEDIUM,
            QUALITY_HIGH,
            QUALITY_SOURCE
    };

    private HashMap<StreamQuality, String> streams;

    public static StreamQuality parseQuality(String name) {
        for (StreamQuality quality : SUPPORTED_QUALITIES) {
            if (quality.getName().equals(name)) {
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
