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
package ch.citux.td.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamQuality;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.worker.TDBasicCallback;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.adapter.PlaylistAdapter;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;
import ch.citux.td.ui.fragments.TDBase;
import ch.citux.td.ui.fragments.VideoFragment;

public class VideoPlayer {

    private static final String TAG = "VideoPlayer";

    public static void playVideo(TDActivity activity, VideoPlaylist playlist) {
        Log.d(TAG, "Playing Playlist'" + playlist.getTitle() + "' with " + playlist.getVideos().size() + " parts");

        if (useInternPlayer(activity)) { //built-in Player
            Bundle args = new Bundle();
            args.putString(VideoFragment.TITLE, playlist.getTitle());
            args.putStringArray(VideoFragment.PLAYLIST, getPlaylistUrls(playlist));
            playVideoIntern(activity, args);
        } else {
            activity.showPlaylist(playlist);
        }
    }

    private static String[] getPlaylistUrls(VideoPlaylist playlist) {
        if (playlist != null && playlist.getVideos() != null) {
            ArrayList<Video> videos = playlist.getVideos();
            String[] urls = new String[videos.size()];
            for (int i = 0; i < videos.size(); i++) {
                urls[i] = videos.get(i).getUrl();
            }
            return urls;
        }
        return null;
    }

    public static void playVideo(TDActivity activity, String title, String url) {
        Log.d(TAG, "Playing '" + title + "' from " + url);

        if (useInternPlayer(activity)) { //built-in Player
            Log.d(TAG, "internal");
            Bundle args = new Bundle();
            args.putString(VideoFragment.TITLE, title);
            args.putString(VideoFragment.URL, url);
            playVideoIntern(activity, args);
        } else { //external Player
            Log.d(TAG, "external");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), TDConfig.MIME_FLV);

            if (intent.getData() != null) {
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(activity);
                    builder.setTitle(R.string.error_no_player_title);
                    builder.setMessage(R.string.error_no_player_message);
                    builder.show();
                }
            }
        }
    }

    private static void playVideoIntern(TDActivity activity, Bundle args) {
        activity.showVideo(args);
    }

    private static void playVideoExtern(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, TDConfig.MIME_FLV);
        context.startActivity(intent);
    }

    public static boolean useInternPlayer(TDActivity activity) {
//        try {
//            return activity != null && activity.getDefaultSharedPreferences().getBoolean(R.id.internal_player, false) && LibsChecker.checkVitamioLibs(activity);
//        } catch (Throwable throwable) {
//            Log.e(TAG, "Cannot Use internal Player");
//            Toast.makeText(activity, R.string.error_internal_player, android.widget.Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return false;
    }

    public static class GetVideoCallback extends TDBasicCallback<VideoPlaylist> implements DialogInterface.OnClickListener {

        private TDBase fragment;
        private TDActivity activity;
        private PlaylistAdapter adapter;

        public GetVideoCallback(TDBase caller) {
            super(caller);
            fragment = caller;
            activity = caller.getTDActivity();
        }

        @Override
        public void onResponse(VideoPlaylist response) {
            ArrayList<Video> videos = response.getVideos();
            if (videos != null && videos.size() > 0) {
                adapter = new PlaylistAdapter(activity, videos);
                playVideo(activity, response);
            }
        }

        @Override
        public boolean isAdded() {
            return fragment.isAdded();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int position) {
            Video video = adapter.getItem(position);
            if (video != null && video.getUrl() != null && fragment != null && activity != null) {
                playVideoExtern(activity, Uri.parse(video.getUrl()));
            }
        }
    }

    public static class StreamPlaylistCallback extends TDBasicCallback<StreamPlayList> {

        private TDBase fragment;
        private TDActivity activity;
        private String title;

        public StreamPlaylistCallback(TDBase caller, String title) {
            super(caller);
            this.fragment = caller;
            this.activity = caller.getTDActivity();
            this.title = title;
        }

        @Override
        public void onResponse(StreamPlayList response) {
            if (response != null && response.getStreams() != null) {
                Log.d(this, "Streams :" + response.getStreams().toString());
                if (response.getStreams() != null && response.getStreams().size() > 0) {
                    StreamQuality streamQuality = StreamPlayList.parseQuality(fragment.getDefaultSharedPreferences().getString(R.id.stream_quality, StreamPlayList.QUALITY_MEDIUM.getName()));
                    Log.d(this, "streamQuality: " + streamQuality.getName());
                    String url = response.getStream(streamQuality);
                    if (url != null) {
                        playVideo(activity, title, url);
                        return;
                    }
                }
            }
            ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(activity);
            builder.setMessage(R.string.error_stream_offline).setTitle(R.string.dialog_error_title).show();
        }

        @Override
        public boolean isAdded() {
            return fragment.isAdded();
        }
    }
}
