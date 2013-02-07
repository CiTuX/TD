package ch.citux.twitchdroid.util;

import ch.citux.twitchdroid.data.dto.JustinChannel;
import ch.citux.twitchdroid.data.dto.TwitchChannel;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Logo;

import java.util.ArrayList;

public class DtoMapper {

    public static Channel mapChannel(JustinChannel jChannel) {
        Channel channel = new Channel();
        channel.setChannel_name(jChannel.getLogin());
        channel.setLogos(readLogos(jChannel.getImage_url_medium()));
        channel.setTitle(jChannel.getTitle());
        return channel;
    }

    public static Channel mapChannel(TwitchChannel tChannel) {
        Channel channel = new Channel();
        channel.setChannel_name(tChannel.getName());
        channel.setLogos(readLogos(tChannel.getLogo()));
        channel.setTitle(tChannel.getTitle());
        return channel;
    }

    private static ArrayList<Logo> readLogos(String hqLogo) {
        ArrayList<Logo> logos = new ArrayList<Logo>();
        for (String size : Logo.SIZES) {
            logos.add(new Logo(hqLogo.replaceAll("-(\\d+)x(\\d+)", size), size));
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

}
