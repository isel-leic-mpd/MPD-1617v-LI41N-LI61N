package util;

/**
 * Created by lfalcao on 26/04/2017.
 */
public interface SomeInterface {
    default void m2() {
        System.out.println("m2");
        m1();
    }

    default void m3() {
        System.out.println("m3");
        m1();
    }

    void m1();
}
