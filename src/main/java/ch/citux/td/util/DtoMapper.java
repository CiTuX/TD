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
package ch.citux.td.util;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import ch.citux.td.data.dto.TwitchBroadcast;
import ch.citux.td.data.dto.TwitchChannel;
import ch.citux.td.data.dto.TwitchChannels;
import ch.citux.td.data.dto.TwitchChunk;
import ch.citux.td.data.dto.TwitchGame;
import ch.citux.td.data.dto.TwitchGames;
import ch.citux.td.data.dto.TwitchGamesElement;
import ch.citux.td.data.dto.TwitchStream;
import ch.citux.td.data.dto.TwitchStreamElement;
import ch.citux.td.data.dto.TwitchVideo;
import ch.citux.td.data.dto.TwitchVideos;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Game;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.SearchChannels;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.Streams;
import ch.citux.td.data.model.TopGames;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.VideoPlaylist;

public class DtoMapper {

    private static final String PREVIEW_WIDTH_KEY = "{width}";
    private static final String PREVIEW_HEIGHT_KEY = "{height}";

    private static final int PREVIEW_WIDTH_SIZE = 320;
    private static final int PREVIEW_HEIGHT_SIZE = 240;
    private static final int BOX_WIDTH_SIZE = 272;
    private static final int BOX_HEIGHT_SIZE = 380;

    private static String makeImageURL(String template, int width, int height) {
        return template
                .replace(PREVIEW_WIDTH_KEY, String.valueOf(width))
                .replace(PREVIEW_HEIGHT_KEY, String.valueOf(height));
    }

    public static Channel mapChannel(TwitchChannel tChannel) {
        Channel channel = new Channel();
        channel.setId(tChannel.get_id());
        channel.setName(tChannel.getName());
        channel.setLogos(readLogos(tChannel.getLogo()));
        channel.setTitle(tChannel.getDisplay_name());
        return channel;
    }

    public static Video mapVideo(TwitchVideo tVideo) {
        Video video = new Video();
        video.setId(tVideo.get_id());
        video.setDuration(tVideo.getLength());
        video.setDate(tVideo.getRecorded_at());
        video.setThumbnail(tVideo.getPreview());
        video.setTitle(tVideo.getTitle() == null ? "Untitled" : tVideo.getTitle());
        video.setUrl(tVideo.getUrl());
        return video;
    }

    public static VideoPlaylist mapVideo(TwitchBroadcast twitchBroadcast) {
        VideoPlaylist playlist = new VideoPlaylist();
        ArrayList<Video> videos = new ArrayList<Video>();
        Video video;

        if (twitchBroadcast.getChunks() != null) {
            for (TwitchChunk chunk : twitchBroadcast.getChunks().getLive()) {
                video = new Video();
                video.setUrl(chunk.getUrl());
                video.setDuration(chunk.getLength());
                video.setThumbnail(twitchBroadcast.getPreview());
                video.setId(twitchBroadcast.getApi_id());
                video.setSize(chunk.getLength());
                videos.add(video);
            }
            playlist.setVideos(videos);
        }
        return playlist;
    }

    public static Stream mapStream(TwitchStreamElement streamElement) {
        Stream stream = new Stream();

        if (streamElement != null) {
            stream.setId(streamElement.get_id());
            stream.setChannel(mapChannel(streamElement.getChannel()));
            stream.setChannelId(streamElement.getChannel_id());
            stream.setGame(streamElement.getGame());
            stream.setStatus(streamElement.getChannel().getStatus());
            stream.setViewers(streamElement.getViewers());
            stream.setThumbnail(makeImageURL(streamElement.getPreview().getTemplate(), PREVIEW_WIDTH_SIZE, PREVIEW_HEIGHT_SIZE));
            if (stream.getChannel() != null) {
                stream.setName(stream.getChannel().getName());
            }
        }
        return stream;
    }

    public static Streams mapStreams(TwitchStream twitchStream) {
        Streams result = new Streams();

        if (twitchStream != null && twitchStream.getStreams() != null) {
            ArrayList<Stream> streams = new ArrayList<Stream>(twitchStream.getStreams().size());
            for (TwitchStreamElement element : twitchStream.getStreams()) {
                streams.add(mapStream(element));
            }
            result.setEntries(streams);
        }
        return result;
    }

    private static ArrayList<Logo> readLogos(String hqLogo) {
        ArrayList<Logo> logos = new ArrayList<Logo>();
        if (hqLogo != null) {
            for (String size : Logo.SIZES) {
                logos.add(new Logo(hqLogo.replaceAll("-(\\d+)x(\\d+)", size), size));
            }
        }
        return logos;
    }

    public static SparseArray<Channel> mapTwitchChannels(List<TwitchChannels> tChannels) {
        SparseArray<Channel> channels = new SparseArray<Channel>();
        for (TwitchChannels tChannel : tChannels) {
            Channel channel = mapChannel(tChannel.getChannel());
            channels.put(channel.getId(), channel);
        }
        return channels;
    }

    public static ArrayList<Video> mapVideos(TwitchVideos tVideos) {
        ArrayList<Video> videos = new ArrayList<Video>();
        for (TwitchVideo video : tVideos.getVideos()) {
            videos.add(mapVideo(video));
        }
        return videos;
    }

    public static SearchStreams mapSearchStreams(TwitchStream tSearchStreams) {
        SearchStreams searchStreams = new SearchStreams();
        ArrayList<Stream> streams = new ArrayList<Stream>();
        for (TwitchStreamElement stream : tSearchStreams.getStreams()) {
            streams.add(mapStream(stream));
        }
        searchStreams.setResult(streams);
        return searchStreams;
    }

    public static SearchChannels mapSearchChannels(TwitchChannels tChannels) {
        SearchChannels searchChannels = new SearchChannels();
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (TwitchChannel channel : tChannels.getChannels()) {
            channels.add(mapChannel(channel));
        }
        searchChannels.setResult(channels);
        return searchChannels;
    }

    public static Game mapGame(TwitchGamesElement tGamesElement) {
        Game game = new Game();
        if (tGamesElement != null && tGamesElement.getGame() != null) {
            TwitchGame tGame = tGamesElement.getGame();
            game.setId(tGame.get_id());
            game.setName(tGame.getName());
            game.setChannels(tGamesElement.getChannels());
            game.setViewers(tGamesElement.getViewers());
            game.setBox(makeImageURL(tGame.getBox().getTemplate(), BOX_WIDTH_SIZE, BOX_HEIGHT_SIZE));
        }
        return game;
    }

    public static TopGames mapGames(TwitchGames tGames) {
        TopGames games = new TopGames();
        if (tGames != null) {
            ArrayList<Game> top = new ArrayList<Game>();
            for (TwitchGamesElement tGame : tGames.getTop()) {
                top.add(mapGame(tGame));
            }
            games.setGames(top);
            games.setTotal(tGames.get_total());
        }
        return games;
    }
}