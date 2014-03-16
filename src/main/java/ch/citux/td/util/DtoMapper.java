package ch.citux.td.util;

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

    public static ArrayList<Channel> mapTwitchChannels(List<TwitchChannels> tChannels) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (TwitchChannels tChannel : tChannels) {
            channels.add(mapChannel(tChannel.getChannel()));
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