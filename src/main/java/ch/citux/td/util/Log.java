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

import org.apache.commons.lang3.StringUtils;

import ch.citux.td.BuildConfig;

public class Log {

    public static void d(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.d(tag, message);
        }
    }

    public static void d(Object caller, String message) {
        String tag = caller.getClass().getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.d(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.v(tag, message);
        }
    }

    public static void v(Object caller, String message) {
        String tag = caller.getClass().getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.v(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (!StringUtils.isBlank(tag) && !StringUtils.isBlank(message)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(Class caller, String message) {
        String tag = caller.getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(Object caller, String message) {
        String tag = caller.getClass().getSimpleName();
        if (!StringUtils.isBlank(message)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(String tag, Exception exception) {
        if (exception != null && !StringUtils.isBlank(exception.getMessage())) {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            } else {
                android.util.Log.e(tag, exception.getMessage());
            }
        }
    }

    public static void e(Class caller, Exception exception) {
        String tag = caller.getSimpleName();
        if (exception != null && !StringUtils.isBlank(exception.getMessage())) {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            } else {
                android.util.Log.e(tag, exception.getMessage());
            }
        }
    }
}