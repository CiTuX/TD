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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.Status;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.ArchiveAdapter;
import ch.citux.td.ui.widget.EmptyView;
import ch.citux.td.util.VideoPlayer;

public class ChannelFragment extends TDFragment<Videos> implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String CHANNEL = "channel";

    @InjectView(R.id.content) ViewGroup content;
    @InjectView(R.id.imgLogo) ImageView imgLogo;
    @InjectView(R.id.lblTitle) TextView lblTitle;
    @InjectView(R.id.lblStatus) TextView lblStatus;
    @InjectView(R.id.btnStream) Button btnStream;

    private ArchiveAdapter adapter;
    private Channel channel;

    @Override
    protected int onCreateView() {
        return R.layout.channel_detail;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(CHANNEL)) {
            updateChannel((Channel) getArguments().get(CHANNEL));
        } else {
            if (hasUsername) {
                emptyView.setText(R.string.channel_detail_empty);
            } else {
                emptyView.setImage(R.drawable.ic_glitchicon_black);
            }
        }
        btnStream.setOnClickListener(this);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (channel != null) {
            refreshData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (channel != null && emptyView != null) {
            emptyView.showProgress();
        }
    }

    @Override
    public void loadData() {
        if (adapter != null) {
            adapter.clear();
        }
        if (channel != null) {
            TDTaskManager.getArchives(this, channel.getName());
        }
    }

    public void updateChannel(final Channel channel) {
        this.channel = channel;
        Picasso.with(getActivity()).load(channel.getLogo(Logo.LARGE)).placeholder(R.drawable.default_channel_logo_medium).into(imgLogo);
        lblTitle.setText(channel.getTitle());
        lblStatus.setText(channel.getStatus().getText());
        btnStream.setVisibility(channel.getStatus() == Status.ONLINE ? View.VISIBLE : View.GONE);

        emptyView.showProgress();
        emptyView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        getListView().setEmptyView(emptyView);
        loadData();
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(new VideoPlayer.StreamPlaylistCallback(this, channel.getTitle()), channel.getName());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Video video = adapter.getItem(position);
        TDTaskManager.getVideo(new VideoPlayer.GetVideoCallback(this), video.getId());
    }

    @Override
    public void onResponse(Videos response) {
        emptyView.setText(R.string.channel_archives_empty);
        if (adapter == null) {
            adapter = new ArchiveAdapter(getActivity(), response.getVideos());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getVideos());
        }
    }
}
