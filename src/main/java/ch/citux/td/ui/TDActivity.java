/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.citux.td.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.LinearLayout;

import java.lang.reflect.Field;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.fragments.ChannelFragment;
import ch.citux.td.ui.fragments.FavoritesFragment;
import ch.citux.td.ui.fragments.GameOverviewFragment;
import ch.citux.td.ui.fragments.GameStreamsFragment;
import ch.citux.td.ui.fragments.SearchFragment;
import ch.citux.td.ui.fragments.SettingsFragment;
import ch.citux.td.ui.fragments.VideoFragment;
import ch.citux.td.util.Log;

public class TDActivity extends Activity implements View.OnFocusChangeListener {

    private GameOverviewFragment gameOverviewFragment;
    private GameStreamsFragment gameStreamsFragment;
    private FavoritesFragment favoritesFragment;
    private ChannelFragment channelFragment;
    private SearchFragment searchFragment;
    private VideoFragment videoFragment;

    private MenuItem settingsItem;
    private MenuItem refreshItem;
    private MenuItem searchItem;
    private boolean isLoading;
    private boolean isTablet;
    private boolean hasUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        hasUsername = !PreferenceManager.getDefaultSharedPreferences(this).getString(TDConfig.SETTINGS_CHANNEL_NAME, "").equals("");
        isTablet = findViewById(R.id.detail) != null;

        Bundle args = new Bundle();
        args.putBoolean(TDConfig.SETTINGS_CHANNEL_NAME, hasUsername);

        favoritesFragment = new FavoritesFragment();
        favoritesFragment.setArguments(args);

        channelFragment = new ChannelFragment();
        channelFragment.setArguments(args);

        if (getSupportFragmentManager().findFragmentById(R.id.content) == null && !favoritesFragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content, favoritesFragment);
            if (isTablet) {
                transaction.add(R.id.detail, channelFragment);
            }
            transaction.commitAllowingStateLoss();
        }
        showGameOverview();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if (searchFragment == null) {
                searchFragment = new SearchFragment();
            }
            searchFragment.setQuery(query);
            searchFragment.loadData();

            if (getSupportFragmentManager().findFragmentById(R.id.content) != searchFragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, searchFragment);
                fragmentTransaction.commit();
            }
            Log.d(this, query);
        }
    }

    @Override
    public void onPause() {
        TDTaskManager.cancelAllTasks();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        refreshItem = menu.findItem(R.id.menu_refresh);
        settingsItem = menu.findItem(R.id.menu_settings);
        searchItem = menu.findItem(R.id.menu_search);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextFocusChangeListener(this);

        //Replace the system icons for higher resolution
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            try {
                Field searchField = SearchView.class.getDeclaredField("mSearchButton");
                searchField.setAccessible(true);
                ImageView searchBtn = (ImageView) searchField.get(searchView);
                searchBtn.setImageResource(R.drawable.ic_action_search);
                searchField = SearchView.class.getDeclaredField("mSearchPlate");
                searchField.setAccessible(true);
                LinearLayout searchPlate = (LinearLayout) searchField.get(searchView);
                ((ImageView) searchPlate.getChildAt(1)).setImageResource(R.drawable.abc_ic_clear);
            } catch (NoSuchFieldException e) {
                Log.e(this, e);
            } catch (IllegalAccessException e) {
                Log.e(this, e);
            }
        }
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
                getSupportFragmentManager()
                        .beginTransaction()
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
            transaction.replace(R.id.content, channelFragment);
            transaction.addToBackStack(ChannelFragment.class.getSimpleName());
            transaction.commit();
        }
    }

    public void showVideo(Bundle args) {
        if(videoFragment == null){
            videoFragment = new VideoFragment();
        }
        videoFragment.setArguments(args);

        if (videoFragment.isAdded()) {
            videoFragment.playVideo();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(isTablet ? R.id.detail : R.id.content, videoFragment);
            transaction.addToBackStack(VideoFragment.class.getSimpleName());
            transaction.commit();
        }
        MenuItemCompat.collapseActionView(searchItem);
    }

    public void showPlaylist(VideoPlaylist playlist) {
        if (channelFragment.isAdded()) {
            channelFragment.showPlaylist(playlist);
        }
    }

    public void showGameOverview() {
        if (gameOverviewFragment == null) {
            gameOverviewFragment = new GameOverviewFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(isTablet ? R.id.detail : R.id.content, gameOverviewFragment);
        transaction.addToBackStack(GameOverviewFragment.class.getSimpleName());
        transaction.commit();
    }

    public void showStreams(Bundle args) {
        if (gameStreamsFragment == null) {
            gameStreamsFragment = new GameStreamsFragment();
        }
        gameStreamsFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(isTablet ? R.id.detail : R.id.content, gameStreamsFragment);
        transaction.addToBackStack(GameStreamsFragment.class.getSimpleName());
        transaction.commit();
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

    public void showActionItems() {
        if (refreshItem != null) {
            refreshItem.setVisible(true);
        }
        if (settingsItem != null) {
            settingsItem.setVisible(true);
        }
        if (searchItem != null) {
            searchItem.setVisible(true);
        }
    }

    public void hideActionItems() {
        if (refreshItem != null) {
            refreshItem.setVisible(false);
        }
        if (settingsItem != null) {
            settingsItem.setVisible(false);
        }
        if (searchItem != null) {
            searchItem.setVisible(false);
        }
    }

    protected void refreshData() {
        if (favoritesFragment != null && favoritesFragment.isAdded()) {
            favoritesFragment.refreshData();
        }
        if (channelFragment != null && channelFragment.isAdded()) {
            channelFragment.refreshData();
        }
        if (searchFragment != null && searchFragment.isAdded()) {
            searchFragment.refreshData();
        }
        if (gameOverviewFragment != null && gameOverviewFragment.isAdded()) {
            gameOverviewFragment.refreshData();
        }
        if (gameStreamsFragment != null && gameStreamsFragment.isAdded()) {
            gameStreamsFragment.refreshData();
        }
    }

    public boolean isTablet() {
        return isTablet;
    }

    @Override
    public void onFocusChange(View view, boolean queryTextFocused) {
        if (queryTextFocused) {
            if (settingsItem != null) {
                settingsItem.setVisible(false);
            }
        } else {
            MenuItemCompat.collapseActionView(searchItem);

            if (settingsItem != null) {
                settingsItem.setVisible(true);
            }

            if (getSupportFragmentManager().findFragmentById(R.id.content) == searchFragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, favoritesFragment);
                transaction.commit();
            }
        }
    }
}
