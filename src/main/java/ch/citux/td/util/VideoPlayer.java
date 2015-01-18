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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import net.chilicat.m3u8.Element;
import net.chilicat.m3u8.ParseException;
import net.chilicat.m3u8.Playlist;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.TwitchAccessToken;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChunk;
import ch.citux.td.data.model.TwitchChunks;
import ch.citux.td.data.model.TwitchStreamPlayList;
import ch.citux.td.data.model.TwitchStreamQuality;
import ch.citux.td.data.model.TwitchVideo;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDBasicCallback;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.adapter.PlaylistAdapter;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;
import ch.citux.td.ui.fragments.TDBase;
import ch.citux.td.ui.fragments.VideoFragment;
import retrofit.client.Response;

public class VideoPlayer {

    private static final String TAG = "VideoPlayer";

    public static void playVideo(TDActivity activity, TwitchBroadcast broadcast) {
        Log.d(TAG, "Playing Playlist'" + broadcast.getChannel() + "' with " + broadcast.getChunks().getLive().size() + " parts");

        if (useInternPlayer(activity)) { //built-in Player
            Bundle args = new Bundle();
            args.putString(VideoFragment.TITLE, broadcast.getChannel());
            args.putStringArray(VideoFragment.PLAYLIST, getPlaylistUrls(broadcast));
            playVideoIntern(activity, args);
        } else {
            activity.showPlaylist(broadcast);
        }
    }

    private static String[] getPlaylistUrls(TwitchBroadcast broadcast) {
        if (broadcast != null && broadcast.getChunks() != null && broadcast.getChunks().getLive() != null) {
            List<TwitchChunk> videos = broadcast.getChunks().getLive();
            String[] urls = new String[videos.size()];
            for (int i = 0; i < videos.size(); i++) {
                urls[i] = videos.get(i).getUrl();
            }
            return urls;
        }
        return null;
    }

    public static void playVideo(TDActivity activity, String title, String url) {
        if (activity != null) {
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
                    } catch (Exception exception) {
                        ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(activity);
                        builder.setTitle(R.string.error_no_player_title);
                        builder.setMessage(R.string.error_no_player_message);
                        builder.show();
                    }
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

    public static class GetVideoCallback extends TDBasicCallback<TwitchBroadcast> implements DialogInterface.OnClickListener {

        private TDBase fragment;
        private TDActivity activity;
        private PlaylistAdapter adapter;
        private String videoId;

        public GetVideoCallback(TDBase caller, TwitchVideo video) {
            super(caller);
            fragment = caller;
            activity = caller.getTDActivity();
            videoId = video.get_id();
        }

        @Override
        public TwitchBroadcast startRequest() {
            return TDServiceImpl.getInstance().getVideoPlaylist(videoId);
        }

        @Override
        public void onResponse(TwitchBroadcast response) {
            TwitchChunks videos = response.getChunks();
            if (videos != null && videos.getLive() != null && videos.getLive().size() > 0) {
                adapter = new PlaylistAdapter(activity, videos.getLive());
                playVideo(activity, response);
            }
        }

        @Override
        public boolean isAdded() {
            return fragment.isAdded();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int position) {
            TwitchChunk video = adapter.getItem(position);
            if (video != null && video.getUrl() != null && fragment != null && activity != null) {
                playVideoExtern(activity, Uri.parse(video.getUrl()));
            }
        }
    }

    public static class StreamPlaylistCallback extends TDBasicCallback<TwitchStreamPlayList> {

        private TDBase fragment;
        private TDActivity activity;
        private String channel;

        public StreamPlaylistCallback(TDBase caller, String channel) {
            super(caller);
            this.fragment = caller;
            this.activity = caller.getTDActivity();
            this.channel = channel;
        }

        @Override
        public TwitchStreamPlayList startRequest() {
            TwitchStreamPlayList streamPlayList = new TwitchStreamPlayList();
            TwitchAccessToken accessToken = getAccessToken();
            Response response = TDServiceImpl.getInstance().getStreamPlaylist(channel, accessToken.getP(), accessToken.getToken(), accessToken.getSig());
            try {
                Playlist playlist = Playlist.parse(response.getBody().in());
                streamPlayList.setStreams(parsePlaylist(playlist));
            } catch (ParseException | IOException e) {
                Log.e(this, e);
            }
            return streamPlayList;
        }

        private TwitchAccessToken getAccessToken() {
            TwitchAccessToken accessToken = TDServiceImpl.getInstance().getStreamToken(channel);
            accessToken.setP(String.valueOf((Math.random() * 999999)));
            return accessToken;
        }

        private HashMap<TwitchStreamQuality, String> parsePlaylist(Playlist playlist) {
            HashMap<TwitchStreamQuality, String> streams = new HashMap<TwitchStreamQuality, String>();
            if (playlist != null) {
                List<Element> elements = playlist.getElements();
                if (elements != null && elements.size() > 0) {
                    for (Element element : elements) {
                        Log.d(TAG, "URI: " + element.getURI() + " Name: " + element.getPlayListInfo().getVideo());
                        TwitchStreamQuality quality = TwitchStreamPlayList.parseQuality(element.getPlayListInfo().getVideo());
                        if (quality != null) {
                            streams.put(quality, element.getURI().toString());
                        }
                    }
                }
            }
            return streams;
        }

        @Override
        public void onResponse(TwitchStreamPlayList response) {
            if (response != null && response.getStreams() != null) {
                Log.d(this, "Streams :" + response.getStreams().toString());
                if (response.getStreams() != null && response.getStreams().size() > 0) {
                    String quality = fragment.getDefaultSharedPreferences().getString(TDConfig.SETTINGS_STREAM_QUALITY, TwitchStreamPlayList.QUALITY_MEDIUM.getKey());
                    TwitchStreamQuality streamQuality = TwitchStreamPlayList.parseQuality(quality);
                    Log.d(this, "streamQuality: " + streamQuality.getName());
                    String url = response.getStream(streamQuality);
                    if (url != null) {
                        playVideo(activity, channel, url);
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
