package ch.citux.twitchdroid.data.worker;

import android.os.AsyncTask;
import android.os.Build;
import ch.citux.twitchdroid.data.model.Archives;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.worker.tasks.*;

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

    public static TDTask getFavorites(TDCallback<Favorites> callback, String username) {
        TaskGetFavorites task = new TaskGetFavorites(callback);
        task.execute(username);
        tasks.add(task);
        return task;
    }

    public static TDTask getStatus(TDCallback<Channel> callback, String channel) {
        TaskGetChannel task = new TaskGetChannel(callback, true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, channel);
        } else {
            task.execute(channel);
        }
        tasks.add(task);
        return task;
    }

    public static TDTask getChannel(TDCallback<Channel> callback, String channel) {
        TaskGetChannel task = new TaskGetChannel(callback);
        task.execute(channel);
        tasks.add(task);
        return task;
    }

    public static TDTask getArchives(TDCallback<Archives> callback, String channel) {
        TaskGetArchives task = new TaskGetArchives(callback);
        task.execute(channel);
        tasks.add(task);
        return task;
    }

    public static TDTask getStreamPlaylist(TDCallback<StreamPlayList> callback, String channel, boolean hd) {
        TaskGetStreamPlaylist task = new TaskGetStreamPlaylist(callback);
        task.execute(channel, Boolean.toString(hd));
        tasks.add(task);
        return task;
    }
}
