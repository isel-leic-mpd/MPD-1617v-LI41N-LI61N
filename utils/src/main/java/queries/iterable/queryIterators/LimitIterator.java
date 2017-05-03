package queries.iterable.queryIterators;

import java.util.Iterator;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class LimitIterator<T> extends BaseIterator<T>  {
    private final Iterator<T> previous;
    private int n;

    public LimitIterator(Iterator<T> previous, int n) {

        this.previous = previous;
        this.n = n;
        curr = moveNext();

    }

    protected T moveNext() {
        T next = null;
        if(n-- > 0 && previous.hasNext()) {
            next = previous.next();
        }
        return next;
    }
}
