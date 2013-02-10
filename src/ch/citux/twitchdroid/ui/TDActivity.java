package ch.citux.twitchdroid.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.config.TDConfig;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.dialogs.InputDialogFragment;
import ch.citux.twitchdroid.ui.fragments.TDFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TDActivity extends SherlockFragmentActivity implements InputDialogFragment.OnDoneListener {

    private ArrayList<WeakReference<TDFragment>> fragments;
    private SharedPreferences preferences;
    private MenuItem refreshItem;
    private boolean isLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<WeakReference<TDFragment>>();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.main);
    }

    @Override
    public void onPause() {
        super.onPause();
        TDTaskManager.cancelAllTasks();
    }

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof TDFragment) {
            fragments.add(new WeakReference<TDFragment>((TDFragment) fragment));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        inflater.inflate(R.menu.refresh, menu);
        refreshItem = menu.findItem(R.id.menu_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (!isLoading) {
                    refreshData();
                }
                return true;
            case R.id.menu_settings:
                InputDialogFragment.InputDialogFragmentBuilder builder
                        = new InputDialogFragment.InputDialogFragmentBuilder(this);
                builder.setTitle(R.string.action_settings)
                        .setHint(R.string.channel_name)
                        .setText(preferences.getString(TDConfig.SETTINGS_CHANNEL_NAME, ""))
                        .setOnDoneListener(this)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishInputDialog(String inputText) {
        preferences.edit().putString(TDConfig.SETTINGS_CHANNEL_NAME, inputText).apply();
        refreshData();
    }

    public void startLoading() {
        isLoading = true;
        if (refreshItem != null) {
            refreshItem.setActionView(R.layout.action_refresh);
        }
    }

    public void stopLoading() {
        isLoading = false;
        if (refreshItem != null) {
            refreshItem.setActionView(null);
        }
    }

    protected void refreshData() {
        for (WeakReference<TDFragment> ref : fragments) {
            TDFragment fragment = ref.get();
            if (fragment != null && fragment.isAdded()) {
                fragment.refreshData();
            }
        }
    }
}
