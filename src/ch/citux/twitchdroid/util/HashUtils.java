package ch.citux.twitchdroid.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final String ALGORITHM = "HmacSHA1";

    public static String decodeJSON(String json) {
        return json.replace("\\", "");
    }

    public static String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8").replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String encodeHmacSHA1(String key, String message) {
        try {
            SecretKey secretKey = null;

            byte[] keyBytes = key.getBytes();
            secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKey);
            byte[] text = message.getBytes();
            return byteArrayToHexString(mac.doFinal(text));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }

    private static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}