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
package ch.citux.td.util;

import java.util.ArrayList;

import ch.citux.td.data.model.Video;

public class PlaylistGenerator {

    private static final String EXTM3U = "#EXTM3U";
    private static final String EXTINF = "#EXTINF:%d,%s";
    private static final String NEWLINE = System.getProperty("line.separator");

    public static String generateM3U8Playlist(ArrayList<Video> videos) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(EXTM3U)
                .append(NEWLINE);

        for (Video video : videos) {
            builder
                    .append(String.format(EXTINF, video.getDuration(), video.getTitle()))
                    .append(NEWLINE)
                    .append(video.getUrl())
                    .append(NEWLINE);
        }
        return builder.toString();
    }

}
