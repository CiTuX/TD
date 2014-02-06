package ch.citux.td.data.dto;

import java.util.List;

public class TwitchVideos {

    private long _total;
    private List<TwitchVideo> videos;

    public long get_total() {
        return _total;
    }

    public void set_total(long _total) {
        this._total = _total;
    }

    public List<TwitchVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<TwitchVideo> videos) {
        this.videos = videos;
    }
}
