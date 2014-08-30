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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.holoeverywhere.app.Fragment;

import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.Status;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.util.VideoPlayer;

public class ChannelFragment extends TDFragment<Void> implements View.OnClickListener {

    public static final String CHANNEL = "channel";
    public static final String PLAYLIST = "playlist";

    @InjectView(R.id.content) ViewGroup content;
    @InjectView(R.id.imgLogo) ImageView imgLogo;
    @InjectView(R.id.lblTitle) TextView lblTitle;
    @InjectView(R.id.lblStatus) TextView lblStatus;
    @InjectView(R.id.btnStream) Button btnStream;

    private Channel channel;
    private ChannelVideosFragment videosFragment;
    private ChannelPlaylistFragment playlistFragment;

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

    public void updateChannel(final Channel channel) {
        this.channel = channel;
        Picasso.with(getActivity()).load(channel.getLogo(Logo.LARGE)).placeholder(R.drawable.default_channel_logo_medium).into(imgLogo);
        lblTitle.setText(channel.getTitle());
        lblStatus.setText(channel.getStatus().getText());
        btnStream.setVisibility(channel.getStatus() == Status.ONLINE ? View.VISIBLE : View.GONE);

        emptyView.showProgress();
        emptyView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        Bundle args = new Bundle();
        args.putSerializable(CHANNEL, channel);
        videosFragment = Fragment.instantiate(ChannelVideosFragment.class, args);
        setFragment(videosFragment, false);
    }

    public void showPlaylist(final VideoPlaylist playlist) {
        if (playlist != null && playlist.getVideos() != null) {
            Bundle args = new Bundle();
            args.putSerializable(PLAYLIST, playlist);
            playlistFragment = Fragment.instantiate(ChannelPlaylistFragment.class, args);
            setFragment(playlistFragment, true);
        }
    }

    private void setFragment(Fragment fragment, boolean backstack) {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (backstack) {
                    transaction.addToBackStack(null);
                }

                Fragment currentFragment = (Fragment) fragmentManager.findFragmentById(R.id.container);
                if (currentFragment == null) {
                    transaction.add(R.id.container, fragment);
                } else {
                    if (!currentFragment.equals(fragment)) {
                        transaction.replace(R.id.container, fragment);
                    } else {
                        ((TDBase) fragment).loadData();
                    }
                }
                transaction.commit();
            }
        }
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(new VideoPlayer.StreamPlaylistCallback(this, channel.getTitle()), channel.getName());
    }

    @Override
    public void loadData() {
        if (videosFragment != null) {
            videosFragment.loadData();
        }
    }

    @Override
    public void onResponse(Void response) {
    }
}
