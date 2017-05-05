package queries.iterable.queryIterators;


import queries.iterable.Queries;

import java.util.Iterator;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class FilterIterator<T> extends BaseIterator<T> {
    private final Iterator<T> previous;
    private final Queries.Predicate<T> pred;

    public FilterIterator(Iterator<T> previous, Queries.Predicate<T> pred) {
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
