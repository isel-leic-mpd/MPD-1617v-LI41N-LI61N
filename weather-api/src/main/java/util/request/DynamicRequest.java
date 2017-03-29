package util.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * Created by lfalcao on 29/03/2017.
 */
public class DynamicRequest extends RequestImplBase {

    private Function<String, InputStream> streamer;

    public DynamicRequest(Function<String, InputStream> streamer) {
        this.streamer = streamer;
    }

    public void setStreamer(Function<String, InputStream> streamer) {
        this.streamer = streamer;
    }

    @Override
    protected InputStream getStream(String path) throws IOException {
        return streamer.apply(path);
    }
}
