package weather.model.queries;

/**
 *
 */
public interface Predicate<T> {
    boolean test(T t);
}
