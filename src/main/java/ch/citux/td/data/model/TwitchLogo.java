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

import java.util.HashMap;

public class TwitchLogo extends TwitchBase {

    public enum Size {
        HUGE("-600x600"),
        LARGE("-300x300"),
        MEDIUM("-150x150"),
        SMALL("-70x70"),
        TINY("-50x50");

        private String size;

        private Size(String size) {
            this.size = size;
        }
    }

    private HashMap<Size, String> logos;

    public TwitchLogo() {
        logos = new HashMap<Size, String>();
    }

    public TwitchLogo(String url) {
        logos = new HashMap<Size, String>();
        parseUrl(url);
    }

    private void parseUrl(String url) {
        if (url != null) {
            for (Size size : Size.values()) {
                logos.put(size, url.replaceAll("-(\\d+)x(\\d+)", size.size));
            }
        }
    }

    public void setUrl(String url) {
        parseUrl(url);
    }

    public String getUrl(Size size) {
        return logos.get(size);
    }
}