package ch.citux.twitchdroid.data.worker;

import android.util.Log;
import ch.citux.twitchdroid.BuildConfig;
import ch.citux.twitchdroid.data.model.Response;
import net.chilicat.m3u8.ParseException;
import net.chilicat.m3u8.Playlist;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TwitchDroidRequestHandler {

    private static final String TAG = "TwitchDroidRequestHandler";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    public static Response<String> startStringRequest(String request) {
        Log.d(TAG, "Url :" + request);
        Response.Status status;
        String result = "";
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

            result = readString(urlConnection.getInputStream());
            Log.d(TAG, result);
            status = Response.Status.OK;
        } catch (MalformedURLException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
            status = Response.Status.ERROR_URL;
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
            status = Response.Status.ERROR_CONNECTION;
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        return new Response<String>(status, result);
    }

    public static Response<Playlist> startPlaylistRequest(String request) {
        Log.d(TAG, "Url :" + request);
        Response.Status status;
        Playlist result = null;
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

            result = Playlist.parse(urlConnection.getInputStream());
            status = Response.Status.OK;
        } catch (MalformedURLException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
            status = Response.Status.ERROR_URL;
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
            status = Response.Status.ERROR_CONNECTION;
        } catch (ParseException e) {
            status = Response.Status.ERROR_CONTENT;
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        return new Response<Playlist>(status, result);
    }

    private static String readString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        return out.toString();
    }
}