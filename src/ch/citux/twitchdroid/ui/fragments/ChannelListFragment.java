package ch.citux.twitchdroid.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import ch.citux.twitchdroid.data.worker.TwitchDroidTaskManager;
import com.actionbarsherlock.app.SherlockListFragment;
import com.yixia.zi.utils.Log;

public class ChannelListFragment extends SherlockListFragment implements TwitchDroidCallback<Favorites> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TwitchDroidTaskManager.getFavorites(this, "citux_ch");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResponse(Favorites response) {
        Log.d("RESULT", response.getChannels().get(0).getId());
    }

    @Override
    public void onError(String title, String message) {
    }
}
