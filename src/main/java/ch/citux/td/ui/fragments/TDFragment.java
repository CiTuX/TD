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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;
import ch.citux.td.ui.widget.EmptyView;

public abstract class TDFragment<Result> extends ListFragment implements TDCallback<Result> {

    private TDActivity activity;
    protected boolean hasUsername;
    @Optional @InjectView(android.R.id.empty) EmptyView emptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hasUsername = getArguments().getBoolean(TDConfig.SETTINGS_CHANNEL_NAME);
        }
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
        View view = inflater.inflate(onCreateView());
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

    @Override
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
        ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
        builder.setTitle(title).setMessage(message).show();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (getListView() != null) {
            getListView().setOnItemClickListener(onItemClickListener);
        }
    }

    public abstract void loadData();

    public void refreshData() {
        loadData();
    }

    public TDActivity getTDActivity() {
        return activity;
    }
}
