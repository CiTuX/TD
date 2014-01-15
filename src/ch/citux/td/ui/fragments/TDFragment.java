package ch.citux.td.ui.fragments;


import android.os.Bundle;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ListFragment;

import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.ui.TDActivity;
import ch.citux.td.ui.dialogs.ErrorDialogFragment;

public abstract class TDFragment<Result> extends ListFragment implements TDCallback<Result> {

    private TDActivity activity;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity != null) {
            activity.showOptions();
        }
    }

    public void startLoading() {
        if (activity != null) {
            activity.startLoading();
        }
    }

    public void stopLoading() {
        if (activity != null) {
            activity.stopLoading();
        }
    }

    @Override
    public void onError(String title, String message) {
        ErrorDialogFragment.ErrorDialogFragmentBuilder builder = new ErrorDialogFragment.ErrorDialogFragmentBuilder(getActivity());
        builder.setTitle(title).setMessage(message).show();
    }

    public void loadData() {
    }

    public void refreshData() {
        loadData();
    }

    public TDActivity getTDActivity() {
        return activity;
    }
}
