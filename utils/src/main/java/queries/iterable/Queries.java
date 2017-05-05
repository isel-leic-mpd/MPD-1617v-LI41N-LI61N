package queries.iterable;


import queries.iterable.queryIterators.FilterIterator;
import queries.iterable.queryIterators.LimitIterator;
import queries.iterable.queryIterators.MapIterator;
import queries.iterable.queryIterators.SkipIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    /**
     * Class that contains methods to make queries in past weather data
     */
    class EagerQueries<T> implements Queries<T>, Iterable<T> {
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

    /**
     * Created by lfalcao on 31/03/2017.
     */
    class Functions {
        static class MyLazySupplier<T> implements Supplier<T> {

            private Supplier<T> longRunningSupplier;
            T value;

            public MyLazySupplier(Supplier<T> longRunningSupplier) {
                this.longRunningSupplier = longRunningSupplier;
            }

            @Override
            public T get() {
                if(value == null) {
                    value = longRunningSupplier.get();
                }
                return value;
            }
        }

        static class MyLazySupplierWithoutIf<T> implements Supplier<T> {
            T value;
            private Supplier<T> sup;

            private final Supplier<T> othersCalls = () -> value;

    //        Function<Supplier<T>, Supplier<T>> firstCallCreator = (s) ->
    //            () -> {
    //                sup = othersCalls;
    //                value = s.get();
    //                return value;
    //            };



            public MyLazySupplierWithoutIf(Supplier<T> longRunningSupplier) {

                //sup = firstCallCreator.apply(longRunningSupplier);
                final Function<Supplier<T>, Supplier<T>> function =
                        new Function<Supplier<T>, Supplier<T>>() {

                    @Override
                    public Supplier<T> apply(Supplier<T> s) {

                        return new Supplier<T>() {
                            Supplier<T> sup = null;
                            @Override
                            public T get() {
                                MyLazySupplierWithoutIf.this.sup = othersCalls;
                                value = s.get();
                                return value;
                            }
                        };
                    }
                };

                sup = function.apply(longRunningSupplier);
            }



            @Override
            public T get() {
                return sup.get();
            }
        }
        public static <T> Supplier<T> lazilyWithNamedClass(Supplier<T> sup) {
            return new MyLazySupplier<>(sup);
        }

        public static <T> Supplier<T> lazilyWithLambdaAndContextCapture(Supplier<T> sup) {
            final Object[] value = new Object[1];
            return () -> {
                if(value[0] == null) {
                    value[0] = sup.get();
                }
                return (T)value[0];
            };
        }

        public static <T> Supplier<T> lazilyWithOptional(Supplier<T> sup) {
            final Optional<T>[] value = new Optional[] { Optional.empty() };
            return () -> value[0].orElseGet(() -> {
                        value[0] = Optional.of(sup.get());
                        return value[0].get();
                    }
            );
        }

        public static <T, R> Function<T,R> decorate(Function<T, R> funcToCall, Consumer<T> decorateWith) {
            Objects.requireNonNull(funcToCall, ()-> "Func to call cannot be null in decorate method from class " + Functions.class.getSimpleName());
            return (t) -> { decorateWith.accept(t); return funcToCall.apply(t); };

        }

        public static <T, R, U> Function<T,U> andThen(Function<T, R> funcToCall, Function<R, U> nextFunction ) {
            //return (t) -> { R ret = funcToCall.apply(t);  return nextFunction.apply(ret); };
            return (t) -> nextFunction.apply(funcToCall.apply(t));
        }

    }

    /**
     * Class that contains methods to make queries in past weather data
     */
    class LazyQueries<T> implements Queries<T>, Iterable<T> {

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

    /**
     *
     */

    @FunctionalInterface
    interface Predicate<T> {
        boolean test(T t);
    }

    /**
     * Creates an {@link Iterator<String>} from an {@link InputStream} creating a {@link BufferedReader}
     * to read the stream chars. Only use instances of this class, when you are sure the
     * {@link InputStream} represents contains strings and not binary content.
     */
    class StringIteratorFromInputStream implements Iterator<String> {
        private final BufferedReader reader;
        String nextLine;

        public StringIteratorFromInputStream(InputStream is) throws IOException {
            reader = new BufferedReader(new InputStreamReader(is));
            moveNext();

        }

        private void moveNext() {
            try {
                nextLine = reader.readLine();

                if (nextLine == null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public String next() {
            String ret = nextLine;
            moveNext();
            return ret;
        }
    }
}
