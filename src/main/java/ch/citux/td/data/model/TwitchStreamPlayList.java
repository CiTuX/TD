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

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class TwitchStreamPlayList extends TwitchBase {

    public static final TwitchStreamQuality QUALITY_MOBILE = new TwitchStreamQuality("mobile", 1);
    public static final TwitchStreamQuality QUALITY_LOW = new TwitchStreamQuality("low", 2);
    public static final TwitchStreamQuality QUALITY_MEDIUM = new TwitchStreamQuality("medium", 3);
    public static final TwitchStreamQuality QUALITY_HIGH = new TwitchStreamQuality("high", 4);
    public static final TwitchStreamQuality QUALITY_SOURCE = new TwitchStreamQuality("source", 5);

    public static final TwitchStreamQuality[] SUPPORTED_QUALITIES = {
            QUALITY_MOBILE,
            QUALITY_LOW,
            QUALITY_MEDIUM,
            QUALITY_HIGH,
            QUALITY_SOURCE
    };

    private HashMap<TwitchStreamQuality, String> streams;

    public static TwitchStreamQuality parseQuality(String name) {
        for (TwitchStreamQuality quality : SUPPORTED_QUALITIES) {
            if (quality.getName().equals(name.toLowerCase())) {
                return quality;
            }
        }
        return null;
    }

    public String getStream(TwitchStreamQuality quality) {
        String url = streams.get(quality);
        if (StringUtils.isEmpty(url)) {
            url = getBestStream();
        }
        return url;
    }

    public String getBestStream() {
        TwitchStreamQuality best = new TwitchStreamQuality("", -1);

        for (TwitchStreamQuality quality : streams.keySet()) {
            if (best.getValue() < quality.getValue()) {
                best = quality;
            }
        }

        if (best.getValue() > -1) {
            return getStream(best);
        }
        return null;
    }

    public HashMap<TwitchStreamQuality, String> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<TwitchStreamQuality, String> streams) {
        this.streams = streams;
    }
}
