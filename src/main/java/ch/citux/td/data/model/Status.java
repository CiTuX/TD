package ch.citux.td.data.model;

import ch.citux.td.R;

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
