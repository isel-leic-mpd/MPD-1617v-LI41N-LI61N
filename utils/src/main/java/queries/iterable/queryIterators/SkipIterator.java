package queries.iterable.queryIterators;


import java.util.Iterator;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class SkipIterator<T> extends BaseIterator<T> implements Iterator<T> {
    private final Iterator<T> previous;

    public SkipIterator(Iterator<T> previous, int n) {
        this.previous = previous;

        while (n-- > 0 && previous.hasNext())
            previous.next();

        curr = moveNext();

    }

    protected T moveNext() {
        return previous.hasNext() ? previous.next() : null;
    }

}
