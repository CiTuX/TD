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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.widget.EmptyView;

public abstract class TDFragment<Result> extends Fragment implements TDBase, TDCallback<Result> {

    private TDActivity activity;
    private Bundle args;
    protected boolean hasUsername;
    @Optional @InjectView(android.R.id.empty) EmptyView emptyView;

    public static <T extends Fragment> T instantiate(Class<T> clazz) {
        return instantiate(clazz, null);
    }

    public static <T extends Fragment> T instantiate(Class<T> clazz, Bundle args) {
        try {
            T fragment = clazz.newInstance();
            if (args != null) {
                args.setClassLoader(clazz.getClassLoader());
                fragment.setArguments(args);
            }
            return fragment;
        } catch (Exception e) {
            throw new InstantiationException("Unable to instantiate fragment " + clazz
                    + ": make sure class name exists, is public, and has an"
                    + " empty constructor that is public", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasUsername = getArgs().getBoolean(TDConfig.SETTINGS_CHANNEL_NAME);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TDActivity) {
            this.activity = (TDActivity) activity;
        } else {
            throw new IllegalStateException("TDFragment must be attached to a TDActivity.");
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(onCreateView(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    protected abstract int onCreateView();

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActionBar().setTitle("");
    }

    protected EmptyView getEmptyView() {
        return emptyView;
    }

    public void startLoading() {
        if (activity != null) {
            activity.startLoading();
            if (emptyView != null) {
                emptyView.showProgress();
            }
        }
    }

    public void stopLoading() {
        if (activity != null) {
            activity.stopLoading();
        }
        if (emptyView != null) {
            emptyView.showText();
        }
    }

    @Override
    public void onError(String title, String message) {
        activity.onError(title, message);
    }

    public abstract void loadData();

    public void refreshData() {
        loadData();
    }

    public TDActivity getTDActivity() {
        return activity;
    }

    public ActionBar getSupportActionBar() {
        return activity.getSupportActionBar();
    }

    @Override
    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(activity);
    }

    @Override
    public void setArgs(Bundle args) {
        this.args = args;
    }

    @Override
    public Bundle getArgs() {
        if (args == null) {
            args = new Bundle();
        }
        return args;
    }
}
