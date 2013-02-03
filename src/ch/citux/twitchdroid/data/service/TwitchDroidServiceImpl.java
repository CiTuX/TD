package ch.citux.twitchdroid.data.service;

import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Stream;
import ch.citux.twitchdroid.data.model.StreamToken;
import net.chilicat.m3u8.PlayList;

import java.util.ArrayList;

public class TwitchDroidServiceImpl implements TwitchDroidService {

    @Override
    public ArrayList<Channel> getFavorites(String username) {
        return null;
    }

    @Override
    public ArrayList<Stream> listStreams(String channel) {
        return null;
    }

    @Override
    public StreamToken getStreamToken(String channel) {
        return null;
    }

    @Override
    public PlayList getStreamPlaylist(String channel, String token, boolean hd) {
        return null;
    }
}
