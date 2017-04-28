package weather.domain.queries;

import util.queries.LazyQueries;
import util.queries.Queries;
import util.queries.queryIterators.LoggerIterator;
import weather.domain.HourlyInfo;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class LazyQueriesTest extends BaseQueriesTest {
    @Override
    protected Queries<HourlyInfo> getQueries() {
        return LazyQueries.query(() -> new LoggerIterator(hourlyInfos.iterator()));
    }
}