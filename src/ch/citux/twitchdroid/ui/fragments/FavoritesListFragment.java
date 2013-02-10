package ch.citux.twitchdroid.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.config.TDConfig;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.model.Status;
import ch.citux.twitchdroid.data.worker.TDBasicCallback;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.adapter.FavoritesAdapter;
import ch.citux.twitchdroid.ui.widget.EmptyView;
import com.yixia.zi.utils.StringUtils;

public class FavoritesListFragment extends TDFragment<Favorites> implements AdapterView.OnItemClickListener {

    private String channelName;
    private SharedPreferences preferences;
    private FavoritesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        adapter = new FavoritesAdapter(getActivity());
        EmptyView emptyView = (EmptyView) getListView().getEmptyView();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        if (!preferences.contains(TDConfig.SETTINGS_CHANNEL_NAME)) {
            emptyView.setText(R.string.channel_name_empty);
        }
        loadData();
    }

    @Override
    public void loadData() {
        channelName = preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, "");
        if (!StringUtils.isBlank(channelName)) {
            TDTaskManager.getFavorites(this, channelName);
        }
    }

    @Override
    public void refreshData() {
        if (!channelName.equals(preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, channelName))) {
            //Channel change, reload favorites
            loadData();
        } else {
            //Only status update
            for (Channel channel : adapter.getData()) {
                channel.setStatus(Status.UNKNOWN);
                adapter.updateChannel(channel);
                TDTaskManager.getStatus(new ChannelCallback(this), channel.getName());
            }
        }
    }

    @Override
    public void onResponse(Favorites response) {
        if (adapter == null) {
            adapter = new FavoritesAdapter(getActivity(), response.getChannels());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getChannels());
        }
        for (Channel channel : response.getChannels()) {
            TDTaskManager.getStatus(new ChannelCallback(this), channel.getName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private class ChannelCallback extends TDBasicCallback<Channel> {

        protected ChannelCallback(Object caller) {
            super(caller);
        }

        @Override
        public void onResponse(Channel response) {
            adapter.updateChannel(response);
        }
    }
}
