package ch.citux.twitchdroid.data.service;

import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Stream;
import ch.citux.twitchdroid.data.model.StreamToken;
import net.chilicat.m3u8.PlayList;

import java.util.ArrayList;

public interface TwitchDroidService {

    public ArrayList<Channel> getFavorites(String username);

    public ArrayList<Stream> listStreams(String channel);

    public StreamToken getStreamToken(String channel);

    public PlayList getStreamPlaylist(String channel, String token, boolean hd);

}
