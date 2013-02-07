package ch.citux.twitchdroid.data.model;

import java.util.ArrayList;

public class Channel extends Base {

    private String channel_name;
    private ArrayList<Logo> logos;
    private boolean online;
    private String title;

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getLogo(String size) {
        if (logos != null) {
            for (Logo logo : logos) {
                if (logo.getSize().equals(size)) {
                    return logo.getUrl();
                }
            }
        }
        return null;
    }

    public ArrayList<Logo> getLogos() {
        return logos;
    }

    public void setLogos(ArrayList<Logo> logos) {
        this.logos = logos;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
