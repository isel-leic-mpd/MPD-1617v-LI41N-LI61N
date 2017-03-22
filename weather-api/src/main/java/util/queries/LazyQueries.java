package util.queries;

import util.queries.queryIterators.FilterIterator;
import util.queries.queryIterators.LimitIterator;
import util.queries.queryIterators.MapIterator;
import util.queries.queryIterators.SkipIterator;
import weather.model.queries.Predicate;

import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Class that contains methods to make queries in past weather data
 */
public class LazyQueries<T> implements Iterable<T> {

    private Iterable<T> data;

    private LazyQueries(Iterable<T> data) {
        this.data = data;
    }

    /**
     * Filter objects from {@code data} that pass the given {@predicate}
     * @param pred the {@link Predicate} to apply to each member
     * @return The collection with the filtered object (the ones that passed the predicate filter)
     */
    public LazyQueries<T> filter(Predicate<T> pred) {
        return new LazyQueries<>(() -> new FilterIterator<>(this.iterator(), pred));
    }

    public LazyQueries<T> distinct() {
        return distinct((o1, o2) -> o1.equals(o2));
    }

    public LazyQueries<T> distinct(BiPredicate<T, T> equal) {
        throw new UnsupportedOperationException();
    }


    public <U> LazyQueries<U> map(Function<T, U> mapper) {
        return new LazyQueries<>(
                () -> new MapIterator<T, U>(data.iterator(), mapper));
    }

    public static <T> LazyQueries<T> query(Iterable<T> initialIter) {
        return new LazyQueries<>(initialIter);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    public LazyQueries<T> skip(int n) {
        return new LazyQueries<>(
                () -> new SkipIterator<>(data.iterator(), n));
    }

    public LazyQueries<T> limit(int n) {
        return new LazyQueries<>(
                () -> new LimitIterator<>(data.iterator(), n));
    }
}
