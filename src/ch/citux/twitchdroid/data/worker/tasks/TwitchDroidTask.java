package ch.citux.twitchdroid.data.worker.tasks;

import android.content.Context;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.TDApplication;
import ch.citux.twitchdroid.data.model.Base;
import ch.citux.twitchdroid.data.worker.TDCallback;
import com.yixia.zi.utils.AsyncTask;

public abstract class TwitchDroidTask<Params, Result extends Base> extends AsyncTask<Params, Void, Result> {

    protected Context context;
    protected TDCallback<Result> callback;

    public TwitchDroidTask(TDCallback<Result> callback) {
        this.callback = callback;
        this.context = TDApplication.getContext();
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
    }

    protected String getString(int resId) {
        return context.getString(resId);
    }
}
