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

import ch.citux.td.data.dto.JustinArchive;
import ch.citux.td.data.dto.TwitchChannel;
import ch.citux.td.data.dto.TwitchChannels;
import ch.citux.td.data.dto.TwitchStream;
import ch.citux.td.data.dto.TwitchStreamElement;
import ch.citux.td.data.dto.TwitchVideo;
import ch.citux.td.data.dto.TwitchVideos;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.SearchChannels;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.VideoPlaylist;

public class DtoMapper {

    private static final String PREVIEW_WIDTH_KEY = "{width}";
    private static final String PREVIEW_WIDTH_SIZE = "320";
    private static final String PREVIEW_HEIGHT_KEY = "{height}";
    private static final String PREVIEW_HEIGHT_SIZE = "240";

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
        video.setId(tVideo.get_id().split("\\D+")[1]);
        video.setDuration(tVideo.getLength());
        video.setDate(tVideo.getRecorded_at());
        video.setThumbnail(tVideo.getPreview());
        video.setTitle(tVideo.getTitle() == null ? "Untitled" : tVideo.getTitle());
        video.setUrl(tVideo.getUrl());
        return video;
    }

    public static VideoPlaylist mapVideo(List<JustinArchive> jArchive) {
        VideoPlaylist playlist = new VideoPlaylist();
        ArrayList<Video> videos = new ArrayList<Video>();
        String title = "";
        Video video;

        for (JustinArchive archive : jArchive) {
            title = archive.getTitle();
            video = new Video();
            video.setTitle(title);
            video.setUrl(archive.getVideo_file_url());
            video.setDuration(archive.getLength());
            video.setDate(archive.getCreated_on());
            videos.add(video);
        }
        playlist.setTitle(title);
        playlist.setVideos(videos);

        return playlist;
    }

    public static Stream mapStream(TwitchStreamElement streamElement) {
        Stream stream = new Stream();

        if (streamElement != null) {
            stream.setId(streamElement.get_id());
            stream.setChannel(mapChannel(streamElement.getChannel()));
            stream.setChannelId(streamElement.getChannel_id());
            stream.setGame(streamElement.getGame());
            stream.setName(streamElement.getName());
            stream.setStatus(streamElement.getChannel().getStatus());
            stream.setViewers(streamElement.getViewers());

            String thumbnailUrl = streamElement
                    .getPreview()
                    .getTemplate()
                    .replace(PREVIEW_WIDTH_KEY, PREVIEW_WIDTH_SIZE)
                    .replace(PREVIEW_HEIGHT_KEY, PREVIEW_HEIGHT_SIZE);
            stream.setThumbnail(thumbnailUrl);
        }
        return stream;
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
}