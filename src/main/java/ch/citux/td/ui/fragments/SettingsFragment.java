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
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (activity != null) {
            activity.hideActionItems();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (activity != null) {
            activity.showActionItems();
        }
    }
}