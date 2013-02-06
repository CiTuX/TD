package ch.citux.twitchdroid.data.worker.tasks;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.service.TwitchDroidServiceImpl;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;

public class TaskGetChannel extends TwitchDroidTask<String, Channel> {


    public TaskGetChannel(TwitchDroidCallback<Channel> callback) {
        super(callback);
    }

    @Override
    protected Channel doInBackground(String... params) {
        if (params.length == 1) {
            return TwitchDroidServiceImpl.getInstance().getChannel(params[0]);
        }
        Channel status = new Channel();
        status.setErrorResId(R.string.error_unexpected);
        return status;
    }
}
