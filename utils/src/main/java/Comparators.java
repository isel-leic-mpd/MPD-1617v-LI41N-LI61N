import java.util.Comparator;
import java.util.function.Function;

/**
 * Created by lfalcao on 26/04/2017.
 */
public class Comparators {
    public static <T, U extends Comparable<U>> Comparator<T> comparing(Function<T, U> comparingCriteria) {
        return (t1, t2) -> comparingCriteria.apply(t1).compareTo(comparingCriteria.apply(t2));
        //Comparator.comparing((Function<T, U>) comparingCriteria::apply);
    }

    public static <T, U extends Comparable<U>> ComparatorBy<T> comparingBy(Function<T, U> comparingCriteria) {
        return (t1, t2) -> comparingCriteria.apply(t1).compareTo(comparingCriteria.apply(t2));
        //Comparator.comparing((Function<T, U>) comparingCriteria::apply);
    }

    public interface ComparatorBy<T> {
        int compare(T t1, T t2);

        default <U extends Comparable<U>> ComparatorBy<T> reverse() {
            return (t1, t2) -> compare(t2,t1);
        }

        default <U extends Comparable<U>> ComparatorBy<T> thenComparingLambda(Function<T, U> nextComparingCriteria) {
            ComparatorBy<T> currentComparator = comparingBy(nextComparingCriteria);
            //ComparatorBy<T> prevComparator = this;
            return (t1, t2) -> {
                //final int nextCmpValue = prevComparator.compare(t1, t2);
                final int nextCmpValue = this.compare(t1, t2);
                return nextCmpValue != 0 ? nextCmpValue : currentComparator.compare(t1, t2);
            };
        }


        default <U extends Comparable<U>> ComparatorBy<T> thenComparingAnonymousType(Function<T, U> nextComparingCriteria) {
            ComparatorBy<T> currentComparator = comparingBy(nextComparingCriteria);
            ComparatorBy<T> prevComparator = this;
            return new ComparatorBy<T>() {

                @Override
                public int compare(T t1, T t2) {
                    final int nextCmpValue = prevComparator.compare(t1, t2);
                    return nextCmpValue != 0 ? nextCmpValue : currentComparator.compare(t1, t2);
                }
            };
        }

        default <U extends Comparable<U>> ComparatorBy<T> thenComparingNamedType(Function<T, U> nextComparingCriteria) {
            ComparatorBy<T> currentComparator = comparingBy(nextComparingCriteria);
            return new ComparatorByImpl(this, nextComparingCriteria);
            
        }

        class ComparatorByImpl<T> implements ComparatorBy<T> {
            private final ComparatorBy<T> prevComparator;
            private final ComparatorBy<T> currentComparator;


            public <U extends Comparable<U>> ComparatorByImpl(ComparatorBy<T> prevComparator, Function<T, U> nextComparingCriteria) {

                this.prevComparator = prevComparator;
                currentComparator = comparingBy(nextComparingCriteria);
            }

            @Override
            public int compare(T t1, T t2) {
                final int nextCmpValue = prevComparator.compare(t1, t2);
                return nextCmpValue != 0 ? nextCmpValue : currentComparator.compare(t1, t2);
            }
        }
    }
}
