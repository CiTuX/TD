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

import org.holoeverywhere.widget.CheckBox;

import java.util.ArrayList;

import ch.citux.td.R;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.ui.adapter.PlaylistAdapter;
import ch.citux.td.util.VideoPlayer;

public class ChannelPlaylistFragment extends TDListFragment<Void> implements AdapterView.OnItemClickListener {

    private PlaylistAdapter adapter;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ChannelFragment.PLAYLIST)) {
            VideoPlaylist playlist = (VideoPlaylist) getArguments().getSerializable(ChannelFragment.PLAYLIST);
            setData(playlist.getVideos());
        }

        setOnItemClickListener(this);
    }

    public void setData(ArrayList<Video> videos) {
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
    public void onResponse(Void response) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Video video = adapter.getItem(position);
        if (video != null) {
            VideoPlayer.playVideo(getTDActivity(), video.getTitle(), video.getUrl());
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
