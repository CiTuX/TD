package ch.citux.twitchdroid.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.config.TDConfig;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.worker.TDTaskManager;
import ch.citux.twitchdroid.ui.dialogs.InputDialogFragment;
import ch.citux.twitchdroid.ui.fragments.ChannelFragment;
import ch.citux.twitchdroid.ui.fragments.FavoritesFragment;

public class TDActivity extends FragmentActivity implements InputDialogFragment.OnDoneListener {

    private FavoritesFragment favoritesFragment;
    private ChannelFragment channelFragment;
    private SharedPreferences preferences;
    private MenuItem refreshItem;
    private boolean isLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
            return;
        }

        favoritesFragment = new FavoritesFragment();
        channelFragment = new ChannelFragment();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content, favoritesFragment);
        if (findViewById(R.id.detail) != null) { //Tablet
            transaction.add(R.id.detail, channelFragment);
        }
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        TDTaskManager.cancelAllTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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

    public void showChannel(Channel channel) {
        if (channelFragment.isAdded()) {
            channelFragment.updateChannel(channel);
        } else {
            Bundle arguments = new Bundle();
            arguments.putSerializable(ChannelFragment.CHANNEL, channel);
            channelFragment.setArguments(arguments);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, channelFragment).addToBackStack(ChannelFragment.class.getSimpleName()).commit();
        }

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
        if (favoritesFragment != null && favoritesFragment.isAdded()) {
            favoritesFragment.refreshData();
        }
        if (channelFragment != null && channelFragment.isAdded()) {
            channelFragment.refreshData();
        }
    }
}
