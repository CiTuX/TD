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

import net.chilicat.m3u8.ParseException;
import net.chilicat.m3u8.Playlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.Response;
import ch.citux.td.util.Log;
import hugo.weaving.DebugLog;

public class TDRequestHandler {

    private static final String TAG = "TDRequestHandler";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String HEADER_ACCEPT = "Accept";

    @DebugLog
    public static Response<String> startStringRequest(String request) {
        Response.Status status;
        String result = "";
        Response<InputStream> response = startRequest(request);
        if (response.getStatus() == Response.Status.OK) {
            try {
                result = readString(response.getResult());
                status = Response.Status.OK;
            } catch (IOException e) {
                Log.e(TAG, e);
                status = Response.Status.ERROR_CONNECTION;
            }
        } else {
            status = response.getStatus();
        }
        return new Response<String>(status, result);
    }

    @DebugLog
    public static Response<Playlist> startPlaylistRequest(String request) {
        Response.Status status;
        Playlist result = null;
        Response<InputStream> response = startRequest(request);
        if (response.getStatus() == Response.Status.OK) {
            try {
                result = Playlist.parse(response.getResult());
                status = Response.Status.OK;
            } catch (ParseException e) {
                status = Response.Status.ERROR_CONTENT;
            }
        } else {
            status = response.getStatus();
        }
        return new Response<Playlist>(status, result);
    }

    @DebugLog
    private static Response<InputStream> startRequest(String request) {
        Response.Status status;
        InputStream result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(request);

            if (url.getProtocol().equals(HTTP)) {
                urlConnection = (HttpURLConnection) url.openConnection();
            } else if (url.getProtocol().equals(HTTPS)) {
                urlConnection = (HttpsURLConnection) url.openConnection();
            } else {
                throw new MalformedURLException();
            }

            if (request.startsWith(TDConfig.URL_API_TWITCH_KRAKEN_BASE)) {
                urlConnection.addRequestProperty(HEADER_ACCEPT, TDConfig.MIME_TWITCH);
            }

            result = urlConnection.getInputStream();
            status = Response.Status.OK;
        } catch (MalformedURLException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_URL;
        } catch (IOException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_CONNECTION;
        }
        return new Response<InputStream>(status, result);
    }

    private static String readString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        in.close();
        return out.toString();
    }
}