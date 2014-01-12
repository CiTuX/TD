package ch.citux.td.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixia.zi.utils.ImageFetcher;

import org.holoeverywhere.LayoutInflater;

import ch.citux.td.R;
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
import io.vov.vitamio.LibsChecker;

public class ChannelFragment extends TDFragment<Videos> implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String CHANNEL = "channel";

    private Channel channel;
    private ArchiveAdapter adapter;
    private ImageFetcher imageFetcher;
    private EmptyView empty;
    private ViewGroup content;
    private ImageView imgLogo;
    private TextView lblTitle;
    private TextView lblStatus;
    private Button btnStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.channel_detail, null, false);
        empty = (EmptyView) contentView.findViewById(R.id.empty);
        content = (ViewGroup) contentView.findViewById(R.id.content);
        imgLogo = (ImageView) contentView.findViewById(R.id.imgLogo);
        lblTitle = (TextView) contentView.findViewById(R.id.lblTitle);
        lblStatus = (TextView) contentView.findViewById(R.id.lblStatus);
        btnStream = (Button) contentView.findViewById(R.id.btnStream);
        return contentView;
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
        if (imageFetcher == null) {
            imageFetcher = new ImageFetcher(getActivity());
        }
        imageFetcher.loadImage(channel.getLogo(Logo.LARGE), imgLogo, R.drawable.default_channel_logo_medium);
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
        if (LibsChecker.checkVitamioLibs(getActivity())) {
            // vitamio works
            Bundle arguments = new Bundle();
            arguments.putString(PlayerFragment.TITLE, title);
            arguments.putString(PlayerFragment.URL, url);

            PlayerFragment playerFragment = new PlayerFragment();
            playerFragment.setArguments(arguments);

//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.content, playerFragment)
//                    .addToBackStack(playerFragment.getClass().getSimpleName())
//                    .commit();
        }
//        Intent playerIntent = new Intent(Intent.ACTION_VIEW, uri, getActivity(), VideoActivity.class);
//        playerIntent.putExtra("displayName", title);
//        getActivity().startActivity(playerIntent);
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(new StreamPlaylistCallback(this), channel.getName(), true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Video video = adapter.getItem(position);
        if (video != null) {
            playVideo(video.getTitle(), video.getUrl());
        }
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

    private class StreamPlaylistCallback extends TDBasicCallback<StreamPlayList> {

        protected StreamPlaylistCallback(Object caller) {
            super(caller);
        }

        @Override
        public void onResponse(StreamPlayList response) {
            Log.d(this, "Streams :" + response.getStreams().toString());
            if (response.getStreams() != null && response.getStreams().size() > 0) {
                String url = response.getBestStream();
                if (url != null) {
                    playVideo(channel.getTitle(), url);
                }
            } else {
                ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
                builder.setMessage("Stream offline :(").setTitle("Error").show();
            }
        }
    }
}
