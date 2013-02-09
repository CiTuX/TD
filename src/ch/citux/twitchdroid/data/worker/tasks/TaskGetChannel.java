package ch.citux.twitchdroid.data.worker.tasks;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Stream;
import ch.citux.twitchdroid.data.service.TDServiceImpl;
import ch.citux.twitchdroid.data.worker.TDCallback;

public class TaskGetChannel extends TDTask<String, Channel> {


    public TaskGetChannel(TDCallback<Channel> callback) {
        super(callback);
    }

    @Override
    protected Channel doInBackground(String... params) {
        if (params.length == 1) {
            TDServiceImpl service = TDServiceImpl.getInstance();
            Stream stream = service.getStream(params[0]);
            if (stream.getChannel() != null) {
                Channel channel = stream.getChannel();
                channel.setOnline(true);
                return channel;
            } else {
                return TDServiceImpl.getInstance().getChannel(params[0]);
            }

        }
        Channel status = new Channel();
        status.setErrorResId(R.string.error_unexpected);
        return status;
    }
}
