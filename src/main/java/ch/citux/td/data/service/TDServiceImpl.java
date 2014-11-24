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
package ch.citux.td.data.service;

import ch.citux.td.BuildConfig;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.TwitchAccessToken;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchChannels;
import ch.citux.td.data.model.TwitchFollows;
import ch.citux.td.data.model.TwitchGames;
import ch.citux.td.data.model.TwitchStream;
import ch.citux.td.data.model.TwitchVideos;
import ch.citux.td.data.service.TDService.TwitchAPI;
import ch.citux.td.data.service.TDService.TwitchKraken;
import ch.citux.td.data.service.TDService.TwitchUsher;
import ch.citux.td.util.Log;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

public class TDServiceImpl implements TwitchAPI, TwitchKraken, TwitchUsher, RestAdapter.Log {

    private static final String TAG = "TDService";

    private static TDServiceImpl instance;

    private TwitchAPI twitchAPI;
    private TwitchUsher twitchUsher;
    private TwitchKraken twitchKraken;

    private TDServiceImpl() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLog(this)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new JacksonConverter());

        RestAdapter apiAdapter = builder
                .setEndpoint(TDConfig.URL_API_TWITCH_API_BASE)
                .build();
        RestAdapter usherAdapter = builder
                .setEndpoint(TDConfig.URL_API_USHER_BASE)
                .build();
        RestAdapter krakenAdapter = builder
                .setEndpoint(TDConfig.URL_API_TWITCH_KRAKEN_BASE)
                .setRequestInterceptor(new KrakenRequestInterceptor())
                .build();

        twitchAPI = apiAdapter.create(TwitchAPI.class);
        twitchUsher = usherAdapter.create(TwitchUsher.class);
        twitchKraken = krakenAdapter.create(TwitchKraken.class);
    }

    public static TDServiceImpl getInstance() {
        if (instance == null) {
            instance = new TDServiceImpl();
        }
        return instance;
    }

    @Override
    public void log(String message) {
        Log.d(TAG, message);
    }

    private class KrakenRequestInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestInterceptor.RequestFacade request) {
            request.addHeader("Accept", TDConfig.MIME_TWITCH);
        }
    }

    @Override
    public TwitchFollows getFollows(String username) {
        return twitchKraken.getFollows(username);
    }

    @Override
    public TwitchChannel getChannel(String channel) {
        return twitchKraken.getChannel(channel);
    }

    @Override
    public TwitchStream getStream(String channel) {
        return twitchKraken.getStream(channel);
    }

    @Override
    public TwitchStream getStreams(String game, int offset) {
        return twitchKraken.getStreams(game, offset);
    }

    @Override
    public TwitchVideos getVideos(String channel, int offset) {
        return twitchKraken.getVideos(channel, offset);
    }

    @Override
    public TwitchBroadcast getVideoPlaylist(String id) {
        return twitchAPI.getVideoPlaylist(id);
    }

    @Override
    public TwitchAccessToken getStreamToken(String channel) {
        return twitchAPI.getStreamToken(channel);
    }

    @Override
    public Response getStreamPlaylist(String channel, String p, String nauth, String nauthsig) {
        return twitchUsher.getStreamPlaylist(channel, p, nauth, nauthsig);
    }

    @Override
    public TwitchStream searchStreams(String query, int offset) {
        return twitchKraken.searchStreams(query, offset);
    }

    @Override
    public TwitchChannels searchChannels(String query, int offset) {
        return twitchKraken.searchChannels(query, offset);
    }

    @Override
    public TwitchGames getTopGames(int limit, int offset) {
        return twitchKraken.getTopGames(limit, offset);
    }
}