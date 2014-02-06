package ch.citux.td.data.model;

import java.util.ArrayList;

public class Follows extends Base {

    private ArrayList<Channel> channels;

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }
}
