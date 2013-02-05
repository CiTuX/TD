package ch.citux.twitchdroid.data.worker.tasks;

import android.content.Context;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.TwitchDroidApplication;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;
import com.yixia.zi.utils.AsyncTask;

public abstract class TwitchDroidTask<Params, Result> extends AsyncTask<Params, Void, Result> {

    protected Context context;
    protected TwitchDroidCallback<Result> callback;

    public TwitchDroidTask(TwitchDroidCallback<Result> callback) {
        this.callback = callback;
        this.context = TwitchDroidApplication.getContext();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null) {
            callback.onResponse(result);
        } else {
            callback.onError(getString(R.string.error_connection_error_title), getString(R.string.error_connection_error_message));
        }
    }

    protected String getString(int resId) {
        return context.getString(resId);
    }

//    @Override
//    protected void onCancelled(Result result) {
//        super.onCancelled(result);
//        callback.onError(result.toString());
//    }
//
//    @Override
//    protected void onCancelled() {
//        super.onCancelled();
//        callback.onError(TwitchDroidApplication.getContext().getString(R.string.dialog_error_connection_error_message));
//    }

}
