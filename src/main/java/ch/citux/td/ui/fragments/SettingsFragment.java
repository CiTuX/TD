package ch.citux.td.ui.fragments;

import android.os.Bundle;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.preference.PreferenceFragment;

import ch.citux.td.R;
import ch.citux.td.ui.TDActivity;

public class SettingsFragment extends PreferenceFragment {

    private TDActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TDActivity) {
            this.activity = (TDActivity) activity;
        } else {
            throw new IllegalStateException("Fragment must be attached to a TDActivity.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (activity != null) {
            activity.hideOptions();
        }

        addPreferencesFromResource(R.xml.preferences);
    }

}