package queries.stream;

import java.util.function.Consumer;

/**
 * Created by lfalcao on 03/05/2017.
 */
@FunctionalInterface
public interface Advancer<T> {
    boolean tryAdvance(Consumer<T> action);
}
