package ch.citux.twitchdroid.data.worker;

import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.tasks.TDTask;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetChannel;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetFavorites;
import ch.citux.twitchdroid.data.worker.tasks.TaskGetStreamPlaylist;

import java.util.concurrent.CopyOnWriteArrayList;

public class TDTaskManager {

    private static CopyOnWriteArrayList<TDTask> tasks = new CopyOnWriteArrayList<TDTask>();

    public static void removeTask(TDTask task) {
        removeTask(task, false);
    }

    public static void removeTask(TDTask task, boolean cancel) {
        if (cancel) {
            task.cancel(true);
        }
        tasks.remove(task);
    }

    public static void cancelAllTasks() {
        for (TDTask task : tasks) {
            removeTask(task, true);
        }
    }

    public static void getFavorites(TDCallback<Favorites> callback, String username) {
        TaskGetFavorites task = new TaskGetFavorites(callback);
        task.execute(username);
        tasks.add(task);
    }

    public static void getStatus(TDCallback<Channel> callback, String channel) {
        TaskGetChannel task = new TaskGetChannel(callback, true);
        task.execute(channel);
        tasks.add(task);
    }

    public static void getChannel(TDCallback<Channel> callback, String channel) {
        TaskGetChannel task = new TaskGetChannel(callback);
        task.execute(channel);
        tasks.add(task);
    }

    public static void getStreamPlaylist(TDCallback<StreamPlayList> callback, String channel, boolean hd) {
        TaskGetStreamPlaylist task = new TaskGetStreamPlaylist(callback);
        task.execute(channel, Boolean.toString(hd));
        tasks.add(task);
    }
}
