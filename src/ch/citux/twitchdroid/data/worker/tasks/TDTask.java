package ch.citux.twitchdroid.data.worker.tasks;

import android.content.Context;
import android.os.AsyncTask;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.TDApplication;
import ch.citux.twitchdroid.data.model.Base;
import ch.citux.twitchdroid.data.worker.TDCallback;
import ch.citux.twitchdroid.data.worker.TDTaskManager;

public abstract class TDTask<Params, Result extends Base> extends AsyncTask<Params, Void, Result> {

    protected Context context;
    protected TDCallback<Result> callback;

    public TDTask(TDCallback<Result> callback) {
        this.callback = callback;
        this.context = TDApplication.getContext();
    }

    @Override
    protected void onPreExecute() {
        callback.startLoading();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null) {
            if (!result.hasError()) {
                callback.onResponse(result);
            } else {
                callback.onError(getString(R.string.dialog_error_title), result.getError());
            }

        } else {
            callback.onError(getString(R.string.error_connection_error_title), getString(R.string.error_connection_error_message));
        }
        callback.stopLoading();
        TDTaskManager.removeTask(this);
    }

    protected String getString(int resId) {
        return context.getString(resId);
    }
}
