package ch.citux.twitchdroid.util;

import ch.citux.twitchdroid.data.dto.JustinChannel;
import ch.citux.twitchdroid.data.dto.TwitchChannel;
import ch.citux.twitchdroid.data.model.Channel;

import java.util.ArrayList;

public class DtoMapper {

    public static Channel mapChannel(JustinChannel jChannel) {
        Channel channel = new Channel();
        channel.setChannel_name(jChannel.getLogin());
        channel.setLogo_url(jChannel.getImage_url_small());
        channel.setTitle(jChannel.getTitle());
        return channel;
    }

    public static Channel mapChannel(TwitchChannel tChannel) {
        Channel channel = new Channel();
        channel.setChannel_name(tChannel.getName());
        channel.setLogo_url(changeQuality(tChannel.getLogo()));
        channel.setTitle(tChannel.getTitle());
        return channel;
    }

    private static String changeQuality(String hqLogo) {
        if (hqLogo != null) {
            return hqLogo.replaceAll("-(\\d+)x(\\d+).png", "-70x70.png");
        }
        return hqLogo;
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

}
