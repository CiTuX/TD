package ch.citux.twitchdroid.data.worker.tasks;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.ChannelStatus;
import ch.citux.twitchdroid.data.service.TwitchDroidServiceImpl;
import ch.citux.twitchdroid.data.worker.TwitchDroidCallback;

public class TaskGetChannelStatus extends TwitchDroidTask<String, ChannelStatus> {


    public TaskGetChannelStatus(TwitchDroidCallback<ChannelStatus> callback) {
        super(callback);
    }

    @Override
    protected ChannelStatus doInBackground(String... params) {
        if (params.length == 1) {
            return TwitchDroidServiceImpl.getInstance().getChannelStatus(params[0]);
        }
        ChannelStatus status = new ChannelStatus();
        status.setErrorResId(R.string.error_unexpected);
        return status;
    }
}
