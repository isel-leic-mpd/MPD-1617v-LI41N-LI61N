package misc;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * Created by lfalcao on 29/03/2017.
 */
public class ComposingFunctionsTest {

    @Test
    public void name() throws Exception {
        final List<String> strings = Arrays.asList("a", "ab", "abc", "defe", "kdsjfkds");

        strings.sort((s1, s2) -> s1.length()-s2.length());

        strings.sort(createIntComparator(String::length));
        strings.sort((s1, s2) -> s1.compareTo(s2)*-1);
        strings.sort((s1, s2) -> s1.charAt(s1.length()-1) - s2.charAt(s2.length()-1));
    }


    private static <T> Comparator<T> createIntComparator(ToIntFunction<T> intExtractor) {
        return (t1, t2) -> intExtractor.applyAsInt(t1) - intExtractor.applyAsInt(t2);

    }
}
