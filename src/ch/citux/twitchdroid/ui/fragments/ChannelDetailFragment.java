package ch.citux.twitchdroid.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import ch.citux.twitchdroid.data.worker.TwitchDroidTaskManager;
import com.actionbarsherlock.app.SherlockFragment;

public class ChannelDetailFragment extends SherlockFragment implements TwitchDroidCallback<StreamPlayList> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TwitchDroidTaskManager.getStreamPlaylist(this, "clg_premier_series", true);
        return inflater.inflate(R.layout.channel_detail, null);
    }

    @Override
    public void onResponse(StreamPlayList response) {
    }

    @Override
    public void onError(String title, String message) {
    }
}
