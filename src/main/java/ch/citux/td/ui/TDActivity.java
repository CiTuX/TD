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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.TwitchBroadcast;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchLogo;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.fragments.ChannelFragment;
import ch.citux.td.ui.fragments.FavoritesFragment;
import ch.citux.td.ui.fragments.GameOverviewFragment;
import ch.citux.td.ui.fragments.GameStreamsFragment;
import ch.citux.td.ui.fragments.SearchFragment;
import ch.citux.td.ui.fragments.SettingsFragment;
import ch.citux.td.ui.fragments.VideoFragment;
import ch.citux.td.util.Log;

public class TDActivity extends ActionBarActivity implements TDCallback<TwitchChannel>, View.OnFocusChangeListener, AdapterView.OnItemClickListener {

    private FavoritesFragment favoritesFragment;
    private GameOverviewFragment gameOverviewFragment;
    private GameStreamsFragment gameStreamsFragment;
    private ChannelFragment channelFragment;
    private SearchFragment searchFragment;
    private VideoFragment videoFragment;
    private SettingsFragment settingsFragment;

    private ActionBarDrawerToggle toggle;
    private MenuItem refreshItem;
    private MenuItem searchItem;
    private View refreshView;
    private String username;
    private Toast toast;
    private boolean isLoading;
    private boolean hasUsername;

    @InjectView(R.id.user) View user;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.imgUser) ImageView imgUser;
    @InjectView(R.id.lblUser) TextView lblUser;
    @InjectView(R.id.lblNoUser) TextView lblNoUser;
    @Optional @InjectView(R.id.drawerLayout) DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        ButterKnife.inject(this);

        Context contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme_TD_Light);
        LayoutInflater inflater = LayoutInflater.from(this).cloneInContext(contextThemeWrapper);
        refreshView = inflater.inflate(R.layout.action_refresh, null);

        initNavigation();
        updateUser();

        if (favoritesFragment == null) {
            Bundle args = new Bundle();
            args.putBoolean(TDConfig.SETTINGS_CHANNEL_NAME, hasUsername);

            favoritesFragment = new FavoritesFragment();
            favoritesFragment.setArgs(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, favoritesFragment)
                    .commit();
        } else {
            replaceFragment(favoritesFragment);
        }
    }

    private void initNavigation() {
        toolbar.setLogo(R.drawable.twitch_logo_white);
        toolbar.setLogoDescription(R.string.app_name);
        setSupportActionBar(toolbar);

        if (drawerLayout != null) {
            toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
            toggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(toggle);
        }

        ListView lstNav = (ListView) findViewById(R.id.lstNav);
        lstNav.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_navigation, getResources().getStringArray(R.array.navigation)));
        lstNav.setOnItemClickListener(this);
    }

    public void updateUser() {
        username = PreferenceManager.getDefaultSharedPreferences(this).getString(TDConfig.SETTINGS_CHANNEL_NAME, "");
        hasUsername = !username.equals("");

        if (hasUsername) {
            user.setVisibility(View.VISIBLE);
            lblUser.setText(username);
            lblNoUser.setVisibility(View.GONE);

            TDTaskManager.executeTask(this);
        } else {
            user.setVisibility(View.GONE);
            lblNoUser.setVisibility(View.VISIBLE);
            imgUser.setImageResource(R.drawable.default_channel_logo_medium);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerLayout != null) {
            toggle.syncState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, true);
    }

    private void replaceFragment(Fragment fragment, boolean backstack) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            if (backstack) {
                transaction.addToBackStack(null);
            }
            transaction.commitAllowingStateLoss();
        }
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

            replaceFragment(searchFragment);
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
        searchItem = menu.findItem(R.id.menu_search);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextFocusChangeListener(this);

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
        }
        return super.onOptionsItemSelected(item);
    }

    public void showChannel(TwitchChannel channel) {
        if (channelFragment == null) {
            channelFragment = new ChannelFragment();
        }

        if (channelFragment.isAdded()) {
            channelFragment.updateChannel(channel);
        } else {
            Bundle arguments = new Bundle();
            arguments.putSerializable(ChannelFragment.CHANNEL, channel);
            channelFragment.setArgs(arguments);
            replaceFragment(channelFragment);
        }
    }

    public void showVideo(Bundle args) {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        videoFragment.setArgs(args);

        if (videoFragment.isAdded()) {
            videoFragment.playVideo();
        } else {
            replaceFragment(videoFragment);
        }
        MenuItemCompat.collapseActionView(searchItem);
    }

    public void showPlaylist(TwitchBroadcast broadcast) {
        if (channelFragment.isAdded()) {
            channelFragment.showPlaylist(broadcast);
        }
    }

    public void showStreams(Bundle args) {
        if (gameStreamsFragment == null) {
            gameStreamsFragment = new GameStreamsFragment();
        }
        gameStreamsFragment.setArgs(args);
        replaceFragment(gameStreamsFragment);
    }

    public void startLoading() {
        isLoading = true;
        if (refreshItem != null) {
            MenuItemCompat.setActionView(refreshItem, refreshView);
        }
    }

    public void stopLoading() {
        isLoading = false;
        if (refreshItem != null) {
            MenuItemCompat.setActionView(refreshItem, null);
        }
    }

    @Override
    public TwitchChannel startRequest() {
        return TDServiceImpl.getInstance().getChannel(username);
    }

    @Override
    public void onResponse(TwitchChannel response) {
        if (response != null) {
            Picasso.with(this)
                    .load(response.getLogo().getUrl(TwitchLogo.Size.MEDIUM))
                    .placeholder(R.drawable.default_channel_logo_medium)
                    .into(imgUser);
        }
    }

    @Override
    public void onError(String title, String message) {
        toast = Toast.makeText(this, title + ": " + message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean isAdded() {
        return true;
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (toolbar != null) {
            toolbar.setTitle(titleId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
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

    @Override
    public void onFocusChange(View view, boolean queryTextFocused) {
        if (!queryTextFocused) {
            MenuItemCompat.collapseActionView(searchItem);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if (favoritesFragment == null) {
                    favoritesFragment = new FavoritesFragment();
                }
                replaceFragment(favoritesFragment);
                break;
            case 1:
                if (gameOverviewFragment == null) {
                    gameOverviewFragment = new GameOverviewFragment();
                }
                replaceFragment(gameOverviewFragment);
                break;
            case 2:
                if (settingsFragment == null) {
                    settingsFragment = new SettingsFragment();
                }
                replaceFragment(settingsFragment);
                break;
        }
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
    }
}
