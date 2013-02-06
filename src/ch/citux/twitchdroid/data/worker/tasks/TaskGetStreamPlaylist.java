package ch.citux.twitchdroid.data.worker.tasks;

import android.util.Log;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.config.TDConfig;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.model.StreamToken;
import ch.citux.twitchdroid.data.service.TDServiceImpl;
import ch.citux.twitchdroid.data.worker.TDCallback;
import ch.citux.twitchdroid.util.HashUtils;

public class TaskGetStreamPlaylist extends TwitchDroidTask<String, StreamPlayList> {


    public TaskGetStreamPlaylist(TDCallback<StreamPlayList> callback) {
        super(callback);
    }

    @Override
    protected StreamPlayList doInBackground(String... params) {
        if (params.length == 2) {
            StreamToken streamToken = TDServiceImpl.getInstance().getStreamToken(params[0]);
            if (streamToken.getToken() != null) {
                String token = HashUtils.decodeJSON(streamToken.getToken());
                String hash = HashUtils.encodeHmacSHA1(TDConfig.USHER_STREAM_TOKEN, token);
                token = HashUtils.encodeURL(hash + ":" + token);
//                token = hash + ":" + token;
                Log.d("Twitch", token);
                return TDServiceImpl.getInstance().getStreamPlaylist(params[0], token, params[1]);
            }
            //NOT ONLINE
        }
        StreamPlayList playList = new StreamPlayList();
        playList.setErrorResId(R.string.error_unexpected);
        return playList;
    }
}
