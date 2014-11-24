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
package ch.citux.td.data.model;

import java.util.List;

import ch.citux.td.R;


public class TwitchChannel extends TwitchBase {

    private int _id;
    private String background;
    private String banner;
    private Status channelStatus;
    private Long delay;
    private String display_name;
    private long followers;
    private String game;
    private TwitchLogo logo;
    private boolean mature;
    private String name;
    private boolean partner;
    private List<TwitchChannel> teams;
    private String title;
    private String status;
    private String url;
    private String video_banner;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Status getChannelStatus() {
        if (channelStatus == null) {
            channelStatus = Status.UNKNOWN;
        }
        return channelStatus;
    }

    public void setChannelStatus(Status channelStatus) {
        this.channelStatus = channelStatus;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public TwitchLogo getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = new TwitchLogo(logo);
    }

    public boolean isMature() {
        return mature;
    }

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    public List<TwitchChannel> getTeams() {
        return teams;
    }

    public void setTeams(List<TwitchChannel> teams) {
        this.teams = teams;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideo_banner() {
        return video_banner;
    }

    public void setVideo_banner(String video_banner) {
        this.video_banner = video_banner;
    }

    public enum Status {
        ONLINE,
        OFFLINE,
        UNKNOWN;

        public int getText() {
            switch (this) {
                case ONLINE:
                    return R.string.channel_online;
                case OFFLINE:
                    return R.string.channel_offline;
            }
            return R.string.channel_unknown;
        }
    }
}
