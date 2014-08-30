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

package ch.citux.td.license;

import android.content.Context;

import ch.citux.td.R;
import de.psdev.licensesdialog.licenses.License;

public class VitamioLicense extends License {

    @Override
    public String getName() {
        return "Vitamio License";
    }

    @Override
    public String getSummaryText(final Context context) {
        return getContent(context, R.raw.vitamio_summary);
    }

    @Override
    public String getFullText(final Context context) {
        return getContent(context, R.raw.vitamio_full);
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getUrl() {
        return "http://www.vitamio.org";
    }
}