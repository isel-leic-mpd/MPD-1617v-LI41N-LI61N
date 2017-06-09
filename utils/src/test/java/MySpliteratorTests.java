import org.junit.Test;
import streams.MySpliterator;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Created by lfalcao on 17/05/2017.
 */
public class MySpliteratorTests {
    @Test
    public void mySpliteratorShouldWork() throws Exception {
        final List<String> strs = Arrays.asList("a", "b", "c", "d", "e");
        StreamSupport.stream(new MySpliterator<>(strs), false)
                .parallel()
                //.max(String.CASE_INSENSITIVE_ORDER)
                //.ifPresent(System.out::println);
                .forEach(MySpliteratorTests::print);
    }


    @Test
    public void collectionSpliterators() throws Exception {
        final List<String> strs = Arrays.asList("a", "b", "c", "d", "e");
        System.out.println(strs.getClass().getName());

        final Queue<String> q = new ConcurrentLinkedQueue<String>();

        System.out.println("ArrayList Spliterator");
        showSpliteratorInfo(strs.spliterator());

        System.out.println("IntStream finite Spliterator");
        showSpliteratorInfo(IntStream.range(2,5).spliterator());

        System.out.println("IntStream infinite Spliterator");
        showSpliteratorInfo(IntStream.generate(() -> 1).spliterator());

        System.out.println("Concurrent QueueSpliterator");
        showSpliteratorInfo(q.spliterator());




    }

    private <T> void showSpliteratorInfo(Spliterator<T> spliterator) {
        System.out.println(spliterator.estimateSize());
        System.out.println(String.format("%x", spliterator.characteristics()));
        System.out.println(spliterator.getExactSizeIfKnown());

        int count = 0;
        while (spliterator.trySplit() != null)
            ++count;
        System.out.println(count + " splits allowed");

    }


    private static void print(String s) {
        System.out.println("print called on thread " + Thread.currentThread().getId() + " - " + s );
    }
}
