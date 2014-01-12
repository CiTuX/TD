package ch.citux.td.util;

import java.util.ArrayList;
import java.util.List;

import ch.citux.td.data.dto.JustinArchive;
import ch.citux.td.data.dto.JustinChannel;
import ch.citux.td.data.dto.TwitchChannel;
import ch.citux.td.data.dto.TwitchStream;
import ch.citux.td.data.dto.TwitchStreamElement;
import ch.citux.td.data.model.Archive;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.Stream;

public class DtoMapper {

    public static Channel mapChannel(JustinChannel jChannel) {
        Channel channel = new Channel();
        channel.setId(jChannel.getId());
        channel.setName(jChannel.getLogin());
        channel.setLogos(readLogos(jChannel.getImage_url_medium()));
        channel.setTitle(jChannel.getTitle());
        return channel;
    }

    public static Channel mapChannel(TwitchChannel tChannel) {
        Channel channel = new Channel();
        channel.setId(tChannel.get_id());
        channel.setName(tChannel.getName());
        channel.setLogos(readLogos(tChannel.getLogo()));
        channel.setTitle(tChannel.getDisplay_name());
        return channel;
    }

    public static Archive mapArchive(JustinArchive jArchive) {
        Archive archive = new Archive();
        archive.setId(jArchive.getId());
        archive.setDuration(jArchive.getLength());
        archive.setDate(jArchive.getCreated_on());
        archive.setSize(jArchive.getFile_size());
        archive.setThumbnail(jArchive.getImage_url_medium());
        archive.setTitle(jArchive.getTitle() == null ? "Untitled" : jArchive.getTitle());
        archive.setUrl(jArchive.getVideo_file_url());
        return archive;
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

    public static ArrayList<Channel> mapJustinChannels(ArrayList<JustinChannel> jChannels) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (JustinChannel jChannel : jChannels) {
            channels.add(mapChannel(jChannel));
        }
        return channels;
    }

    public static ArrayList<Channel> mapTwitchChannels(ArrayList<TwitchChannel> tChannels) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (TwitchChannel tChannel : tChannels) {
            channels.add(mapChannel(tChannel));
        }
        return channels;
    }

    public static ArrayList<Archive> mapArchives(List<JustinArchive> jArchives) {
        ArrayList<Archive> archives = new ArrayList<Archive>();
        for (JustinArchive archive : jArchives) {
            archives.add(mapArchive(archive));
        }
        return archives;
    }
}
