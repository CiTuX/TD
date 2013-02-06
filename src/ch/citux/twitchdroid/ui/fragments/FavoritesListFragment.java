package ch.citux.twitchdroid.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.worker.TDCallback;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.adapter.FavoritesAdapter;
import com.actionbarsherlock.app.SherlockListFragment;

public class FavoritesListFragment extends SherlockListFragment implements TDCallback<Favorites>, AdapterView.OnItemClickListener {

    private FavoritesAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        getListView().setOnItemClickListener(this);
    }

    private void loadData() {
        TDTaskManager.getFavorites(this, "jackfrags");
    }

    @Override
    public void onResponse(Favorites response) {
        adapter = new FavoritesAdapter(getActivity(), response.getChannels());
        setListAdapter(adapter);
    }

    @Override
    public void onError(String title, String message) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TDTaskManager.getChannel(new ChannelCallback(), adapter.getItem(position).getChannel_name());
    }

    private class ChannelCallback implements TDCallback<Channel> {

        @Override
        public void onResponse(Channel response) {
        }

        @Override
        public void onError(String title, String message) {
        }
    }

}
