package com.zxz.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 24447
 */
public class IOUtil {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int ERR = -1;

    public static byte[] getByteByUrl(String url) throws IOException {
        final URLConnection urlConnection = new URL(url).openConnection();
        return getByteByUrlConnection(urlConnection);
    }

    public static byte[] getByteByUri(URI uri) throws IOException {
        final URLConnection urlConnection = uri.toURL().openConnection();
        return getByteByUrlConnection(urlConnection);
    }

    public static byte[] getByteByUrl(URL url) throws IOException {
        final URLConnection urlConnection = url.openConnection();
        return getByteByUrlConnection(urlConnection);
    }

    public static byte[] getByteByUrlConnection(URLConnection urlConnection) throws IOException {
        try (final InputStream inputStreamByUrlConnection = getInputStreamByUrlConnection(urlConnection)) {
            return inputStreamToArrayByte(inputStreamByUrlConnection);
        }
    }

    public static InputStream getInputStreamByUrlConnection(URLConnection urlConnection) throws IOException {
        return urlConnection.getInputStream();
    }


    public static byte[] inputStreamToArrayByte(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            copy(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE]);
            return outputStream.toByteArray();
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream, byte[] buff) throws IOException {
        int tag;
        while (ERR != (tag = inputStream.read(buff))) {
            outputStream.write(buff, 0, tag);
        }
    }

}
