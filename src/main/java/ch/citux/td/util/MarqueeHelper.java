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

import android.text.TextUtils;
import android.widget.TextView;

public class MarqueeHelper {

    public static void setupMarquee(TextView textView, String text) {
        textView.setText(text);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine();
        textView.setSelected(true);
    }
}
