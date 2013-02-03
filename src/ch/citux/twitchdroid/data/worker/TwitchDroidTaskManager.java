package ch.citux.twitchdroid.data.worker;

import ch.citux.twitchdroid.data.model.ChannelStatus;
import ch.citux.twitchdroid.data.model.Favorites;
import ch.citux.twitchdroid.data.model.StreamPlayList;
import ch.citux.twitchdroid.data.model.StreamToken;
import ch.citux.twitchdroid.data.worker.tasks.*;

public class TwitchDroidTaskManager {

    private static TwitchDroidTask currentTask;

    public static void cancelTask() {
        if (currentTask != null) {
            currentTask.cancel(true);
        }
    }

    public static void getFavorites(TwitchDroidCallback<Favorites> callback, String username) {
        cancelTask();
        TaskGetFavorites task = new TaskGetFavorites(callback);
        task.execute(username);
        currentTask = task;
    }

    public static void getStreams(TwitchDroidCallback<ChannelStatus> callback, String channel) {
        cancelTask();
        TaskGetChannelStatus task = new TaskGetChannelStatus(callback);
        task.execute(channel);
        currentTask = task;
    }

    public static void getStreamPlaylist(TwitchDroidCallback<StreamPlayList> callback, String channel, boolean hd) {
        cancelTask();
        TaskGetStreamPlaylist task = new TaskGetStreamPlaylist(callback);
        task.execute(channel, Boolean.toString(hd));
        currentTask = task;
    }
}
