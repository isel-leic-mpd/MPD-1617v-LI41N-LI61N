package util.queries;

import com.sun.istack.internal.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 31/03/2017.
 */
public class Functions {
    public static <T> Supplier<T> lazyly(Supplier<T> sup) {
        return null;
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
