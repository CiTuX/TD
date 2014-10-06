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

public class Game extends Base {

    private long id;
    private String name;
    private String  box;
    private String  logo;
    private long viewers;
    private long channels;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public long getChannels() {
        return channels;
    }

    public void setChannels(long channels) {
        this.channels = channels;
    }
}
