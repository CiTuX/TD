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
package ch.citux.td.data.worker.tasks;

import ch.citux.td.R;
import ch.citux.td.data.model.Follows;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetFavorites extends TDTask<String, Follows> {


    public TaskGetFavorites(TDCallback<Follows> callback) {
        super(callback);
    }

    @Override
    protected Follows doInBackground(String... params) {
        if (params.length == 1) {
            return TDServiceImpl.getInstance().getFollows(params[0]);
        }
        Follows follows = new Follows();
        follows.setErrorResId(R.string.error_unexpected);
        return follows;
    }
}
