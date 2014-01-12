package ch.citux.td.data.dto;

import java.util.Date;

public class TwitchVideo {

    private String _id;
    private String title;
    private String description;
    private long broadcast_id;
    private Date recorded_at;
    private String game;
    private long length;
    private String preview;
    private String url;
    private long views;
    private TwitchChannel channel;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBroadcast_id() {
        return broadcast_id;
    }

    public void setBroadcast_id(long broadcast_id) {
        this.broadcast_id = broadcast_id;
    }

    public Date getRecorded_at() {
        return recorded_at;
    }

    public void setRecorded_at(Date recorded_at) {
        this.recorded_at = recorded_at;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public TwitchChannel getChannel() {
        return channel;
    }

    public void setChannel(TwitchChannel channel) {
        this.channel = channel;
    }
}
