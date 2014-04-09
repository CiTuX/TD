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

import android.content.Context;
import android.os.AsyncTask;

import ch.citux.td.R;
import ch.citux.td.TDApplication;
import ch.citux.td.data.model.Base;
import ch.citux.td.data.worker.TDCallback;
import ch.citux.td.data.worker.TDTaskManager;

public abstract class TDTask<Params, Result extends Base> extends AsyncTask<Params, Void, Result> {

    protected Context context;
    protected TDCallback<Result> callback;

    public TDTask(TDCallback<Result> callback) {
        this.callback = callback;
        this.context = TDApplication.getContext();
    }

    @Override
    protected void onPreExecute() {
        callback.startLoading();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null) {
            if (!result.hasError()) {
                if (callback.isAdded()) {
                    callback.onResponse(result);
                }
            } else {
                callback.onError(getString(R.string.dialog_error_title), result.getError());
            }

        } else {
            callback.onError(getString(R.string.error_connection_error_title), getString(R.string.error_connection_error_message));
        }
        callback.stopLoading();
        TDTaskManager.removeTask(this);
    }

    protected String getString(int resId) {
        return context.getString(resId);
    }
}
