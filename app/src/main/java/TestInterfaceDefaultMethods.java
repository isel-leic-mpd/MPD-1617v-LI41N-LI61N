
import defaultmethods.MyClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lfalcao on 26/04/2017.
 */
public class TestInterfaceDefaultMethods {
    public static void main(String[] args) {
        MyClass mc = new MyClass();
        mc.m2(); // m2 m1
        mc.m3(); // m3 m1
        mc.m1(); // m1


        List<String> ls = Stream.of("a, abc, def, defg")
                .collect(Collectors.toList());

    }
}
