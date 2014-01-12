
package ch.citux.td.data.dto;

import java.util.Date;

public class JustinArchive extends JustinBase {

    private String audio_codec;
    private String broadcast_id;
    private String broadcast_part;
    private String broadcaster;
    private Date created_on;
    private boolean deleted_by_user;
    private String file_name;
    private long file_size;
    private String filtered;
    private long id;
    private String image_url_medium;
    private String keyframes;
    private String last_part;
    private long length;
    private String origin_name;
    private boolean save_forever;
    private long servers;
    private String start_time;
    private String stream_name;
    private String tag_list;
    private String title;
    private Date updated_on;
    private long user_id;
    private String video_bitrate;
    private String video_codec;
    private String video_file_url;
    private int video_rotation;

    public String getAudio_codec() {
        return this.audio_codec;
    }

    public void setAudio_codec(String audio_codec) {
        this.audio_codec = audio_codec;
    }

    public String getBroadcast_id() {
        return this.broadcast_id;
    }

    public void setBroadcast_id(String broadcast_id) {
        this.broadcast_id = broadcast_id;
    }

    public String getBroadcast_part() {
        return this.broadcast_part;
    }

    public void setBroadcast_part(String broadcast_part) {
        this.broadcast_part = broadcast_part;
    }

    public String getBroadcaster() {
        return this.broadcaster;
    }

    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    public boolean isSave_forever() {
        return save_forever;
    }

    public void setSave_forever(boolean save_forever) {
        this.save_forever = save_forever;
    }

    public boolean isDeleted_by_user() {
        return deleted_by_user;
    }

    public void setDeleted_by_user(boolean deleted_by_user) {
        this.deleted_by_user = deleted_by_user;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getFile_name() {
        return this.file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public String getFiltered() {
        return this.filtered;
    }

    public void setFiltered(String filtered) {
        this.filtered = filtered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage_url_medium() {
        return this.image_url_medium;
    }

    public void setImage_url_medium(String image_url_medium) {
        this.image_url_medium = image_url_medium;
    }

    public String getKeyframes() {
        return this.keyframes;
    }

    public void setKeyframes(String keyframes) {
        this.keyframes = keyframes;
    }

    public String getLast_part() {
        return this.last_part;
    }

    public void setLast_part(String last_part) {
        this.last_part = last_part;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getOrigin_name() {
        return this.origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public long getServers() {
        return servers;
    }

    public void setServers(long servers) {
        this.servers = servers;
    }

    public String getStart_time() {
        return this.start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStream_name() {
        return this.stream_name;
    }

    public void setStream_name(String stream_name) {
        this.stream_name = stream_name;
    }

    public String getTag_list() {
        return this.tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getVideo_bitrate() {
        return this.video_bitrate;
    }

    public void setVideo_bitrate(String video_bitrate) {
        this.video_bitrate = video_bitrate;
    }

    public String getVideo_codec() {
        return this.video_codec;
    }

    public void setVideo_codec(String video_codec) {
        this.video_codec = video_codec;
    }

    public String getVideo_file_url() {
        return this.video_file_url;
    }

    public void setVideo_file_url(String video_file_url) {
        this.video_file_url = video_file_url;
    }

    public int getVideo_rotation() {
        return video_rotation;
    }

    public void setVideo_rotation(int video_rotation) {
        this.video_rotation = video_rotation;
    }
}
