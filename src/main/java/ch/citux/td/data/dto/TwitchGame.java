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
package ch.citux.td.data.dto;

public class TwitchGame {

    private String name;
    private TwitchPicture box;
    private TwitchPicture logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TwitchPicture getBox() {
        return box;
    }

    public void setBox(TwitchPicture box) {
        this.box = box;
    }

    public TwitchPicture getLogo() {
        return logo;
    }

    public void setLogo(TwitchPicture logo) {
        this.logo = logo;
    }
}
