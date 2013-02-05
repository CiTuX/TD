package ch.citux.twitchdroid.ui;

import android.os.Bundle;
import ch.citux.twitchdroid.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class HomeActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
