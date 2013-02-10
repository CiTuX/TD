package ch.citux.twitchdroid.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Logo;
import ch.citux.twitchdroid.data.model.Status;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.dialogs.ErrorDialogFragment;
import ch.citux.twitchdroid.ui.widget.EmptyView;
import ch.citux.twitchdroid.util.Log;
import com.yixia.zi.utils.ImageFetcher;
import io.vov.vitamio.activity.VideoActivity;

public class ChannelFragment extends TDFragment<StreamPlayList> implements View.OnClickListener {

    public static final String CHANNEL = "channel";

    private Channel channel;
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
            empty.setText("Select a channel for details.");
        }
        btnStream.setOnClickListener(this);
    }

    public void updateChannel(final Channel channel) {
        this.channel = channel;
        if (imageFetcher == null) {
            imageFetcher = new ImageFetcher(getActivity());
        }
        imageFetcher.loadImage(channel.getLogo(Logo.MEDIUM), imgLogo, R.drawable.default_channel_logo_medium);
        lblTitle.setText(channel.getTitle());
        lblStatus.setText(channel.getStatus().getText());
        btnStream.setVisibility(channel.getStatus() == Status.ONLINE ? View.VISIBLE : View.GONE);

        empty.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(StreamPlayList response) {
        Log.d(this, "Streams :" + response.getStreams().toString());
        if (response.getStreams() != null && response.getStreams().size() > 0) {
            Uri uri = Uri.parse(response.getStream(StreamPlayList.QUALITY_720P));
            Intent playerIntent = new Intent(Intent.ACTION_VIEW, uri, getActivity(), VideoActivity.class);
            playerIntent.putExtra("displayName", channel.getTitle());
            getActivity().startActivity(playerIntent);
        } else {
            ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
            builder.setMessage("Stream offline :(").setTitle("Error").show();
        }
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(this, channel.getName(), true);
    }
}
