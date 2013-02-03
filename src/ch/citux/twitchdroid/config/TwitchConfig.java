package ch.citux.twitchdroid.config;

public class TwitchConfig {

    public static final String CLIENT_ID = "sx9d3whtcht2lhrtd6yjia8rv019wxi";

    public static final String URL_API_TWITCH_BASE = "https://api.twitch.tv/kraken/";
    public static final String URL_API_JUSTIN_BASE = "https://api.justin.tv/api/";
    public static final String URL_API_USHER_BASE = "http://usher.justin.tv/";

    public static final String URL_API_GET_FAVORITES = URL_API_JUSTIN_BASE + "user/favorites/{0}.json";
    public static final String URL_API_LIST_STREAMS = URL_API_JUSTIN_BASE + "stream/list.json?channel={0}";
    public static final String URL_API_GET_STREAM_TOKEN = URL_API_USHER_BASE + "stream/iphone_token/{0}.json"; //?connection=wifi&type=any
    public static final String URL_API_GET_STREAM_PLAYLISTS = URL_API_USHER_BASE + "stream/multi_playlist/{0}.m3u8"; //?token=09de1b354da7bbf4a1373e23fb416ad0b6d0b4ac:fc99f7d6a41ea34afd451495b5f8354ed878b80f:{\\\"expiration\\\": 1359848689.992758, \\\"channel\\\": \\\"esltv_lol\\\", \\\"user_agent\\\": \\\".*\\\"}&hd=true";


}
