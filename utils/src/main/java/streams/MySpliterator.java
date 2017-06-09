package streams;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by lfalcao on 17/05/2017.
 */
public class MySpliterator<T> implements Spliterator<T> {

    private int end;
    private final int start;
    private List<T> col;

    int idx;


    public MySpliterator(List<T> col) {
        this(col ,0, col.size());

    }

    private MySpliterator(List<T> col, int start, int end) {
        this.col = col;
        idx = start;
        this.end = end;
        this.start = start;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        System.out.println("tryAdvance called in thread " + Thread.currentThread().getId());
        if(idx < end) {
            action.accept(col.get(idx++));
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        System.out.println("try split called in thread " + Thread.currentThread().getId());
        if(size() > col.size() /2) {
            int middle = start + size()/2;
            final MySpliterator<T> split = new MySpliterator<>(col, middle, end);
            end = middle;
            return split;
        }

        System.out.println("returning null in thread " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public long estimateSize() {
        return size();
    }

    private int size() {
        return end-start;
    }

    @Override
    public int characteristics() {
        System.out.println("characteristics called in thread " + Thread.currentThread().getId());
        return Spliterator.SORTED;
        //return SORTED;
    }

    @Override
    public Comparator<? super T> getComparator() {
        return null;
    }


}
