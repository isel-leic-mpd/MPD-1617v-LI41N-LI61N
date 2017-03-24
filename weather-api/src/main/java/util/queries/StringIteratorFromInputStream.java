package util.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Creates an {@link Iterator<String>} from an {@link InputStream} creating a {@link BufferedReader}
 * to read the stream chars. Only use instances of this class, when you are sure the
 * {@link InputStream} represents contains strings and not binary content.
 */
public class StringIteratorFromInputStream implements Iterator<String> {
    private final BufferedReader reader;
    String nextLine;

    public StringIteratorFromInputStream(InputStream is) throws IOException {
        reader = new BufferedReader(new InputStreamReader(is));
        moveNext();

    }

    private void moveNext() {
        try {
            nextLine = reader.readLine();

            if (nextLine == null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String next() {
        String ret = nextLine;
        moveNext();
        return ret;
    }
}
