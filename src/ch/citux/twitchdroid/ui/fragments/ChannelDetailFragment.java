package ch.citux.twitchdroid.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import ch.citux.twitchdroid.data.worker.TwitchDroidTaskManager;
import com.actionbarsherlock.app.SherlockFragment;
import io.vov.vitamio.activity.VideoActivity;

public class ChannelDetailFragment extends SherlockFragment implements TwitchDroidCallback<StreamPlayList> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TwitchDroidTaskManager.getStreamPlaylist(this, "gamespot", true);
        return inflater.inflate(R.layout.channel_detail, null);
    }

    @Override
    public void onResponse(StreamPlayList response) {
        if (response.getStreams().size() > 0) {
            Intent playerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getStreams().get(StreamPlayList.QUALITY_720P)), getActivity(), VideoActivity.class);
            playerIntent.putExtra("displayName", "Test");
            getActivity().startActivity(playerIntent);
        }
    }

    @Override
    public void onError(String title, String message) {
    }
}
