package util.queries.queryIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by lfalcao on 22/03/2017.
 */
public abstract class BaseIterator<T> implements Iterator<T> {
    protected T curr;


    @Override
    public boolean hasNext() {
        return curr != null;
    }

    @Override
    public T next() {
        T ret = curr;
        if(curr == null) {
            throw new NoSuchElementException();
        }
        curr = moveNext();
        return ret;
    }


    /**
     * Returns the next element or {@code null} if none exists.
     * @return the next element or {@code null} if none exists.
     */
    protected abstract T moveNext();
}
