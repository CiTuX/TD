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
