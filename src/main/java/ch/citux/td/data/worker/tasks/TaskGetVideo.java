package ch.citux.td.data.worker.tasks;

import ch.citux.td.R;
import ch.citux.td.data.model.Video;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetVideo extends TDTask<String, Video> {


    public TaskGetVideo(TDCallback<Video> callback) {
        super(callback);
    }

    @Override
    protected Video doInBackground(String... params) {
        if (params.length == 1) {
            return TDServiceImpl.getInstance().getVideo(params[0]);
        }
        Video video = new Video();
        video.setErrorResId(R.string.error_unexpected);
        return video;
    }
}
