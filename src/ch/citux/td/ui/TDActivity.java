package ch.citux.td.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.holoeverywhere.app.Activity;

import ch.citux.td.R;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.fragments.ChannelFragment;
import ch.citux.td.ui.fragments.FavoritesFragment;
import ch.citux.td.ui.fragments.SettingsFragment;

public class TDActivity extends Activity {

    private FavoritesFragment favoritesFragment;
    private ChannelFragment channelFragment;
    private SharedPreferences preferences;
    private MenuItem refreshItem;
    private MenuItem settingsItem;
    private boolean isLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
        settingsItem = menu.findItem(R.id.menu_settings);
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
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.content, new SettingsFragment())
                        .addToBackStack(SettingsFragment.class.getSimpleName())
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            MenuItemCompat.setActionView(refreshItem, R.layout.action_refresh);
        }
    }

    public void stopLoading() {
        isLoading = false;
        if (refreshItem != null) {
            MenuItemCompat.setActionView(refreshItem, null);
        }
    }

    public void showOptions() {
        if (refreshItem != null) {
            refreshItem.setVisible(true);
        }
        if (settingsItem != null) {
            settingsItem.setVisible(true);
        }
    }

    public void hideOptions() {
        if (refreshItem != null) {
            refreshItem.setVisible(false);
        }
        if (settingsItem != null) {
            settingsItem.setVisible(false);
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
