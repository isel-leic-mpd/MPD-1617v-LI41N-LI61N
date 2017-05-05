import org.junit.Test;
import queries.stream.QueryableStream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by lfalcao on 03/05/2017.
 */
public class StreamTests {
    @Test
    public void testStreamQueries() throws Exception {


        Stream.of(1,2,3,4,5,6,2)
                .peek(i -> System.out.println("before filter :" + i))
                .filter(i -> i % 2 == 0)
                .peek(i -> System.out.println("before map :" + i))
                .map(i -> i * i)
                .peek(i -> System.out.println("before forEach :" + i))
                .distinct()
                .forEach(System.out::println);
        System.out.println("---------------------------------");

        final QueryableStream<Integer> qs = QueryableStream.of(Arrays.asList(1, 2, 3, 4, 5, 6, 2))
                .peek(i -> System.out.println("before filter :" + i))
                .filter(i -> i % 2 == 0)
                .peek(i -> System.out.println("before map :" + i))
                .map(i -> i * i)
                .peek(i -> System.out.println("before forEach :" + i));

        while (qs.tryAdvance((x) -> System.out.println(x)));


    }
}
