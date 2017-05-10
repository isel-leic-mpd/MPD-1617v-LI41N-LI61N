package queries.stream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 03/05/2017.
 */
public interface QueryableStream<T> extends Advancer<T> {
    static <T>QueryableStream<T> of(Iterable<T> base) {
        final Iterator<T> iterator = base.iterator();
        //return new IterableQueryableStream<T>(base);

//        return new QueryableStream<T>() {
//
//            @Override
//            public boolean tryAdvance(Consumer<T> action) {
//                if(iterator.hasNext()) {
//                    action.accept(iterator.next());
//                    return true;
//                }
//                return false;
//            }
//        };

        return (action) -> {
            if(iterator.hasNext()) {
                action.accept(iterator.next());
                return true;
            }
            return false;
        };

    }


    default <U> QueryableStream<U> map(Function<T, U> mapper) {
//        QueryableStream<T> prev = this;
//        return new QueryableStream<U>() {
//            @Override
//            public boolean tryAdvance(Consumer<U> action) {
//                return prev.tryAdvance((t) -> action.accept(mapper.apply(t)));
//            }
//        };

        return action -> this.tryAdvance((t) -> action.accept(mapper.apply(t)));

    }

    default QueryableStream<T> skip(int n) {
//        QueryableStream<T> prev = this;

//        return new QueryableStream<T>() {
//            private boolean skipped = false;
//            @Override
//            public boolean tryAdvance(Consumer<T> action) {
//                if(!skipped) {
//                    int count = n;
//                    while (count-- > 0 && prev.tryAdvance((t) -> { }));
//                    skipped = true;
//                }
//                return prev.tryAdvance(action);
//            }
//        };

//        final boolean []skipped = { false };
//        return action -> {
//            if (!skipped[0]) {
//                int count = n;
//                while (count-- > 0 && tryAdvance((t) -> { })) ;
//                skipped[0] = true;
//            }
//
//            return tryAdvance(action);
//        };
//
         // Now, something completelly different. Using filter to implement this operator
        int []count = {0};
        return filter(t -> ++count[0] > n);


    }

    default QueryableStream<T> take(int n) {
//        final int []skipped = { 0 };
//        return (action) -> skipped[0]++ < n && tryAdvance(action);

        // Now, something completelly different. Using filter to implement this operator
        int []count = {0};
        return filter(t -> count[0]++ < n);

        // This solutions is beautiful but does not work for infinite streams, that fortunately
        // this implementation does not support!!!
    }

    default QueryableStream<T> peek(Consumer<T> action) {
        return (downstreamAction) -> tryAdvance(t -> { action.accept(t); downstreamAction.accept(t); });
    }

    default QueryableStream<T> distinct() {
        Set<T> past = new HashSet<>();
        //return (action) -> filterWithPredicate(past::add, action);

        return filter(past::add);
    }

    default QueryableStream<T> filter(Predicate<T> pred) {
        return (action) -> filterWithPredicate(pred, action);
    }


    default boolean filterWithPredicate(Predicate<T> pred, Consumer<T> action) {
        RefBoolean found = new RefBoolean(false);
        //boolean [] found = {false};
        while(tryAdvance(t -> { if(found.setVal(pred.test(t))) action.accept(t);  })
                && !found.getVal());
        return found.getVal();
    }


    class IterableQueryableStream<T> implements QueryableStream<T> {

        private final Iterator<T> iterator;

        public IterableQueryableStream(Iterable<T> base) {
            iterator = base.iterator();
        }

        @Override
        public boolean tryAdvance(Consumer<T> action) {
            if(iterator.hasNext()) {
                action.accept(iterator.next());
                return true;
            }
            return false;
        }
    }

    class Ref<T> {
        private T val;

        public Ref(T val) {

            this.val = val;
        }

        public T getVal() {
            return val;
        }

        public T setVal(T val) {
            this.val = val;
            return val;
        }
    }

    class RefBoolean {
        private boolean val;

        public RefBoolean(boolean val) {

            this.val = val;
        }

        public boolean getVal() {
            return val;
        }

        public boolean setVal(boolean val) {
            this.val = val;
            return val;
        }
    }
}
