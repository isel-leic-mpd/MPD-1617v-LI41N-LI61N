package weather.domain.queries;

import queries.EagerQueries;
import queries.Queries;
import queries.iterable.queryIterators.LoggerIterator;
import weather.domain.HourlyInfo;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class EagerQueriesTest extends BaseQueriesTest {
    @Override
    protected Queries<HourlyInfo> getQueries() {
        return EagerQueries.query(() -> new LoggerIterator(hourlyInfos.iterator()));
    }
}