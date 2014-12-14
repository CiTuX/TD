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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchLogo;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.util.VideoPlayer;

public class ChannelFragment extends TDFragment<Void> implements View.OnClickListener {

    public static final String CHANNEL = "channel";
    public static final String CHUNKS = "chunks";

    @InjectView(R.id.content) ViewGroup content;
    @InjectView(R.id.imgLogo) ImageView imgLogo;
    @InjectView(R.id.lblTitle) TextView lblTitle;
    @InjectView(R.id.lblStatus) TextView lblStatus;
    @InjectView(R.id.btnStream) Button btnStream;

    private TwitchChannel channel;
    private ChannelVideosFragment videosFragment;
    private ChannelPlaylistFragment playlistFragment;

    @Override
    protected int onCreateView() {
        return R.layout.channel_detail;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArgs().containsKey(CHANNEL)) {
            updateChannel((TwitchChannel) getArgs().get(CHANNEL));
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

    public void updateChannel(final TwitchChannel channel) {
        this.channel = channel;
        Picasso.with(getActivity()).load(channel.getLogo().getUrl(TwitchLogo.Size.LARGE)).placeholder(R.drawable.default_channel_logo_medium).into(imgLogo);
        lblTitle.setText(channel.getDisplay_name());
        lblStatus.setText(channel.getChannelStatus().getText());
        btnStream.setVisibility(channel.getChannelStatus() == TwitchChannel.Status.ONLINE ? View.VISIBLE : View.GONE);

        emptyView.showProgress();
        emptyView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        Bundle args = new Bundle();
        args.putSerializable(CHANNEL, channel);
        videosFragment = TDFragment.instantiate(ChannelVideosFragment.class, args);
        setFragment(videosFragment, false);
    }

    public void showPlaylist(final TwitchBroadcast broadcast) {
        if (broadcast != null && broadcast.getChunks() != null) {
            Bundle args = new Bundle();
            args.putSerializable(CHUNKS, broadcast);
            playlistFragment = TDFragment.instantiate(ChannelPlaylistFragment.class, args);
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
        TDTaskManager.executeTask(new VideoPlayer.StreamPlaylistCallback(this, channel.getName()));
    }

    @Override
    public void loadData() {
        if (videosFragment != null) {
            videosFragment.loadData();
        }
    }

    @Override
    public Void startRequest() {
        return null;
    }

    @Override
    public void onResponse(Void response) {
    }
}
