package weather.domain.queries;

import queries.Queries;
import queries.iterable.queryIterators.LoggerIterator;
import queries.iterable.Queries;
import weather.domain.HourlyInfo;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class LazyQueriesTest extends BaseQueriesTest {
    @Override
    protected Queries<HourlyInfo> getQueries() {
        return Queries.LazyQueries.query(() -> new LoggerIterator(hourlyInfos.iterator()));
    }
}