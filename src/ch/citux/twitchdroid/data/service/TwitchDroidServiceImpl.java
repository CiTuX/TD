package ch.citux.twitchdroid.data.service;

import android.util.Log;
import ch.citux.twitchdroid.config.TwitchConfig;
import ch.citux.twitchdroid.data.model.*;
import ch.citux.twitchdroid.data.worker.TwitchDroidRequestHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sourceforge.jplaylistparser.playlist.Playlist;
import net.sourceforge.jplaylistparser.playlist.PlaylistEntry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TwitchDroidServiceImpl implements TwitchDroidService {

    private static final String TAG = "TwitchDroidService";

    private static TwitchDroidServiceImpl instance;

    private Gson gson;

    private TwitchDroidServiceImpl() {
        gson = new Gson();
    }

    public static TwitchDroidServiceImpl getInstance() {
        if (instance == null) {
            instance = new TwitchDroidServiceImpl();
        }
        return instance;
    }

    private String buildUrl(String base, Object... params) {
        return MessageFormat.format(base, params);
    }

    @Override
    public Favorites getFavorites(String username) {
        Favorites result = new Favorites();
        String url = buildUrl(TwitchConfig.URL_API_GET_FAVORITES, username);
        Response<String> response = TwitchDroidRequestHandler.startStringRequest(url);
        if (response.getStatus() == Response.Status.OK) {
            ArrayList<Channel> channels = gson.fromJson(response.getResult(), new TypeToken<ArrayList<Channel>>() {
            }.getType());
            result.setChannels(channels);
        }
        return result;
    }

    @Override
    public ChannelStatus getChannelStatus(String channel) {
        return null;
    }

    @Override
    public StreamToken getStreamToken(String channel) {
        StreamToken result = new StreamToken();
        String url = buildUrl(TwitchConfig.URL_API_GET_STREAM_TOKEN, channel);
        Response<String> response = TwitchDroidRequestHandler.startStringRequest(url);
        if (response.getStatus() == Response.Status.OK) {
            result = gson.<ArrayList<StreamToken>>fromJson(response.getResult(), new TypeToken<ArrayList<StreamToken>>() {
            }.getType()).get(0);
        }
        return result;
    }

    @Override
    public StreamPlayList getStreamPlaylist(String channel, String token, String hd) {
        StreamPlayList result = new StreamPlayList();
        String url = buildUrl(TwitchConfig.URL_API_GET_STREAM_PLAYLIST, channel, token, hd);
        Response<Playlist> response = TwitchDroidRequestHandler.startPlaylistRequest(url);
        if (response.getStatus() == Response.Status.OK) {
            List<PlaylistEntry> entries = response.getResult().getPlaylistEntries();
            if (entries.size() > 0) {
                for (PlaylistEntry entry : entries) {
                    Log.d(TAG, entry.toString());
                }
            }
        }
        return null;
    }
}
