package util.queries;

import weather.domain.queries.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Class that contains methods to make queries in past weather data
 */
public class EagerQueries<T> implements  Queries<T>, Iterable<T> {
    private Iterable<T> data;
    private EagerQueries(Iterable<T> data) {
        this.data = data;
    }

    /**
     * Filter objects from {@code data} that pass the given {@predicate}
     * @param pred the {@link Predicate} to apply to each member
     * @return The collection with the filtered object (the ones that passed the predicate filter)
     */
    public EagerQueries<T> filter(Predicate<T> pred) {
        final Collection<T> ret = new ArrayList<>();

        for(T curr : data) {
            if(pred.test(curr)) {
                ret.add(curr);
            }
        }

        return new EagerQueries<T>(ret);
    }

    public EagerQueries<T> distinct() {
        return distinct((o1, o2) -> o1.equals(o2));
    }

    public EagerQueries<T> distinct(BiPredicate<T, T> equal) {
        final Collection<T> ret = new ArrayList<>();
        for(T curr : data) {
            if(!contains(ret, curr, equal)) {
                ret.add(curr);
            }
        }
        return new EagerQueries(ret);
    }


    public <U> EagerQueries<U> map(Function<T, U> mapper) {
        final Collection<U> ret = new ArrayList<>();
        for(T curr : data) {
            ret.add(mapper.apply(curr));
        }
        return new EagerQueries<U>(ret);
    }

    public EagerQueries<T> skip(int n) {
        final Collection<T> ret = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        while(n-- > 0 && iterator.hasNext())
            iterator.next();
        while (iterator.hasNext()) {
            ret.add(iterator.next());
        }
        return new EagerQueries<T>(ret);
    }

    public EagerQueries<T> limit(int n) {
        final Collection<T> ret = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        while(n-- > 0 && iterator.hasNext()) {
            ret.add(iterator.next());
        }
        return new EagerQueries<T>(ret);
    }



    private boolean contains(Collection<T> data, T t, BiPredicate<T, T> comp) {
        for(T curr : data) {
           if(comp.test(curr, t)) {
               return true;
           }
        }
        return false;
    }


    public static <T> Queries<T> query(Iterable<T> initialIter) {
        return new EagerQueries<>(initialIter);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}
