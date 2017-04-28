package util.queries;

import weather.domain.queries.Predicate;

import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Created by lfalcao on 22/03/2017.
 */
public interface Queries<T> extends Iterable<T> {
    Queries<T> filter(Predicate<T> pred);

    Queries<T> distinct();

    Queries<T> distinct(BiPredicate<T, T> equal);

    <U> Queries<U> map(Function<T, U> mapper);

    Queries<T> skip(int n);

    Queries<T> limit(int n);
}
