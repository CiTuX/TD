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
import android.widget.CheckBox;

import java.util.List;

import ch.citux.td.R;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChunk;
import ch.citux.td.ui.adapter.PlaylistAdapter;
import ch.citux.td.util.VideoPlayer;

public class ChannelPlaylistFragment extends TDListFragment implements AdapterView.OnItemClickListener {

    private TwitchBroadcast broadcast;
    private PlaylistAdapter adapter;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ChannelFragment.CHUNKS)) {
            TwitchBroadcast broadcast = (TwitchBroadcast) getArguments().getSerializable(ChannelFragment.CHUNKS);
            this.broadcast = broadcast;
            setData(broadcast.getChunks().getLive());
        }

        setOnItemClickListener(this);
    }

    public void setData(List<TwitchChunk> videos) {
        if (adapter == null) {
            adapter = new PlaylistAdapter(getActivity(), videos);
            setListAdapter(adapter);
        } else {
            adapter.setVideos(videos);
        }
    }

    @Override
    public void loadData() {
    }

    @Override
    public Void startRequest() {
        return null;
    }

    @Override
    public void onResponse(Object response) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TwitchChunk video = adapter.getItem(position);
        if (video != null) {
            //TODO Name
            VideoPlayer.playVideo(getTDActivity(), "", video.getUrl());
        }
        if (view != null) {
            CheckBox chkPlayed = (CheckBox) view.findViewById(R.id.chkPlayed);
            if (chkPlayed != null) {
                chkPlayed.setChecked(true);
                adapter.setPlayed(position);
            }
        }
    }
}
