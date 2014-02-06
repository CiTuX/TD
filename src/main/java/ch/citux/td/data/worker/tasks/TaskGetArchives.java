package ch.citux.td.data.worker.tasks;

import ch.citux.td.R;
import ch.citux.td.data.model.Videos;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetArchives extends TDTask<String, Videos> {


    public TaskGetArchives(TDCallback<Videos> callback) {
        super(callback);
    }

    @Override
    protected Videos doInBackground(String... params) {
        if (params.length == 1) {
            return TDServiceImpl.getInstance().getVideos(params[0]);
        }
        Videos videos = new Videos();
        videos.setErrorResId(R.string.error_unexpected);
        return videos;
    }
}
