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

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.widget.EmptyView;

public class VideoFragment extends Fragment {//implements MediaPlayer.OnPreparedListener {

    public static final String PLAYLIST = "playlist";
    public static final String TITLE = "title";
    public static final String URL = "url";

    @InjectView(android.R.id.empty) EmptyView emptyView;
    //    @InjectView(R.id.videoView) VideoView videoView;
    @InjectView(R.id.player) View player;
    @InjectView(R.id.chat) View chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.video, container, false);
        ButterKnife.inject(this, contentView);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.setRetainInstance(true);

        if (((TDActivity) getActivity()).isTablet()) {
            emptyView.setImage(R.drawable.ic_glitchicon_white);
        }

//        MediaController mediaController = new MediaController(getActivity());
//        videoView.setMediaController(mediaController);
//        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 1);
//        videoView.setOnPreparedListener(this);
//
//        onOrientationChange(getResources().getConfiguration().orientation);
//
//        playVideo();
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        onOrientationChange(newConfig.orientation);
    }

    private void onOrientationChange(int orientation) {
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
//            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 1.77f);
//            chat.setVisibility(View.GONE);
//        } else {
//            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().show();
//            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 1.77f);
//            chat.setVisibility(View.VISIBLE);
//        }
    }

    public void playVideo() {

//        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
//        if (getArguments() != null && (!getArguments().containsKey(URL) || !getArguments().containsKey(PLAYLIST))) {
//            getSupportActionBar().setTitle(getArguments().getString(TITLE));
//
//            if (getArguments().containsKey(URL)) {
//                videoView.setVideoURI(Uri.parse(getArguments().getString(URL)));
//            }
//            if (getArguments().containsKey(PLAYLIST)) {
//                videoView.setVideoPlaylist(getArguments().getStringArray(PLAYLIST));
//            }
//            videoView.start();
//        }
    }

//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        emptyView.setVisibility(View.GONE);
//        player.setVisibility(View.VISIBLE);
//    }
}
