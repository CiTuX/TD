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
package ch.citux.td.data.worker;

import ch.citux.td.ui.fragments.TDFragment;

public abstract class TDBasicCallback<Result> implements TDCallback<Result> {

    private Object caller;

    protected TDBasicCallback(Object caller) {
        this.caller = caller;
    }

    @Override
    public void startLoading() {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).startLoading();
        }
    }

    @Override
    public void stopLoading() {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).stopLoading();
        }
    }

    @Override
    public void onError(String title, String message) {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).onError(title, message);
        }
    }
}
