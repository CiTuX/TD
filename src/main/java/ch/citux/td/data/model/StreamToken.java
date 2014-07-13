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

public class StreamToken extends Base {

    private int p;
    private String nauth;
    private String nauthsig;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getNauth() {
        return nauth;
    }

    public void setNauth(String nauth) {
        this.nauth = nauth;
    }

    public String getNauthsig() {
        return nauthsig;
    }

    public void setNauthsig(String nauthsig) {
        this.nauthsig = nauthsig;
    }

    @Override
    public String toString() {
        return "nauth: " + nauth + "nauthsig: " + nauthsig + "p: " + p;
    }
}
