package ch.citux.twitchdroid.data.service;

import ch.citux.twitchdroid.data.model.*;

public interface TwitchDroidService {

    public Favorites getFavorites(String username);

    public ChannelStatus getChannelStatus(String channel);

    public StreamToken getStreamToken(String channel);

    public StreamPlayList getStreamPlaylist(String channel, String token, String hd);

}
