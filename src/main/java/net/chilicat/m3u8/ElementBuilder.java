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
class ElementBuilder {
    private int duration;
    private URI uri;
    private PlaylistInfo playlistInfo;
    private EncryptionInfo encryptionInfo;
    private String title;
    private long programDate = -1;

    public ElementBuilder() {

    }

    public long programDate() {
        return programDate;
    }

    public ElementBuilder programDate(long programDate) {
        this.programDate = programDate;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ElementBuilder title(String title) {
        this.title = title;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public ElementBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public ElementBuilder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public ElementBuilder playList(final int programId, final int bandWidth, final String codec, final String video) {
        this.playlistInfo = new ElementImpl.PlaylistInfoImpl(programId, bandWidth, codec, video);
        return this;
    }

    public ElementBuilder resetPlatListInfo() {
        playlistInfo = null;
        return this;
    }

    public ElementBuilder resetEncryptedInfo() {
        encryptionInfo = null;
        return this;
    }

    public ElementBuilder reset() {
        duration = 0;
        uri = null;
        title = null;
        programDate = -1;
        resetEncryptedInfo();
        resetPlatListInfo();
        return this;
    }


    public ElementBuilder encrypted(EncryptionInfo info) {
        this.encryptionInfo = info;
        return this;
    }

    public ElementBuilder encrypted(final URI uri, final String method) {
        encryptionInfo = new ElementImpl.EncryptionInfoImpl(uri, method);
        return this;
    }

    public Element create() {
        return new ElementImpl(playlistInfo, encryptionInfo, duration, uri, title, programDate);
    }

}
