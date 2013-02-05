package ch.citux.twitchdroid.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import ch.citux.twitchdroid.data.worker.TwitchDroidTaskManager;
import ch.citux.twitchdroid.ui.adapter.FavoritesAdapter;
import com.actionbarsherlock.app.SherlockListFragment;

public class FavoritesListFragment extends SherlockListFragment implements TwitchDroidCallback<Favorites> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TwitchDroidTaskManager.getFavorites(this, "jackfrags");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResponse(Favorites response) {
        FavoritesAdapter adapter = new FavoritesAdapter(getActivity(), response.getChannels());
        setListAdapter(adapter);
//        for (Channel channel : response.getChannels()) {
//            Log.d("RESULT", "Channel: " + channel.getLogin());
//        }
    }

    @Override
    public void onError(String title, String message) {
    }
}
