package ch.citux.twitchdroid.data.model;

import java.util.ArrayList;

public class Favorites extends Base {

    private ArrayList<Channel> channels;

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }
}
