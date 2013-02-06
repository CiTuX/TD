package ch.citux.twitchdroid.data.dto;

import java.util.List;

public class TwitchStream {

    private List<TwitchStreamElement> stream;
    private List<TwitchStreamElement> streams;

    public List<TwitchStreamElement> getStream() {
        return stream;
    }

    public void setStream(List<TwitchStreamElement> stream) {
        this.stream = stream;
    }

    public List<TwitchStreamElement> getStreams() {
        return streams;
    }

    public void setStreams(List<TwitchStreamElement> streams) {
        this.streams = streams;
    }
}
