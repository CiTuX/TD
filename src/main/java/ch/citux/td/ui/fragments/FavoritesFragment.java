/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.citux.td.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;

import org.apache.commons.lang3.StringUtils;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.Status;
import ch.citux.td.data.worker.TDBasicCallback;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.FavoritesAdapter;
import ch.citux.td.ui.widget.EmptyView;

public class FavoritesFragment extends TDFragment<Follows> implements AdapterView.OnItemClickListener {

    private String channelName;
    private SharedPreferences preferences;
    private FavoritesAdapter adapter;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (adapter == null || adapter.getCount() == 0) {
            adapter = new FavoritesAdapter(getActivity());

            setListAdapter(adapter);
            loadData();
        }

        if (!hasUsername) {
            EmptyView emptyView = (EmptyView) getListView().getEmptyView();
            if (emptyView != null) {
                emptyView.setText(R.string.channel_name_empty);
            }
        }
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void loadData() {
        channelName = preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, "");
        if (StringUtils.isNotBlank(channelName)) {
            TDTaskManager.getFavorites(this, channelName);
        }
    }

    @Override
    public void refreshData() {
        if (!channelName.equals(preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, channelName))) {
            //Channel change, reload favorites
            adapter.clear();
            loadData();
        } else {
            //Only status update
            for (int i = 0; i < adapter.getData().size(); i++) {
                Channel channel = adapter.getData().valueAt(i);
                channel.setStatus(Status.UNKNOWN);
                adapter.updateChannel(channel);
                TDTaskManager.getStatus(new ChannelCallback(channel.getId(), this), channel.getName());
            }
        }
    }

    @Override
    public void onResponse(Follows response) {
        if (adapter == null) {
            adapter = new FavoritesAdapter(getActivity(), response.getChannels());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getChannels());
        }
        for (int i = 0; i < response.getChannels().size(); i++) {
            Channel channel = response.getChannels().valueAt(i);
            TDTaskManager.getStatus(new ChannelCallback(channel.getId(), this), channel.getName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Channel channel = adapter.getItem(position);
        if (channel != null && channel.getStatus() != null) {
            getTDActivity().showChannel(channel);
        }
    }

    private class ChannelCallback extends TDBasicCallback<Channel> {

        private int id;

        protected ChannelCallback(int id, Object caller) {
            super(caller);
            this.id = id;
        }

        @Override
        public void onResponse(Channel response) {
            adapter.updateChannel(id, response.getStatus());
        }

        @Override
        public boolean isAdded() {
            return FavoritesFragment.this.isAdded();
        }
    }
}
