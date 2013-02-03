package ch.citux.twitchdroid.data.worker;

import android.util.Log;
import ch.citux.twitchdroid.BuildConfig;
import ch.citux.twitchdroid.data.model.Response;
import net.sourceforge.jplaylistparser.exception.JPlaylistParserException;
import net.sourceforge.jplaylistparser.parser.AutoDetectParser;
import net.sourceforge.jplaylistparser.playlist.Playlist;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TwitchDroidRequestHandler {

    private static final String TAG = "TwitchDroidRequestHandler";

    public static Response<String> startStringRequest(String request) {
        Log.d(TAG, "Url :" + request);
        Response.Status status;
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(request);
            urlConnection = (HttpURLConnection) url.openConnection();
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
        Playlist result = new Playlist();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(request);
            urlConnection = (HttpURLConnection) url.openConnection();
            result = readPlaylist(url, urlConnection.getContentType(), urlConnection.getInputStream());
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

    private static Playlist readPlaylist(URL url, String contentType, InputStream in) {
        AutoDetectParser parser = new AutoDetectParser();
        Playlist playlist = new Playlist();
        try {
            parser.parse(url.toString(), contentType, in, playlist);
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
        } catch (SAXException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
        } catch (JPlaylistParserException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            } else {
                Log.e(TAG, e.toString());
            }
        }
        return playlist;
    }
}