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
import android.content.Intent;
import android.net.Uri;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamQuality;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.worker.TDBasicCallback;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;
import ch.citux.td.ui.fragments.TDFragment;

public class VideoPlayer {

    private static final String TAG = "VideoPlayer";

    public static void playVideo(TDFragment fragment, String title, String url) {
        Log.d(TAG, "Playing '" + title + "' from " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), TDConfig.MIME_FLV);

        if (intent.getData() != null) {
            try {
                fragment.startActivity(intent);
            } catch (ActivityNotFoundException exception) {
                ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(fragment.getActivity());
                builder.setTitle(R.string.error_no_player_title);
                builder.setMessage(R.string.error_no_player_message);
                builder.show();
            }
        }
    }


    public static class GetVideoCallback extends TDBasicCallback<Video> {

        private TDFragment fragment;

        public GetVideoCallback(TDFragment caller) {
            super(caller);
            fragment = caller;
        }

        @Override
        public void onResponse(Video response) {
            if (response.getUrl() != null) {
                playVideo(fragment, response.getTitle(), response.getUrl());
            }
        }

        @Override
        public boolean isAdded() {
            return fragment.isAdded();
        }
    }

    public static class StreamPlaylistCallback extends TDBasicCallback<StreamPlayList> {

        private TDFragment fragment;
        private String title;

        public StreamPlaylistCallback(TDFragment caller, String title) {
            super(caller);
            this.fragment = caller;
            this.title = title;
        }

        @Override
        public void onResponse(StreamPlayList response) {
            Log.d(this, "Streams :" + response.getStreams().toString());
            if (response.getStreams() != null && response.getStreams().size() > 0) {
                StreamQuality streamQuality = StreamPlayList.parseQuality(fragment.getDefaultSharedPreferences().getString(R.id.stream_quality, StreamPlayList.QUALITY_MEDIUM.getName()));
                Log.d(this, "streamQuality: " + streamQuality.getName());
                String url = response.getStream(streamQuality);
                if (url != null) {
                    playVideo(fragment, title, url);
                    return;
                }
            }
            ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(fragment.getActivity());
            builder.setMessage(R.string.error_stream_offline).setTitle(R.string.dialog_error_title).show();
        }

        @Override
        public boolean isAdded() {
            return fragment.isAdded();
        }
    }
}
