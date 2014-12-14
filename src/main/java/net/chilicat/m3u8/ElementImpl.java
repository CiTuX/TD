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
package net.chilicat.m3u8;

import java.net.URI;

/**
 * @author dkuffner
 */
final class ElementImpl implements Element {
    private final PlaylistInfo playlistInfo;
    private final EncryptionInfo encryptionInfo;
    private final int duration;
    private final URI uri;
    private final String title;
    private final long programDate;

    public ElementImpl(PlaylistInfo playlistInfo, EncryptionInfo encryptionInfo, int duration, URI uri, String title, long programDate) {
        if (uri == null) {
            throw new NullPointerException("uri");
        }

        if (duration < -1) {
            throw new IllegalArgumentException();
        }
        if (playlistInfo != null && encryptionInfo != null) {
            throw new IllegalArgumentException("Element cannot be a encrypted playlist.");
        }
        this.playlistInfo = playlistInfo;
        this.encryptionInfo = encryptionInfo;
        this.duration = duration;
        this.uri = uri;
        this.title = title;
        this.programDate = programDate;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public URI getURI() {
        return uri;
    }

    public boolean isEncrypted() {
        return encryptionInfo != null;
    }

    public boolean isPlayList() {
        return playlistInfo != null;
    }

    public boolean isMedia() {
        return playlistInfo == null;
    }

    public EncryptionInfo getEncryptionInfo() {
        return encryptionInfo;
    }

    public PlaylistInfo getPlayListInfo() {
        return playlistInfo;
    }

    public long getProgramDate() {
        return programDate;
    }

    @Override
    public String toString() {
        return "ElementImpl{" +
                "playlistInfo=" + playlistInfo +
                ", encryptionInfo=" + encryptionInfo +
                ", duration=" + duration +
                ", uri=" + uri +
                ", title='" + title + '\'' +
                '}';
    }

    static final class PlaylistInfoImpl implements PlaylistInfo {
        private final int programId;
        private final int bandWidth;
        private final String codec;
        private final String video;

        public PlaylistInfoImpl(int programId, int bandWidth, String codec, String video) {
            this.programId = programId;
            this.bandWidth = bandWidth;
            this.codec = codec;
            this.video = video;
        }

        public int getProgramId() {
            return programId;
        }

        public int getBandWitdh() {
            return bandWidth;
        }

        public String getCodecs() {
            return codec;
        }

        public String getVideo() {
            return video;
        }

        @Override
        public String toString() {
            return "PlaylistInfoImpl{" +
                    "programId=" + programId +
                    ", bandWidth=" + bandWidth +
                    ", codec='" + codec + '\'' +
                    ", video='" + video + '\'' +
                    '}';
        }
    }

    static final class EncryptionInfoImpl implements EncryptionInfo {
        private final URI uri;
        private final String method;

        public EncryptionInfoImpl(URI uri, String method) {
            this.uri = uri;
            this.method = method;
        }

        public URI getURI() {
            return uri;
        }

        public String getMethod() {
            return method;
        }

        @Override
        public String toString() {
            return "EncryptionInfoImpl{" +
                    "uri=" + uri +
                    ", method='" + method + '\'' +
                    '}';
        }
    }
}
