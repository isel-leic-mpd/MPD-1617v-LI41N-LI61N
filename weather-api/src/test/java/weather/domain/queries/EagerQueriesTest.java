package weather.domain.queries;

import queries.iterable.queryIterators.LoggerIterator;
import queries.iterable.Queries;
import weather.domain.HourlyInfo;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class EagerQueriesTest extends BaseQueriesTest {
    @Override
    protected Queries<HourlyInfo> getQueries() {
        return Queries.EagerQueries.query(() -> new LoggerIterator(hourlyInfos.iterator()));
    }
}