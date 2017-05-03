package queries;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 31/03/2017.
 */
public class Functions {
    static class MyLazySupplier<T> implements Supplier<T> {

        private Supplier<T> longRunningSupplier;
        T value;

        public MyLazySupplier(Supplier<T> longRunningSupplier) {
            this.longRunningSupplier = longRunningSupplier;
        }

        @Override
        public T get() {
            if(value == null) {
                value = longRunningSupplier.get();
            }
            return value;
        }
    }

    static class MyLazySupplierWithoutIf<T> implements Supplier<T> {
        T value;
        private Supplier<T> sup;

        private final Supplier<T> othersCalls = () -> value;

//        Function<Supplier<T>, Supplier<T>> firstCallCreator = (s) ->
//            () -> {
//                sup = othersCalls;
//                value = s.get();
//                return value;
//            };



        public MyLazySupplierWithoutIf(Supplier<T> longRunningSupplier) {

            //sup = firstCallCreator.apply(longRunningSupplier);
            final Function<Supplier<T>, Supplier<T>> function =
                    new Function<Supplier<T>, Supplier<T>>() {

                @Override
                public Supplier<T> apply(Supplier<T> s) {

                    return new Supplier<T>() {
                        Supplier<T> sup = null;
                        @Override
                        public T get() {
                            MyLazySupplierWithoutIf.this.sup = othersCalls;
                            value = s.get();
                            return value;
                        }
                    };
                }
            };

            sup = function.apply(longRunningSupplier);
        }



        @Override
        public T get() {
            return sup.get();
        }
    }
    public static <T> Supplier<T> lazilyWithNamedClass(Supplier<T> sup) {
        return new MyLazySupplier<>(sup);
    }

    public static <T> Supplier<T> lazilyWithLambdaAndContextCapture(Supplier<T> sup) {
        final Object[] value = new Object[1];
        return () -> {
            if(value[0] == null) {
                value[0] = sup.get();
            }
            return (T)value[0];
        };
    }

    public static <T> Supplier<T> lazilyWithOptional(Supplier<T> sup) {
        final Optional<T>[] value = new Optional[] { Optional.empty() };
        return () -> value[0].orElseGet(() -> {
                    value[0] = Optional.of(sup.get());
                    return value[0].get();
                }
        );
    }

    public static <T, R> Function<T,R> decorate(Function<T, R> funcToCall, Consumer<T> decorateWith) {
        Objects.requireNonNull(funcToCall, ()-> "Func to call cannot be null in decorate method from class " + Functions.class.getSimpleName());
        return (t) -> { decorateWith.accept(t); return funcToCall.apply(t); };

    }

    public static <T, R, U> Function<T,U> andThen(Function<T, R> funcToCall, Function<R, U> nextFunction ) {
        //return (t) -> { R ret = funcToCall.apply(t);  return nextFunction.apply(ret); };
        return (t) -> nextFunction.apply(funcToCall.apply(t));
    }

}
