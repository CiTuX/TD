package ch.citux.twitchdroid.ui.fragments;

import android.os.Bundle;

import org.holoeverywhere.preference.PreferenceFragment;

import ch.citux.twitchdroid.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.action_settings);
        addPreferencesFromResource(R.xml.preferences);
    }

}