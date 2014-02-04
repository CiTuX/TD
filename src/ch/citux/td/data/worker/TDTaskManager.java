package ch.citux.td.data.worker;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.CopyOnWriteArrayList;

import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.worker.tasks.TDTask;
import ch.citux.td.data.worker.tasks.TaskGetArchives;
import ch.citux.td.data.worker.tasks.TaskGetChannel;
import ch.citux.td.data.worker.tasks.TaskGetFavorites;
import ch.citux.td.data.worker.tasks.TaskGetStreamPlaylist;
import ch.citux.td.data.worker.tasks.TaskGetVideo;

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

    public static TDTask getFavorites(TDCallback<Follows> callback, String username) {
        TaskGetFavorites task = new TaskGetFavorites(callback);
        task.execute(username);
        tasks.add(task);
        return task;
    }

    @TargetApi(11)
    public static TDTask getStatus(TDCallback<Channel> callback, String channel) {
        TaskGetChannel task = new TaskGetChannel(callback, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
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

    public static TDTask getVideo(TDCallback<Video> callback, String id) {
        TaskGetVideo task = new TaskGetVideo(callback);
        task.execute(id);
        tasks.add(task);
        return task;
    }

    public static TDTask getArchives(TDCallback<Videos> callback, String channel) {
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
