/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.citux.td.config;

public class TDConfig {

    public static final String SETTINGS_CHANNEL_NAME = "channel_name";

    public static final String CAST_APPLICATION_ID = "D25E1BBE";

    public static final String UTF_8 = "UTF-8";

    public static final String MIME_FLV = "video/x-flv";
    public static final String MIME_TWITCH = "application/vnd.twitchtv.v3+json";

    public static final String URL_API_TWITCH_KRAKEN_BASE = "https://api.twitch.tv/kraken/";
    public static final String URL_API_GET_FOLLOWS = URL_API_TWITCH_KRAKEN_BASE + "users/{0}/follows/channels";
    public static final String URL_API_GET_VIDEOS = URL_API_TWITCH_KRAKEN_BASE + "channels/{0}/videos?offset={1}&limit=10&broadcasts=true";
    public static final String URL_API_GET_CHANNEL = URL_API_TWITCH_KRAKEN_BASE + "channels/{0}";
    public static final String URL_API_GET_STREAM = URL_API_TWITCH_KRAKEN_BASE + "streams/{0}";
    public static final String URL_API_GET_STREAMS = URL_API_TWITCH_KRAKEN_BASE + "streams?game={0}&limit=20&offset={1}";
    public static final String URL_API_SEARCH_STREAMS = URL_API_TWITCH_KRAKEN_BASE + "search/streams?limit=20&q={0}&offset={1}";
    public static final String URL_API_SEARCH_CHANNELS = URL_API_TWITCH_KRAKEN_BASE + "search/channels?limit=20&q={0}&offset={1}";
    public static final String URL_API_GET_TOP_GAMES = URL_API_TWITCH_KRAKEN_BASE + "games/top?limit={0}&offset={1}";

    public static final String URL_API_TWITCH_API_BASE = "https://api.twitch.tv/api/";
    public static final String URL_API_GET_STREAM_TOKEN = URL_API_TWITCH_API_BASE + "channels/{0}/access_token";

    public static final String URL_API_JUSTIN_BASE = "https://api.justin.tv/api/";
    public static final String URL_API_GET_VIDEO = URL_API_JUSTIN_BASE + "broadcast/by_archive/{0}.json";

    public static final String URL_API_USHER_BASE = "http://usher.twitch.tv/";
    public static final String URL_API_GET_STREAM_PLAYLIST = URL_API_USHER_BASE + "select/{0}.json?p={1}&nauth={2}&allow_source=true&nauthsig={3}&type=any&private_code=null";

}