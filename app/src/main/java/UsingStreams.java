import java.util.Random;

/**
 * Created by lfalcao on 28/04/2017.
 */
public class UsingStreams {
    public static void main(String[] args) {
        System.out.println("Números");
        new Random()
                .ints(1, 51)
                .limit(5)
                .forEach(System.out::println);

        System.out.println("Estrelas");
        new Random()
                .ints(1, 13)
                .limit(2)
                .forEach(System.out::println);


    }
}
