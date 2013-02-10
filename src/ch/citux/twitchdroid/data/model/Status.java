package ch.citux.twitchdroid.data.model;

import ch.citux.twitchdroid.R;

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
