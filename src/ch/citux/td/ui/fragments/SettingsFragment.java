package ch.citux.td.ui.fragments;

import android.os.Bundle;

import org.holoeverywhere.preference.PreferenceFragment;

import ch.citux.td.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        addPreferencesFromResource(R.xml.preferences);
    }

}