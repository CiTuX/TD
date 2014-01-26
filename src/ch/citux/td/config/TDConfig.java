package ch.citux.td.config;

public class TDConfig {

    public static final String SETTINGS_CHANNEL_NAME = "channel_name";

    public static final String MIME_FLV = "video/x-flv";

    public static final String TWITCH_ACCEPT = "application/vnd.twitchtv.v2+json";
    public static final String USHER_STREAM_TOKEN = "Wd75Yj9sS26Lmhve";

    public static final String URL_API_TWITCH_BASE = "https://api.twitch.tv/kraken/";
    public static final String URL_API_JUSTIN_BASE = "https://api.justin.tv/api/";
    public static final String URL_API_USHER_BASE = "http://usher.justin.tv/";

    public static final String URL_API_GET_FOLLOWS = URL_API_TWITCH_BASE + "users/{0}/follows/channels";
    public static final String URL_API_GET_VIDEOS = URL_API_TWITCH_BASE + "channels/{0}/videos?limit={1}&broadcasts=true";
    public static final String URL_API_GET_VIDEO = URL_API_JUSTIN_BASE + "broadcast/by_archive/{0}.json";
    public static final String URL_API_GET_CHANNEL = URL_API_TWITCH_BASE + "channels/{0}";
    public static final String URL_API_GET_STREAM = URL_API_TWITCH_BASE + "streams/{0}";
    public static final String URL_API_GET_STREAM_TOKEN = URL_API_USHER_BASE + "stream/iphone_token/{0}.json?connection=wifi&type=any";
    public static final String URL_API_GET_STREAM_PLAYLIST = URL_API_USHER_BASE + "stream/multi_playlist/{0}.m3u8?token={1}&hd={2}";

}
