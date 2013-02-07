package ch.citux.twitchdroid.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.dialogs.ErrorDialogFragment;
import io.vov.vitamio.activity.VideoActivity;

public class ChannelDetailFragment extends TDFragment<StreamPlayList> implements View.OnClickListener {

    public static final String CHANNEL_NAME = "CHANNEL_NAME";

    private EditText txtChannelName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.channel_detail, null);
        txtChannelName = (EditText) contentView.findViewById(R.id.txtChannelName);
        contentView.findViewById(R.id.btnStream).setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onResponse(StreamPlayList response) {
        if (response.getStreams() != null && response.getStreams().size() > 0) {
            Intent playerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getStreams().get(StreamPlayList.QUALITY_720P)), getActivity(), VideoActivity.class);
            playerIntent.putExtra("displayName", txtChannelName.getText().toString());
            getActivity().startActivity(playerIntent);
        } else {
            ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
            builder.setMessage("Stream offline :(").setTitle("Error").show();
        }
    }

    @Override
    public void onClick(View v) {
        TDTaskManager.getStreamPlaylist(this, txtChannelName.getText().toString(), true);
    }
}
