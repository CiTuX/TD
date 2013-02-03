package ch.citux.twitchdroid.data.model;

import java.util.ArrayList;

public class ChannelStatus extends Base {

    private boolean isStreaming;
    private ArrayList<Stream> streams;

    public boolean isStreaming() {
        return isStreaming;
    }

    public void setStreaming(boolean streaming) {
        isStreaming = streaming;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public void setStreams(ArrayList<Stream> streams) {
        this.streams = streams;
    }
}
