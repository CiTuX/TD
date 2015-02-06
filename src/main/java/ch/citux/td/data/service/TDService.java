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

import ch.citux.td.data.model.TwitchAccessToken;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchChannels;
import ch.citux.td.data.model.TwitchFollows;
import ch.citux.td.data.model.TwitchGames;
import ch.citux.td.data.model.TwitchStream;
import ch.citux.td.data.model.TwitchVideos;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TDService {

    public interface TwitchKraken {

        @GET("/users/{username}/follows/channels?limit=25")
        public TwitchFollows getFollows(@Path("username") String username, @Query("offset") int offset);

        @GET("/channels/{channel}")
        public TwitchChannel getChannel(@Path("channel") String channel);

        @GET("/streams/{channel}")
        public TwitchStream getStream(@Path("channel") String channel);

        @GET("/streams?limit=20")
        public TwitchStream getStreams(@Query("game") String game, @Query("offset") int offset);

        @GET("/channels/{channel}/videos?limit=10&broadcasts=true")
        public TwitchVideos getVideos(@Path("channel") String channel, @Query("offset") int offset);

        @GET("/search/streams?limit=20")
        public TwitchStream searchStreams(@Query("query") String query, @Query("offset") int offset);

        @GET("/search/channels?limit=20")
        public TwitchChannels searchChannels(@Query("query") String query, @Query("offset") int offset);

        @GET("/games/top")
        public TwitchGames getTopGames(@Query("limit") int limit, @Query("offset") int offset);
    }

    public interface TwitchAPI {

        @GET("/channels/{channel}/access_token")
        public TwitchAccessToken getChannelToken(@Path("channel") String channel);

        @GET("/vods/{videoId}/access_token")
        public TwitchAccessToken getVodToken(@Path("videoId") String videoId);

        @GET("/videos/{id}?as3=t")
        public TwitchBroadcast getVideoPlaylist(@Path("id") String id);
    }

    public interface TwitchUsher {

        @GET("/api/channel/hls/{channel}.json?allow_source=true&allow_audio_only=false&type=any&player=twitchweb")
        public Response getChannelPlaylist(@Path("channel") String channel, @Query("p") String p, @Query("token") String token, @Query("sig") String sig);

        @GET("/vod/{videoId}?allow_source=true&allow_audio_only=false&type=any&player=twitchweb")
        public Response getVodPlaylist(@Path("videoId") String videoId, @Query("p") String p, @Query("nauth") String token, @Query("nauthsig") String sig);
    }
}
