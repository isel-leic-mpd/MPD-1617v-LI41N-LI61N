package weather.model.queries;

import org.junit.BeforeClass;
import org.junit.Test;
import util.queries.EagerQueries;
import weather.model.HourlyInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class EagerQueriesTest {
    final static Collection<HourlyInfo> hourlyInfos = new ArrayList<>(5);;
    final static EagerQueries<HourlyInfo> EAGER_QUERIES = EagerQueries.query(hourlyInfos);


    @BeforeClass
    public static void setUpClass() throws Exception {
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 15), 0, "Sunny", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 15), 0, "Dia Glorioso", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 16), 0, "Dia Glorioso", 0, 0));
        hourlyInfos.add(new HourlyInfo(LocalDate.of(2017, 03, 15), 0, "Sunny", 0, 0));
    }


    @Test
    public void filterSunnyDays() throws Exception {
        // Arrange

        // Act
        final EagerQueries<HourlyInfo> query = EagerQueries.query(hourlyInfos);

        final EagerQueries<String> filteredSunnyDays =
                query
                    .filter(sunnyDaysPredicate())
                    .distinct()
                    .map((hi) -> hi.getDate().toString())
                    .skip(1)
                    .limit(3);


//        System.out.println("All");
//        query.forEach((hi) -> System.out.println(hi));
        System.out.println("Filtered");
        filteredSunnyDays.forEach((hi) -> System.out.println(hi));

        // Assert
        //assertEquals(1, mapped.size());
    }

    private Predicate<HourlyInfo> sunnyDaysPredicate() {
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