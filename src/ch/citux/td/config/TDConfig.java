package ch.citux.td.config;

public class TDConfig {

    public static final String SETTINGS_CHANNEL_NAME = "channel_name";

    public static final String MIME_FLV = "video/x-flv";

    public static final String TWITCH_ACCEPT = "application/vnd.twitchtv.v2+json";

    public static final String URL_API_TWITCH_KRAKEN_BASE = "https://api.twitch.tv/kraken/";
    public static final String URL_API_TWITCH_API_BASE = "https://api.twitch.tv/api/";
    public static final String URL_API_JUSTIN_BASE = "https://api.justin.tv/api/";
    public static final String URL_API_USHER_BASE = "http://usher.twitch.tv/";

    public static final String URL_API_GET_FOLLOWS = URL_API_TWITCH_KRAKEN_BASE + "users/{0}/follows/channels";
    public static final String URL_API_GET_VIDEOS = URL_API_TWITCH_KRAKEN_BASE + "channels/{0}/videos?limit={1}&broadcasts=true";
    public static final String URL_API_GET_VIDEO = URL_API_JUSTIN_BASE + "broadcast/by_archive/{0}.json";
    public static final String URL_API_GET_CHANNEL = URL_API_TWITCH_KRAKEN_BASE + "channels/{0}";
    public static final String URL_API_GET_STREAM = URL_API_TWITCH_KRAKEN_BASE + "streams/{0}";
    public static final String URL_API_GET_STREAM_TOKEN = URL_API_TWITCH_API_BASE + "channels/{0}/access_token";
    public static final String URL_API_GET_STREAM_PLAYLIST = URL_API_USHER_BASE + "select/{0}.json?p={1}&nauth={2}&allow_source=true&nauthsig={3}&type=any&private_code=null";

}