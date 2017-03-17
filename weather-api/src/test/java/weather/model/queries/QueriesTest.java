package weather.model.queries;

import org.junit.BeforeClass;
import org.junit.Test;
import util.queries.Queries;
import weather.model.HourlyInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class QueriesTest {
    final static Collection<HourlyInfo> hourlyInfos = new ArrayList<>(5);;
    final static Queries<HourlyInfo> QUERIES = new Queries<>();


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
        final Collection<HourlyInfo> filteredSunnyDays = QUERIES.filter(hourlyInfos,
                hourlyInfo -> "Sunny".equals(hourlyInfo.getDescription())
        );

        final Collection<HourlyInfo> distinct = QUERIES.distinct(filteredSunnyDays);

        final Collection<String> mapped = QUERIES.map(distinct,
                (hi) -> hi.getDate().toString()
        );



        // Assert
        assertEquals(1, mapped.size());


    }


    @Test
    public void filterWarmDays() throws Exception {
        // Arrange

        // Act
        final Collection<HourlyInfo> filteredSunnyDays = QUERIES.filter(hourlyInfos, new Predicate<HourlyInfo>() {
            @Override
            public boolean test(HourlyInfo hourlyInfo) {
                return hourlyInfo.getTempC() > 20;
            }
        });

        // Assert
        assertEquals(1, filteredSunnyDays.size());


    }

}