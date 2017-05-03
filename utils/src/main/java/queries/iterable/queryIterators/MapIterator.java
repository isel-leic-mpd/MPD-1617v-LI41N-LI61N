package queries.iterable.queryIterators;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Created by lfalcao on 22/03/2017.
 */
public class MapIterator<T, U> extends BaseIterator<U> {
    private final Iterator<T> previous;
    private final Function<T, U> mapper;

    public MapIterator(Iterator<T> previous, Function<T, U> mapper) {

        this.previous = previous;
        this.mapper = mapper;

        curr = moveNext();
    }

    @Override
    protected U moveNext() {
        return previous.hasNext() ? mapper.apply(previous.next()) : null;
    }
}
