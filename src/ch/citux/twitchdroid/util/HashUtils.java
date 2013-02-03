package ch.citux.twitchdroid.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final String ALGORITHM = "HmacSHA1";

    public static String encodeHmacSHA1(String key, String message) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(message.getBytes());
            return Hex.encodeHexString(rawHmac);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }
}