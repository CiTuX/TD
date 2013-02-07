package ch.citux.twitchdroid.data.worker;

import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.tasks.TDTask;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetChannel;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetFavorites;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetStreamPlaylist;

public class TDTaskManager {

    private static TDTask currentTask;

    public static void cancelTask() {
        if (currentTask != null) {
            currentTask.cancel(true);
        }
    }

    public static void getFavorites(TDCallback<Favorites> callback, String username) {
        cancelTask();
        TaskGetFavorites task = new TaskGetFavorites(callback);
        task.execute(username);
        currentTask = task;
    }

    public static void getChannel(TDCallback<Channel> callback, String channel) {
        cancelTask();
        TaskGetChannel task = new TaskGetChannel(callback);
        task.execute(channel);
        currentTask = task;
    }

    public static void getStreamPlaylist(TDCallback<StreamPlayList> callback, String channel, boolean hd) {
        cancelTask();
        TaskGetStreamPlaylist task = new TaskGetStreamPlaylist(callback);
        task.execute(channel, Boolean.toString(hd));
        currentTask = task;
    }
}
