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
import ch.citux.twitchdroid.ui.dialogs.InputDialogFragment;
import ch.citux.twitchdroid.ui.widget.EmptyView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.yixia.zi.utils.StringUtils;

public class FavoritesListFragment extends TDFragment<Favorites> implements
        AdapterView.OnItemClickListener,
        InputDialogFragment.OnDoneListener {

    private SharedPreferences preferences;
    private FavoritesAdapter adapter;
    private EmptyView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addRefreshAction();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        adapter = new FavoritesAdapter(getActivity());
        emptyView = (EmptyView) getListView().getEmptyView();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        if (!preferences.contains(TDConfig.SETTINGS_CHANNEL_NAME)) {
            emptyView.setText(R.string.channel_name_empty);
        }
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            InputDialogFragment.InputDialogFragmentBuilder builder
                    = new InputDialogFragment.InputDialogFragmentBuilder(getActivity());
            builder.setTitle(R.string.action_settings)
                    .setHint(R.string.channel_name)
                    .setText(preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, ""))
                    .setOnDoneListener(this)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void loadData() {
        String channel_name = preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, "");
        if (!StringUtils.isBlank(channel_name)) {
            TDTaskManager.getFavorites(this, channel_name);
        }
    }

    @Override
    protected void refreshData() {
        for (Channel channel : adapter.getData()) {
            channel.setStatus(Status.UNKNOWN);
            adapter.updateChannel(channel);
            TDTaskManager.getStatus(new ChannelCallback(this), channel.getName());
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

    @Override
    public void onFinishInputDialog(String inputText) {
        preferences.edit().putString(TDConfig.SETTINGS_CHANNEL_NAME, inputText).apply();
        refreshData();
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
