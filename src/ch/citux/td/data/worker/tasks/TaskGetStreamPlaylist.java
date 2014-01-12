package ch.citux.td.data.worker.tasks;

import android.util.Log;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamToken;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.util.HashUtils;

public class TaskGetStreamPlaylist extends TDTask<String, StreamPlayList> {


    public TaskGetStreamPlaylist(TDCallback<StreamPlayList> callback) {
        super(callback);
    }

    @Override
    protected StreamPlayList doInBackground(String... params) {
        StreamPlayList result = new StreamPlayList();
        if (params.length == 2) {
            StreamToken streamToken = TDServiceImpl.getInstance().getStreamToken(params[0]);
            if (streamToken.getToken() != null) {
                String token = HashUtils.decodeJSON(streamToken.getToken());
                String hash = HashUtils.encodeHmacSHA1(TDConfig.USHER_STREAM_TOKEN, token);
                token = HashUtils.encodeURL(hash + ":" + token);
                Log.d("Twitch", token);
                return TDServiceImpl.getInstance().getStreamPlaylist(params[0], token, params[1]);
            } else {
                result.setError(streamToken.getError());
                return result;
            }
        }
        result.setErrorResId(R.string.error_unexpected);
        return result;
    }
}
