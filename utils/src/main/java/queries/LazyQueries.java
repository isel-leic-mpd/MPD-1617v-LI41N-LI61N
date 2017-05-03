package queries;


import queries.iterable.queryIterators.FilterIterator;
import queries.iterable.queryIterators.LimitIterator;
import queries.iterable.queryIterators.MapIterator;
import queries.iterable.queryIterators.SkipIterator;

import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Class that contains methods to make queries in past weather data
 */
public class LazyQueries<T> implements Queries<T>, Iterable<T> {

    private Iterable<T> data;

    private LazyQueries(Iterable<T> data) {
        this.data = data;
    }

    /**
     * Filter objects from {@code data} that pass the given {@predicate}
     * @param pred the {@link Predicate} to apply to each member
     * @return The collection with the filtered object (the ones that passed the predicate filter)
     */
    @Override
    public Queries<T> filter(Predicate<T> pred) {
        return new LazyQueries<>(() -> new FilterIterator<>(this.iterator(), pred));
    }

    @Override
    public Queries<T> distinct() {
        return distinct((o1, o2) -> o1.equals(o2));
    }

    @Override
    public Queries<T> distinct(BiPredicate<T, T> equal) {
        throw new UnsupportedOperationException();
    }


    @Override
    public <U> Queries<U> map(Function<T, U> mapper) {
        return new LazyQueries<>(
                () -> new MapIterator<T, U>(data.iterator(), mapper));
    }

    public static <T> Queries<T> query(Iterable<T> initialIter) {
        return new LazyQueries<>(initialIter);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public Queries<T> skip(int n) {
        return new LazyQueries<>(
                () -> new SkipIterator<>(data.iterator(), n));
    }

    @Override
    public Queries<T> limit(int n) {
        return new LazyQueries<>(
                () -> new LimitIterator<>(data.iterator(), n));
    }
}
