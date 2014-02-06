package ch.citux.td.data.dto;

import java.util.List;

public class TwitchFollows {

    private List<TwitchChannels> follows;

    public List<TwitchChannels> getFollows() {
        return follows;
    }

    public void setFollows(List<TwitchChannels> follows) {
        this.follows = follows;
    }
}
