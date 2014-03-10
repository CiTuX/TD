package ch.citux.td.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.Status;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.worker.TDBasicCallback;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.ArchiveAdapter;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;
import ch.citux.td.ui.widget.EmptyView;
import ch.citux.td.util.Log;

public class ChannelFragment extends TDFragment<Videos> implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String CHANNEL = "channel";

    private ArchiveAdapter adapter;
    private Channel channel;

    @InjectView(R.id.empty) EmptyView empty;
    @InjectView(R.id.content) ViewGroup content;
    @InjectView(R.id.imgLogo) ImageView imgLogo;
    @InjectView(R.id.lblTitle) TextView lblTitle;
    @InjectView(R.id.lblStatus) TextView lblStatus;
    @InjectView(R.id.btnStream) Button btnStream;

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
            empty.setText(R.string.channel_detail_empty);
        }
        btnStream.setOnClickListener(this);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (empty != null) {
            empty.showProgress();
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

        empty.showProgress();
        empty.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        getListView().setEmptyView(empty);
        loadData();
    }

    private void playVideo(String title, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), TDConfig.MIME_FLV);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
            builder.setTitle(R.string.error_no_player_title);
            builder.setMessage(R.string.error_no_player_message);
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(new StreamPlaylistCallback(this), channel.getName());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Video video = adapter.getItem(position);
        TDTaskManager.getVideo(new GetVideoCallback(this), video.getId());
    }

    @Override
    public void onResponse(Videos response) {
        empty.setText(R.string.channel_archives_empty);
        if (adapter == null) {
            adapter = new ArchiveAdapter(getActivity(), response.getVideos());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getVideos());
        }
    }

    private class GetVideoCallback extends TDBasicCallback<Video> {

        protected GetVideoCallback(Object caller) {
            super(caller);
        }

        @Override
        public void onResponse(Video response) {
            if (response.getUrl() != null) {
                playVideo(response.getTitle(), response.getUrl());
            }
        }

        @Override
        public boolean isAdded() {
            return ChannelFragment.this.isAdded();
        }
    }

    private class StreamPlaylistCallback extends TDBasicCallback<StreamPlayList> {

        protected StreamPlaylistCallback(Object caller) {
            super(caller);
        }

        @Override
        public void onResponse(StreamPlayList response) {
            Log.d(this, "Streams :" + response.getStreams().toString());
            if (response.getStreams() != null && response.getStreams().size() > 0) {
                //TODO quality from settings
                String url = response.getStream(StreamPlayList.QUALITY_MEDIUM);
                if (url != null) {
                    playVideo(channel.getTitle(), url);
                }
            } else {
                ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
                builder.setMessage("Stream offline :(").setTitle("Error").show();
            }
        }

        @Override
        public boolean isAdded() {
            return ChannelFragment.this.isAdded();
        }
    }
}
