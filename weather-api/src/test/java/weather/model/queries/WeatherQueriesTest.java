package weather.model.queries;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weather.model.HourlyInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by lfalcao on 15/03/2017.
 */
public class WeatherQueriesTest {
    final static Collection<HourlyInfo> hourlyInfos = new ArrayList<>(5);;
    final static WeatherQueries weatherQueries = new WeatherQueries();


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
        final Collection<String> filteredSunnyDays = weatherQueries.filterHourlyInfo(hourlyInfos.iterator(), new Predicate<HourlyInfo>() {
            @Override
            public boolean test(HourlyInfo hourlyInfo) {
                return "Sunny".equals(hourlyInfo.getDescription());
            }
        });

        // Assert
        assertEquals(1, filteredSunnyDays.size());


    }


    @Test
    public void filterWarmDays() throws Exception {
        // Arrange

        // Act
        final Collection<String> filteredSunnyDays = weatherQueries.filterHourlyInfo(hourlyInfos.iterator(), new Predicate<HourlyInfo>() {
            @Override
            public boolean test(HourlyInfo hourlyInfo) {
                return hourlyInfo.getTempC() > 20;
            }
        });

        // Assert
        assertEquals(1, filteredSunnyDays.size());


    }

}