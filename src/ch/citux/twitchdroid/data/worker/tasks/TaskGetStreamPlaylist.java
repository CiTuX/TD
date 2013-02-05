package ch.citux.twitchdroid.data.worker.tasks;

import android.util.Log;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.config.TwitchConfig;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.model.StreamToken;
import ch.citux.twitchdroid.data.service.TwitchDroidServiceImpl;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import ch.citux.twitchdroid.util.HashUtils;

public class TaskGetStreamPlaylist extends TwitchDroidTask<String, StreamPlayList> {


    public TaskGetStreamPlaylist(TwitchDroidCallback<StreamPlayList> callback) {
        super(callback);
    }

    @Override
    protected StreamPlayList doInBackground(String... params) {
        if (params.length == 2) {
            StreamToken streamToken = TwitchDroidServiceImpl.getInstance().getStreamToken(params[0]);
            if (streamToken.getToken() != null) {
                String token = HashUtils.decodeJSON(streamToken.getToken());
                String hash = HashUtils.encodeHmacSHA1(TwitchConfig.USHER_STREAM_TOKEN, token);
                token = HashUtils.encodeURL(hash + ":" + token);
//                token = hash + ":" + token;
                Log.d("Twitch", token);
                return TwitchDroidServiceImpl.getInstance().getStreamPlaylist(params[0], token, params[1]);
            }
            //NOT ONLINE
        }
        StreamPlayList playList = new StreamPlayList();
        playList.setErrorResId(R.string.error_unexpected);
        return playList;
    }
}
