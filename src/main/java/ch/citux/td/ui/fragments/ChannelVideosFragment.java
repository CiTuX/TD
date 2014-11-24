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

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import ch.citux.td.R;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchVideo;
import ch.citux.td.data.model.TwitchVideos;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.ArchiveAdapter;
import ch.citux.td.ui.widget.ListView;
import ch.citux.td.util.VideoPlayer;

public class ChannelVideosFragment extends TDListFragment<TwitchVideos> implements AdapterView.OnItemClickListener, ListView.OnLastItemVisibleListener {

    private ArchiveAdapter adapter;
    private TwitchChannel channel;
    private int offset;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ChannelFragment.CHANNEL)) {
            channel = (TwitchChannel) getArguments().getSerializable(ChannelFragment.CHANNEL);
        }

        setOnItemClickListener(this);
        setOnLastItemVisibleListener(this);
    }

    public void setChannel(TwitchChannel channel) {
        this.channel = channel;
    }

    @Override
    public void refreshData() {
        if (adapter != null) {
            adapter.clear();
        }
        loadData();
    }

    @Override
    public void loadData() {
        if (channel != null) {
            TDTaskManager.executeTask(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TwitchVideo video = adapter.getItem(position);
        TDTaskManager.executeTask(new VideoPlayer.GetVideoCallback(this, video));
    }

    @Override
    public void onLastItemVisible() {
        offset += 10;
        loadData();
    }

    @Override
    public TwitchVideos startRequest() {
        return TDServiceImpl.getInstance().getVideos(channel.getName(), offset);
    }

    @Override
    public void onResponse(TwitchVideos response) {
        emptyView.setText(R.string.channel_archives_empty);
        if (adapter == null) {
            adapter = new ArchiveAdapter(getActivity(), response.getVideos());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getVideos());
        }
    }
}
