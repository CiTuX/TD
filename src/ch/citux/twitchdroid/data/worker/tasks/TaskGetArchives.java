package ch.citux.twitchdroid.data.worker.tasks;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Archives;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.service.TDServiceImpl;
import ch.citux.twitchdroid.data.worker.TDCallback;

public class TaskGetArchives extends TDTask<String, Archives> {


    public TaskGetArchives(TDCallback<Archives> callback) {
        super(callback);
    }

    @Override
    protected Archives doInBackground(String... params) {
        if (params.length == 1) {
            return TDServiceImpl.getInstance().getArchives(params[0]);
        }
        Archives archives = new Archives();
        archives.setErrorResId(R.string.error_unexpected);
        return archives;
    }
}
