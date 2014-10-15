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

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import ch.citux.td.R;
import ch.citux.td.TDApplication;
import ch.citux.td.config.TDConfig;
import ch.citux.td.data.model.Response;
import ch.citux.td.util.Log;
import hugo.weaving.DebugLog;

public class TDRequestHandler {

    private static final String TAG = "TDRequestHandler";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String TLS = "TLS";
    private static final String BKS = "BKS";
    private static final String HEADER_ACCEPT = "Accept";

    private static SSLSocketFactory sslSocketFactory;

    private static void init() throws CertificateException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException, IllegalStateException {
        if (sslSocketFactory == null) {
            KeyStore keyStore = KeyStore.getInstance(BKS);
            keyStore.load(TDApplication.getContext().getResources().openRawResource(R.raw.certs), null);
            sslSocketFactory = new TDSocketFactory(keyStore);
        }
    }

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
                status = Response.Status.ERROR_TIMEOUT;
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
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(request);

            if (url.getProtocol().equals(HTTP)) {
                urlConnection = (HttpURLConnection) url.openConnection();
            } else if (url.getProtocol().equals(HTTPS)) {
                init();
                HttpsURLConnection sslConnection = (HttpsURLConnection) url.openConnection();
                if (sslConnection != null && sslSocketFactory != null)
                    sslConnection.setSSLSocketFactory(sslSocketFactory);
                urlConnection = sslConnection;
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
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_CONNECTION;
        } catch (KeyStoreException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_CONNECTION;
        } catch (KeyManagementException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_CONNECTION;
        } catch (CertificateException e) {
            Log.e(TAG, e);
            status = Response.Status.ERROR_CONNECTION;
        } catch (IllegalStateException e) {
            sslSocketFactory = null; //SSLContext is not initialized.
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

//    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
//        public boolean verify(String hostname, SSLSession session) {
//            return true;
//        }
//    };
//    /**
//     * Trust every server - dont check for any certificate
//     */
//    private static void trustAllHosts() {
//        // Create a trust manager that does not validate certificate chains
//        X509TrustManager[] trustAllCerts = new X509TrustManager[]{
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[]{};
//                    }
//
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//                }
//        };
//
//        // Install the all-trusting trust manager
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//            Log.e(TAG, e);
//        }
//    }

    public static class TrustManager implements X509TrustManager {

        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;
        private X509Certificate[] acceptedIssuers;

        public TrustManager(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException {
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);

            tmf.init((KeyStore) null);
            defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

            tmf.init(keyStore);
            localTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
            acceptedIssuers = ArrayUtils.addAll(defaultTrustManager.getAcceptedIssuers(), localTrustManager.getAcceptedIssuers());
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return acceptedIssuers;
        }
    }

    public static class TDSocketFactory extends SSLSocketFactory {

        private SSLContext sslContext;

        public TDSocketFactory(KeyStore keyStore) {
            try {
                sslContext = SSLContext.getInstance(TLS);
                sslContext.init(null, new TrustManager[]{new TrustManager(keyStore)}, null);
            } catch (NoSuchAlgorithmException e) {
                Log.e(this, e);
            } catch (KeyStoreException e) {
                Log.e(this, e);
            } catch (KeyManagementException e) {
                Log.e(this, e);
            }
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return sslContext.getSocketFactory().getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return sslContext.getSocketFactory().getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(host, port);
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return sslContext.getSocketFactory().createSocket(host, port);
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(host, port, localHost, localPort);
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return sslContext.getSocketFactory().createSocket(address, port, localAddress, localPort);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }
    }
}