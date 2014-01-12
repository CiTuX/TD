package ch.citux.td.data.worker.tasks;

import ch.citux.td.R;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetChannel extends TDTask<String, Channel> {

    private boolean statusOnly;

    public TaskGetChannel(TDCallback<Channel> callback) {
        super(callback);
    }

    public TaskGetChannel(TDCallback<Channel> callback, boolean statusOnly) {
        super(callback);
        this.statusOnly = statusOnly;
    }

    @Override
    protected Channel doInBackground(String... params) {
        Channel result;
        if (params.length == 1) {
            TDServiceImpl service = TDServiceImpl.getInstance();
            Stream stream = service.getStream(params[0]);
            if (stream.getChannel() != null) {
                result = stream.getChannel();
                result.setStatus(ch.citux.td.data.model.Status.ONLINE);
            } else {
                if (statusOnly) {
                    result = new Channel();
                    result.setName(params[0]);
                } else {
                    result = TDServiceImpl.getInstance().getChannel(params[0]);
                }
                result.setStatus(ch.citux.td.data.model.Status.OFFLINE);
            }
        } else {
            result = new Channel();
            result.setErrorResId(R.string.error_unexpected);
        }
        return result;
    }
}
