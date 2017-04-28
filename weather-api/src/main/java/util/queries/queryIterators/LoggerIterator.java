package util.queries.queryIterators;

import java.util.Iterator;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class LoggerIterator<T> implements Iterator<T> {
    private Iterator<T> next;
    int nextCounter = 0;
    private int hasNextCounter = 0;

    public LoggerIterator(Iterator<T> next) {
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        System.out.println("hasNext called " + ++hasNextCounter + " times");
        return next.hasNext();
    }

    @Override
    public T next() {
        System.out.println("next called " + ++nextCounter + " times");
        return next.next();
    }
}
