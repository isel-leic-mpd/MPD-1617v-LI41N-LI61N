package util;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lfalcao on 10/03/2017.
 */
public abstract class RequestImplBase implements Request {

    protected void processStream(String path, ArrayList<String> res) {
        try(InputStream in = ClassLoader.getSystemResource(path).openStream()) {
            /*
             * Consumir o Inputstream e adicionar dados ao res
             */
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    res.add(line);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
