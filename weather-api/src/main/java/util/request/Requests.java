package util.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lfalcao on 29/03/2017.
 */
public class Requests {
    private Requests() {
    }


    public static InputStream http(String path)  {
        System.out.println("Http request");
        try {
            return new URL(path).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream file(String path)  {
        System.out.println("file request");
        String[] parts = path.split("/");
        path = parts[parts.length-1]
                .replace('?', '-')
                .replace('&', '-')
                .replace('=', '-')
                .replace(',', '-')
                .substring(0,68);
        try {
            return ClassLoader.getSystemResource(path).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
