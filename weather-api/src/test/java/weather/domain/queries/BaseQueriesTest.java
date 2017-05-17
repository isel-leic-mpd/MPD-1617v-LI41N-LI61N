package weather.domain.queries;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import queries.iterable.Queries;
import weather.domain.HourlyInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lfalcao on 22/03/2017.
 */
public abstract class BaseQueriesTest {
    final static Collection<HourlyInfo> hourlyInfos = new ArrayList<>(5);
    protected Queries<HourlyInfo> QUERIES;


    protected abstract Queries<HourlyInfo> getQueries();


    @BeforeClass
    public static void setUpClass() throws Exception {
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 15), 0, "Sunny", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 16), 0, "Sunny", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 17), 0, "Sunny", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 15), 0, "Dia Glorioso", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 16), 0, "Dia Glorioso", 0, 0));
    }

    @Before
    public void setUp() throws Exception {
        QUERIES = getQueries();

    }

    @Test
    public void filterSunnyDays() throws Exception {
        // Arrange

        // Act

        final Queries<String> filteredSunnyDays =
                QUERIES
                    .filter(sunnyDaysPredicate())
                    //.distinct()
                    .map((hi) -> hi.getDate().toString())
                    //.skip(1)
                    .limit(1)
        ;


//        System.out.println("All");
//        query.forEach((hi) -> System.out.println(hi));
        System.out.println("Filtered");
        filteredSunnyDays.forEach((hi) -> System.out.println(hi));

        // Assert
        //assertEquals(1, mapped.size());
    }

    private Queries.Predicate<HourlyInfo> sunnyDaysPredicate() {
        return hourlyInfo -> "Sunny".equals(hourlyInfo.getDescription());
    }

    @Test
    public void filterWarmDays() throws Exception {
//        // Arrange
//
//        // Act
//        final Collection<HourlyInfo> filteredSunnyDays = EAGER_QUERIES.filter(hourlyInfos, new Predicate<HourlyInfo>() {
//            @Override
//            public boolean test(HourlyInfo hourlyInfo) {
//                return hourlyInfo.getTempC() > 20;
//            }
//        });
//
//        // Assert
//        assertEquals(1, filteredSunnyDays.size());


    }
}
