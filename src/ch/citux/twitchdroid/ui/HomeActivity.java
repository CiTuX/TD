package ch.citux.twitchdroid.ui;

import android.app.Activity;
import android.os.Bundle;
import ch.citux.twitchdroid.R;
import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
