package misc;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 29/03/2017.
 */
public class MethodReferencesTest {

    @Test
    // Method reference kind 1 (java 8 in action - page 83)
    public void shouldCallAStaticMethod() throws Exception {

        sampleMethod((str) -> MethodReferencesTest.len(str), "SLB");
        sampleMethod(MethodReferencesTest::len, "SLB");

    }

    private String benfica = "Benfica";


    @Test
    // Method reference kind 2 (java 8 in action - page 83)
    public void shouldCallAnInstanceMethodOfAnArbitraryObject() throws Exception {
        sampleMethod((str) -> str.length(), "SLB");
        sampleMethod(String::length, "SLB");

        anotherSampleMethod((str, i) -> str.substring(i), "SLB", 1);
        anotherSampleMethod(String::substring, "SLB", 1);

    }

    @Test
    // Method reference kind 3 (java 8 in action - page 83)
    public void shouldCallAnInstanceMethodOfAnExistingObject() throws Exception {
        anotherMethod(() -> benfica.length());
        anotherMethod(benfica::length);

    }




    void sampleMethod(Function<String, Integer> f, String str) {
        System.out.println(f.apply(str));
    }

    void anotherSampleMethod(BiFunction<String, Integer, String> f, String str, int i) {
        System.out.println(f.apply(str, i));
    }

    void anotherMethod(Supplier<Integer> f) {
        System.out.println(f.get());
    }



    private static int len(String str) {
        return str.length();
    }


    void m() {
        m1((Callable<String>)(() -> "SLB"));
    }

    private <T> void m1(Callable<T> c) {

    }

    private <T> void m1(Supplier<T> c) {

    }
}
