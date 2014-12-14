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
package ch.citux.td.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import ch.citux.td.R;
import ch.citux.td.config.TDConfig;
import ch.citux.td.ui.TDActivity;
import de.psdev.licensesdialog.LicensesDialogFragment;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

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

        addPreferencesFromResource(R.xml.preferences);
        findPreference(TDConfig.SETTINGS_LICENSE_DIALOG).setOnPreferenceClickListener(this);
        findPreference(TDConfig.SETTINGS_CHANNEL_NAME).setOnPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.updateUser();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        LicensesDialogFragment
                .newInstance(R.raw.notices, false, true)
                .show(getActivity().getSupportFragmentManager(), "licenses");
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        activity.updateUser();
        return true;
    }
}