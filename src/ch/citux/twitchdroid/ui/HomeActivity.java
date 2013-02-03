package ch.citux.twitchdroid.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import ch.citux.twitchdroid.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import io.vov.vitamio.activity.VideoActivity;

public class HomeActivity extends SherlockFragmentActivity {

    private static final String VIDEO = "http://uscap.posterview.com/Help/files/iPostersVideoHD.mp4";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent playerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO), this, VideoActivity.class);
        playerIntent.putExtra("displayName", "Test");
//        startActivity(playerIntent);
    }
}
