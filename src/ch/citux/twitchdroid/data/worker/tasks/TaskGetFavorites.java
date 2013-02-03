package ch.citux.twitchdroid.data.worker.tasks;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.service.TwitchDroidServiceImpl;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;

public class TaskGetFavorites extends TwitchDroidTask<String, Favorites> {


    public TaskGetFavorites(TwitchDroidCallback<Favorites> callback) {
        super(callback);
    }

    @Override
    protected Favorites doInBackground(String... params) {
        if (params.length == 1) {
            return TwitchDroidServiceImpl.getInstance().getFavorites(params[0]);
        }
        Favorites favorites = new Favorites();
        favorites.setErrorResId(R.string.error_unexpected);
        return favorites;
    }
}
