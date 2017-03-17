package util.queries;

import weather.model.queries.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Class that contains methods to make queries in past weather data
 */
public class Queries<T> implements Iterable<T> {

    private Iterable<T> data;

    private Queries(Iterable<T> data) {
        this.data = data;
    }

    /**
     * Filter objects from {@code data} that pass the given {@predicate}
     * @param pred the {@link Predicate} to apply to each member
     * @return The collection with the filtered object (the ones that passed the predicate filter)
     */
    public Queries<T> filter(Predicate<T> pred) {
        final Collection<T> ret = new ArrayList<>();

        for(T curr : data) {
            if(pred.test(curr)) {
                ret.add(curr);
            }
        }

        return new Queries<T>(ret);
    }

    public Queries<T> distinct() {
        return distinct((o1, o2) -> o1.equals(o2));
    }

    public Queries<T> distinct(BiPredicate<T, T> equal) {
        final Collection<T> ret = new ArrayList<>();
        for(T curr : data) {
            if(!contains(ret, curr, equal)) {
                ret.add(curr);
            }
        }
        return new Queries(ret);
    }


    public <U> Queries<U> map(Function<T, U> mapper) {
        final Collection<U> ret = new ArrayList<>();
        for(T curr : data) {
            ret.add(mapper.apply(curr));
        }
        return new Queries<U>(ret);
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
        return new Queries<>(initialIter);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}
