package util.request;


import queries.StringIteratorFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * Created by lfalcao on 10/03/2017.
 */
public abstract class RequestImplBase implements Request {

    public final Iterable<String> getContent(String path) {

        return () -> {
            try {
                return new StringIteratorFromInputStream(getStream(path));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
    protected abstract InputStream getStream(String path) throws IOException;
}
