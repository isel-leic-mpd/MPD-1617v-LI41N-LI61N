import org.junit.Test;
import queries.stream.QueryableStream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

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


    @Test
    public void implementMaxWithReduce() throws Exception {


        System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")
                .max(Comparator.comparing(String::length)));
        System.out.println("---------------------------------");
        System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2));

    }

    @Test
    public void implementConcatWith() throws Exception {


        final String delimiter = "-#-";
        System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")
                .collect(joining(delimiter)));
        System.out.println("---------------------------------");

        System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")
                .reduce("", (s1, s2) ->  s1 + s2 + delimiter ));

        for (int i = 0; i < 1000; i++) {
            StringBuilder sb = new StringBuilder();
            System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")
                    .parallel()
                    .reduce(sb, (sb1, s2) ->  sb1.append(s2).append(delimiter), (sb1, sb2) -> sb1));
        }



    }

}
