package weather.domain.queries;

/**
 *
 */

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
