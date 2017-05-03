package queries.iterable.queryIterators;


import queries.Predicate;

import java.util.Iterator;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class FilterIterator<T> extends BaseIterator<T> {
    private final Iterator<T> previous;
    private final Predicate<T> pred;

    public FilterIterator(Iterator<T> previous, Predicate<T> pred) {
        this.previous = previous;
        this.pred = pred;

        curr = moveNext();
    }

    @Override
    protected T moveNext() {
        while (previous.hasNext()) {
            T next = previous.next();
            if(pred.test(next))
                return next;
        }
        return null;
    }
}
