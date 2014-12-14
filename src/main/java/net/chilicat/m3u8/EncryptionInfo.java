package net.chilicat.m3u8;

import java.net.URI;

/**
 * Contains information about media encryption.
 */
public interface EncryptionInfo {
    public URI getURI();

    public String getMethod();
}
