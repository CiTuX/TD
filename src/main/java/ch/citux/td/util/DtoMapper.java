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
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.Video;

public class DtoMapper {

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

    public static Video mapVideo(List<JustinArchive> jArchive) {
        JustinArchive jVideo = jArchive.get(0); // TODO all videos
        Video video = new Video();
        video.setId(jVideo.getId());
        video.setUrl(jVideo.getVideo_file_url());
        return video;
    }

    public static Stream mapStream(TwitchStream tStream) {
        Stream stream = new Stream();
        TwitchStreamElement streamElement = null;

        if (tStream.getStream() != null) {
            streamElement = tStream.getStream();
        } else if (tStream.getStreams() != null) {
            streamElement = tStream.getStreams().get(0);
        }

        if (streamElement != null) {
            stream.setId(streamElement.get_id());
            stream.setChannel(mapChannel(streamElement.getChannel()));
            stream.setChannelId(streamElement.getChannel_id());
            stream.setGame(streamElement.getGame());
            stream.setName(streamElement.getName());
            stream.setStatus(streamElement.getStatus());
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
}