package ch.citux.td.data.service;

import ch.citux.td.data.model.Archives;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Favorites;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamToken;

public interface TDService {

    public Favorites getFavorites(String username);

    public Channel getChannel(String channel);

    public Stream getStream(String channel);

    public Archives getArchives(String channel);

    public StreamToken getStreamToken(String channel);

    public StreamPlayList getStreamPlaylist(String channel, String token, String hd);

}
