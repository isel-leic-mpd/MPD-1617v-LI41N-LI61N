import org.junit.Test;
import queries.stream.QueryableStream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 03/05/2017.
 */
public class StreamTests {

    private String loremIpsum = "Lorem ipsum dolor sit amet consectetuer adipiscing elit. Nascetur sit lacus laoreet class metus sodales. Eni iaculis velit pretium et pretium fringilla gravida massa. In neque mollis placerat viverra. Iaculis tortor vestibulum vitae habitasse enim nam taciti hymenaeos condimentum ridiculus fusce eni nunc quam.\n" +
            "\n" +
            "Vel euismod taciti ut vitae duis nibh posuere molestie. Class malesuada elit. Consequat facilisi ornare cum. Commodo. Praesent. Aliquet dapibus diam fusce maecenas ve pellentesque class aliquam cum vivamus interdum. Scelerisque ultricies condimentum vitae. Luctus felis. Ante. Viverra platea adipiscing ultrices. Posuere sodales pede ut eget sapien ve enim at duis dictumst scelerisque tellus. Ipsum nulla penatibus nam velit vivamus sed. Fermentum vehicula fusce est natoque lacus tellus curabitur semper hymenaeos vel leo. Eu felis consectetuer parturient felis malesuada venenatis. Magna nisl sed dictum tristique non dictum. Platea orci primis leo habitasse lorem vitae penatibus fermentum libero.\n" +
            "\n" +
            "Fames leo litora venenatis gravida per id porta dictumst consectetuer leo. Eu suscipit maecenas orci cras aptent class. Scelerisque pretium fusce nunc ut mattis leo augue varius dignissim est ridiculus dui montes eni. Nostra mi tempus sociosqu auctor cubilia tempor ornare scelerisque cras. Ligula ve aliquam egestas montes vestibulum scelerisque platea ad lacinia netus parturient ornare luctus iaculis. Proin torquent integer natoque fames amet blandit est luctus pede fames parturient imperdiet. Odio hac nunc tellus mi pretium sollicitudin torquent eleifend commodo.\n" +
            "\n" +
            "Leo magnis enim rhoncus eni! Cursus nullam? Maecenas risus et odio elit hymenaeos ac ve taciti hymenaeos vestibulum adipiscing erat. Neque nostra eu torquent. Duis auctor id senectus blandit sodales taciti ac ac sit erat ad sollicitudin lobortis.\n" +
            "\n" +
            "Ve tellus mus adipiscing aliquet volutpat hymenaeos duis lorem urna aenean venenatis diam pellentesque sed cubilia. Morbi elit lorem. Hymenaeos donec erat dolor. Nulla elit primis phasellus ante commodo nisi tristique risus felis. Ad arcu tempus nonummy sit habitasse ac tristique tortor dictum lectus aptent! Fames ad nunc mauris placerat conubia adipiscing eget senectus potenti hendrerit congue magna rutrum penatibus. Senectus vivamus tempor dis posuere. Convallis eni convallis. Aenean inceptos per lacus pretium proin felis feugiat eni potenti ipsum euismod ac egestas venenatis. Aliquam velit mi class tellus facilisi sodales potenti nullam vestibulum proin malesuada quis. Sem congue phasellus scelerisque malesuada massa per erat ante.";

    @Test
    public void testStreamQueries() throws Exception {


        Stream.of(1, 2, 3, 4, 5, 6, 2)
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

        while (qs.tryAdvance((x) -> System.out.println(x))) ;
    }


    @Test
    public void implementMaxWithReduce() throws Exception {


        System.out.println(getStream()
                .max(Comparator.comparing(String::length)));
        System.out.println("---------------------------------");
        System.out.println(getStream()
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2));

    }

    int count = 0;

    @Test
    public void implementConcatWithReduce() throws Exception {


        final String delimiter = "-#-";


        System.out.println(getStream()
                .collect(joining(delimiter)));

        System.out.println("---------------------------------");

//        System.out.println(Stream.of("Sport","Lisboa","e","Benfica","Rumo","ao","36")

        System.out.println(IntStream.range(1, 200000).boxed()
                .parallel()
                .reduce("", (s1, s2) -> s1 + s2, (s1, s2) -> {
                    System.out.printf("immutable combiner called with %s and %s in thread %d\n", s1, s2, Thread.currentThread().getId());
                    count++;
                    return s1 + s2 + delimiter;
                }));

        System.out.println("Count: " + count);
//        for (int i = 0; i < 1000; i++) {
        StringBuilder sb = new StringBuilder();
        System.out.println(getStream()
                .parallel()
                .reduce(sb,
                        (sb1, s2) -> sb1.append(s2).append(delimiter),
                        (sb1, sb2) -> {
                            System.out.printf("combiner called with %s and %s\n", sb1, sb2);
                            return sb1;
                        }
                ));
//        }


    }

    @Test
    public void implementToList() throws Exception {


        final String delimiter = "-#-";
        List<String> list =
                getStream()
                        .parallel()
                        .reduce(
                                (List<String>) new ArrayList<String>(),
                                (l, s) -> {
                                    List<String> newL = new ArrayList<>(l);
                                    newL.add(s);
                                    return newL;
                                },
                                (l1, l2) -> {
                                    List<String> newL = new ArrayList<String>(l1);
                                    newL.addAll(l2);
                                    return newL;
                                }
                        );

        System.out.println("Reduce generated List: " + list);


        List<String> list1 =
                getStream()
                        .parallel()
                        .collect(ArrayList::new,
                                List::add,
                                (l1, l2) -> l2.addAll(l1)
                        );
        System.out.println("Collect generated List: " + list1);

        List<String> list2 =
                getStream()
                        .collect(toList());
        System.out.println("toList collector generated List: " + list2);

    }

    @Test
    public void groupingByUsage() throws Exception {
        Map<Character, Map<Integer, Long>> map = getLoremIpsumStream().
                collect(groupingBy(
                        s -> s.toLowerCase().charAt(0),
                        groupingBy(
                                s -> s.length(),
                                counting()
                        )

                ));
        System.out.println(map);
    }




    private Stream<String> getStream() {
        return Stream.of("Sport", "Lisboa", "e", "Benfica", "Rumo", "ao", "36");

    }

    private Stream<String> getLoremIpsumStream() {
        return Arrays.stream(loremIpsum.split(" "));
    }

}
