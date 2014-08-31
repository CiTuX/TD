/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.citux.td.data.worker;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.CopyOnWriteArrayList;

import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.model.SearchChannels;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.VideoPlaylist;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.worker.tasks.TDTask;
import ch.citux.td.data.worker.tasks.TaskGetArchives;
import ch.citux.td.data.worker.tasks.TaskGetChannel;
import ch.citux.td.data.worker.tasks.TaskGetFavorites;
import ch.citux.td.data.worker.tasks.TaskGetStreamPlaylist;
import ch.citux.td.data.worker.tasks.TaskGetVideoPlaylist;
import ch.citux.td.data.worker.tasks.TaskSearchChannels;
import ch.citux.td.data.worker.tasks.TaskSearchStreams;

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

    public static TDTask getVideoPlaylist(TDCallback<VideoPlaylist> callback, String id) {
        TaskGetVideoPlaylist task = new TaskGetVideoPlaylist(callback);
        task.execute(id);
        tasks.add(task);
        return task;
    }

    public static TDTask getArchives(TDCallback<Videos> callback, String channel, int offset) {
        TaskGetArchives task = new TaskGetArchives(callback);
        task.execute(channel, offset);
        tasks.add(task);
        return task;
    }

    public static TDTask getStreamPlaylist(TDCallback<StreamPlayList> callback, String channel) {
        TaskGetStreamPlaylist task = new TaskGetStreamPlaylist(callback);
        task.execute(channel);
        tasks.add(task);
        return task;
    }

    public static TDTask searchStreams(TDCallback<SearchStreams> callback, String query, int offset) {
        TaskSearchStreams task = new TaskSearchStreams(callback);
        task.execute(query, String.valueOf(offset));
        tasks.add(task);
        return task;
    }

    public static TDTask searchChannels(TDCallback<SearchChannels> callback, String query, int offset) {
        TaskSearchChannels task = new TaskSearchChannels(callback);
        task.execute(query, String.valueOf(offset));
        tasks.add(task);
        return task;
    }
}
