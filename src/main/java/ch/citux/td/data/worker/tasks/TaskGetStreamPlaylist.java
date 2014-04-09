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

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import ch.citux.td.R;
import ch.citux.td.data.model.StreamPlayList;
import ch.citux.td.data.model.StreamToken;
import ch.citux.td.data.service.TDServiceImpl;
import ch.citux.td.data.worker.TDCallback;

public class TaskGetStreamPlaylist extends TDTask<String, StreamPlayList> {


    public TaskGetStreamPlaylist(TDCallback<StreamPlayList> callback) {
        super(callback);
    }

    @Override
    protected StreamPlayList doInBackground(String... params) {
        StreamPlayList result = new StreamPlayList();
        if (params.length == 1) {
            StreamToken streamToken = TDServiceImpl.getInstance().getStreamToken(params[0]);
            if (StringUtils.isNoneEmpty(streamToken.getNauth(), streamToken.getNauthsig())) {
                Log.d("Twitch", streamToken.toString());
                return TDServiceImpl.getInstance().getStreamPlaylist(params[0], streamToken);
            } else {
                result.setError(streamToken.getError());
                return result;
            }
        }
        result.setErrorResId(R.string.error_unexpected);
        return result;
    }
}
