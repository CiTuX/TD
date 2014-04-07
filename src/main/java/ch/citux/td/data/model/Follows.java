package ch.citux.td.data.model;

import android.util.SparseArray;

public class Follows extends Base {

    private SparseArray<Channel> channels;

    public SparseArray<Channel> getChannels() {
        return channels;
    }

    public void setChannels(SparseArray<Channel> channels) {
        this.channels = channels;
    }
}
