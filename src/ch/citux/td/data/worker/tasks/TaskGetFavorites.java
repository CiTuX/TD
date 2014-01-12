package ch.citux.td.data.worker.tasks;

import ch.citux.td.R;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetFavorites extends TDTask<String, Follows> {


    public TaskGetFavorites(TDCallback<Follows> callback) {
        super(callback);
    }

    @Override
    protected Follows doInBackground(String... params) {
        if (params.length == 1) {
            return TDServiceImpl.getInstance().getFollows(params[0]);
        }
        Follows follows = new Follows();
        follows.setErrorResId(R.string.error_unexpected);
        return follows;
    }
}
