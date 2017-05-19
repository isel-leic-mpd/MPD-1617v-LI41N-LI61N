import com.sun.tools.javac.util.List;
import org.junit.Test;
import streams.MySpliterator;

import java.util.Comparator;
import java.util.stream.StreamSupport;

/**
 * Created by lfalcao on 17/05/2017.
 */
public class MySpliteratorTests {
    @Test
    public void mySpliteratorShouldWork() throws Exception {
        final List<String> strs = List.of("a", "b", "c", "d", "e");
        StreamSupport.stream(new MySpliterator<>(strs), false)
                .parallel()
                .max(String.CASE_INSENSITIVE_ORDER)
                .ifPresent(System.out::println);
                //.forEach(System.out::println);




    }
}
