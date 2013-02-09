package ch.citux.twitchdroid.data.service;

import ch.citux.twitchdroid.data.dto.UsherStreamToken;
import ch.citux.twitchdroid.data.model.*;

public interface TDService {

    public Favorites getFavorites(String username);

    public Channel getChannel(String channel);

    public Stream getStream(String channel);

    public StreamToken getStreamToken(String channel);

    public StreamPlayList getStreamPlaylist(String channel, String token, String hd);

}
