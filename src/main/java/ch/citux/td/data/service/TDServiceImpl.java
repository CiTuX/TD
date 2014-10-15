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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.chilicat.m3u8.Element;
import net.chilicat.m3u8.Playlist;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.dto.JustinArchive;
import ch.citux.td.data.dto.TwitchAccessToken;
import ch.citux.td.data.dto.TwitchChannel;
import ch.citux.td.data.dto.TwitchChannels;
import ch.citux.td.data.dto.TwitchFollows;
import ch.citux.td.data.dto.TwitchGames;
import ch.citux.td.data.dto.TwitchStream;
import ch.citux.td.data.dto.TwitchVideos;
import ch.citux.td.data.model.Base;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.Response;
import ch.citux.td.data.model.SearchChannels;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamQuality;
import ch.citux.td.data.model.StreamToken;
import ch.citux.td.data.model.Streams;
import ch.citux.td.data.model.TopGames;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.worker.TDRequestHandler;
import ch.citux.td.util.DtoMapper;
import ch.citux.td.util.Log;

public class TDServiceImpl implements TDService {

    private static final String TAG = "TDService";

    private static TDServiceImpl instance;

    private Gson gson;
    private Gson jGson;

    private TDServiceImpl() {
        gson = new Gson();
        jGson = new GsonBuilder().setDateFormat("y-M-d H:m:s 'UTC'").create(); //2013-01-19 02:49:36 UTC
    }

    public static TDServiceImpl getInstance() {
        if (instance == null) {
            instance = new TDServiceImpl();
        }
        return instance;
    }

    private String buildUrl(String base, Object... params) {
        ArrayList<String> args = new ArrayList<String>(params.length);

        for (Object param : params) {
            try {
                String arg = param.toString();
                arg = URLEncoder.encode(arg, TDConfig.UTF_8);
                args.add(arg);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e);
            } catch (NullPointerException e) {
                Log.e(TAG, e);
            }
        }
        return MessageFormat.format(base, args.toArray());
    }

    private String startStringRequest(String url, Base result) {
        RequestHandler requestHandler = new RequestHandler();
        return requestHandler.startStringRequest(url, result);
    }

    @Override
    public Follows getFollows(String username) {
        Follows result = new Follows();
        String url = buildUrl(TDConfig.URL_API_GET_FOLLOWS, username);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchFollows twitchFollows = gson.fromJson(response, TwitchFollows.class);
            if (twitchFollows != null && twitchFollows.getFollows() != null) {
                result.setChannels(DtoMapper.mapTwitchChannels(twitchFollows.getFollows()));
            }
        }
        return result;
    }

    @Override
    public Channel getChannel(String channel) {
        Channel result = new Channel();
        String url = buildUrl(TDConfig.URL_API_GET_CHANNEL, channel);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchChannel twitchChannel = gson.fromJson(response, TwitchChannel.class);
            if (twitchChannel != null) {
                result = DtoMapper.mapChannel(twitchChannel);
            }
        }
        return result;
    }

    @Override
    public Stream getStream(String channel) {
        Stream result = new Stream();
        String url = buildUrl(TDConfig.URL_API_GET_STREAM, channel);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchStream twitchStream = gson.fromJson(response, TwitchStream.class);
            if (twitchStream != null && (twitchStream.getStream() != null || twitchStream.getStreams() != null)) {
                result = DtoMapper.mapStream(twitchStream.getStream());
            }
        }
        return result;
    }

    @Override
    public Streams getStreams(String game, String offset) {
        Streams result = new Streams();
        String url = buildUrl(TDConfig.URL_API_GET_STREAMS, game, offset);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchStream twitchStream = gson.fromJson(response, TwitchStream.class);
            if (twitchStream != null && (twitchStream.getStream() != null || twitchStream.getStreams() != null)) {
                result = DtoMapper.mapStreams(twitchStream);
            }
        }
        return result;
    }

    @Override
    public Videos getVideos(String channel, String offset) {
        Videos result = new Videos();
        String url = buildUrl(TDConfig.URL_API_GET_VIDEOS, channel, offset);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchVideos videos = gson.fromJson(response, TwitchVideos.class);
            if (videos != null) {
                result.setVideos(DtoMapper.mapVideos(videos));
            }
        }
        return result;
    }

    @Override
    public VideoPlaylist getVideoPlaylist(String id) {
        VideoPlaylist result = new VideoPlaylist();
        String url = buildUrl(TDConfig.URL_API_GET_VIDEO, id);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            List<JustinArchive> archives = jGson.fromJson(response, new TypeToken<List<JustinArchive>>() {
            }.getType());
            if (archives != null) {
                result = (DtoMapper.mapVideo(archives));
            }
        }
        return result;
    }

    @Override
    public StreamToken getStreamToken(String channel) {
        StreamToken result = new StreamToken();
        String url = buildUrl(TDConfig.URL_API_GET_STREAM_TOKEN, channel);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchAccessToken accessToken = gson.fromJson(response, TwitchAccessToken.class);
            if (accessToken != null && accessToken.getToken() != null && accessToken.getSig() != null) {
                result.setNauth(accessToken.getToken());
                result.setNauthsig(accessToken.getSig());
                result.setP((int) (Math.random() * 999999));
            }
        }
        return result;
    }

    @Override
    public StreamPlayList getStreamPlaylist(String channel, StreamToken streamToken) {
        StreamPlayList result = new StreamPlayList();
        String url = buildUrl(TDConfig.URL_API_GET_STREAM_PLAYLIST, channel, streamToken.getP(), streamToken.getNauth(), streamToken.getNauthsig());
        Response<Playlist> response = TDRequestHandler.startPlaylistRequest(url);
        if (response.getStatus() == Response.Status.OK) {
            List<Element> elements = response.getResult().getElements();
            if (elements.size() > 0) {
                HashMap<StreamQuality, String> streams = new HashMap<StreamQuality, String>();
                for (Element element : elements) {
                    Log.d(TAG, "URI: " + element.getURI() + " Name: " + element.getName());
                    StreamQuality quality = StreamPlayList.parseQuality(element.getName());
                    if (quality != null) {
                        streams.put(quality, element.getURI().toString());
                    }
                }
                result.setStreams(streams);
            }
        } else {
            result.setErrorResId(R.string.error_data_error_message);
        }
        return result;
    }

    @Override
    public SearchStreams searchStreams(String query, String offset) {
        SearchStreams result = new SearchStreams();
        String url = buildUrl(TDConfig.URL_API_SEARCH_STREAMS, query, offset);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchStream searchStreams = jGson.fromJson(response, TwitchStream.class);
            if (searchStreams != null) {
                result = (DtoMapper.mapSearchStreams(searchStreams));
            }
        }
        return result;
    }

    @Override
    public SearchChannels searchChannels(String query, String offset) {
        SearchChannels result = new SearchChannels();
        String url = buildUrl(TDConfig.URL_API_SEARCH_CHANNELS, query, offset);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchChannels searchStreams = jGson.fromJson(response, TwitchChannels.class);
            if (searchStreams != null) {
                result = (DtoMapper.mapSearchChannels(searchStreams));
            }
        }
        return result;
    }

    @Override
    public TopGames getTopGames(String limit, String offset) {
        TopGames result = new TopGames();
        String url = buildUrl(TDConfig.URL_API_GET_TOP_GAMES, limit, offset);
        String response = startStringRequest(url, result);
        if (StringUtils.isNotEmpty(response)) {
            TwitchGames twitchGames = gson.fromJson(response, TwitchGames.class);
            result = DtoMapper.mapGames(twitchGames);
        }
        return result;
    }

    private class RequestHandler {

        private static final int MAX_RETRY_COUNT = 5;

        private int retryCount;

        private String startStringRequest(String url, Base result) {
            Response<String> response = TDRequestHandler.startStringRequest(url);
            if (response.getStatus() == Response.Status.OK) {
                return response.getResult();
            }
            if (response.getStatus() == Response.Status.ERROR_TIMEOUT) {
                if (++retryCount <= MAX_RETRY_COUNT) {
                    return startStringRequest(url, result);
                }
            }
            result.setErrorResId(R.string.error_data_error_message);
            return "";
        }
    }
}