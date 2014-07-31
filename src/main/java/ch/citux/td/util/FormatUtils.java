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

import android.text.format.DateFormat;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import ch.citux.td.TDApplication;

public class FormatUtils {

    public static String formateDate(Date date) {
        StringBuilder result = new StringBuilder();
        result.append(DateFormat.getDateFormat(TDApplication.getContext()).format(date));
        result.append(" ");
        result.append(DateFormat.getTimeFormat(TDApplication.getContext()).format(date));
        return result.toString();
    }

    public static String formatTime(long duration) {
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.ENGLISH, "%d:%02d", minutes, seconds);
        }
    }

    public static String formatNumber(long number) {
        return NumberFormat.getInstance().format(number);
    }
}