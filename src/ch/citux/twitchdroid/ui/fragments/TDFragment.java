package ch.citux.twitchdroid.ui.fragments;

import android.app.Activity;
import ch.citux.twitchdroid.data.worker.TDCallback;
import ch.citux.twitchdroid.ui.TDActivity;
import com.actionbarsherlock.app.SherlockListFragment;

public abstract class TDFragment<Result> extends SherlockListFragment implements TDCallback<Result> {

    private TDActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TDActivity) {
            this.activity = (TDActivity) activity;
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
    }

    public void loadData() {
    }

    public void refreshData() {
        loadData();
    }

}
