package util.queries;

import weather.model.queries.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Class that contains methods to make queries in past weather data
 */
public class Queries<T> {

    /**
     * Filter objects from {@code data} that pass the given {@predicate}
     * @param data the data {@link Iterator} to filter
     * @param pred the {@link Predicate} to apply to each member
     * @return The collection with the filtered object (the ones that passed the predicate filter)
     */
    public Collection<T> filter(Iterable<T> data, Predicate<T> pred) {
        final Collection<T> ret = new ArrayList<>();

        for(T curr : data) {
            if(pred.test(curr)) {
                ret.add(curr);
            }
        }
        return ret;
    }

    public Collection<T> distinct(Iterable<T> data) {
        return distinct(data, (o1, o2) -> o1.equals(o2));
    }

    public Collection<T> distinct(Iterable<T> data, BiPredicate<T, T> equal) {
        final Collection<T> ret = new ArrayList<>();
        for(T curr : data) {
            if(!contains(ret, curr, equal)) {
                ret.add(curr);
            }
        }
        return ret;
    }


    public <U> Collection<U> map(Iterable<T> data, Function<T, U> mapper) {
        final Collection<U> ret = new ArrayList<>();
        for(T curr : data) {
            ret.add(mapper.apply(curr));
        }
        return ret;
    }

    private boolean contains(Collection<T> data, T t, BiPredicate<T, T> comp) {
        for(T curr : data) {
           if(comp.test(curr, t)) {
               return true;
           }
        }
        return false;
    }


}
