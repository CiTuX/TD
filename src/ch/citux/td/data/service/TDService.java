package ch.citux.td.data.service;

import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamToken;
import ch.citux.td.data.model.Videos;

public interface TDService {

    public Follows getFollows(String username);

    public Channel getChannel(String channel);

    public Stream getStream(String channel);

    public Videos getVideos(String channel);

    public StreamToken getStreamToken(String channel);

    public StreamPlayList getStreamPlaylist(String channel, String token, String hd);

}
