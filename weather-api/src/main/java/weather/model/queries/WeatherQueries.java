package weather.model.queries;

import weather.model.HourlyInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class that contains methods to make queries in past weather data
 */
public class WeatherQueries {

    public Collection<String> filterSunnyDays(Iterator<HourlyInfo> allData) {
        final Collection<String> fd = new ArrayList<>();

        HourlyInfo next;
        while (allData.hasNext()) {
            // Get next element
            next = allData.next();

            // zzzzzzzzzz......
            // zzzzzzzzzz......
            // See if the hourly info is Sunny, and if it does insert it in the filtered data container,
            // if it does not contains yet
            if ("Sunny".equals(next.getDescription()) && !fd.contains(next.getDate().toString())) {
                fd.add(next.getDate().toString());
            }
        }
        return fd;
    }


    public Collection<String> filterWarmDays(Iterator<HourlyInfo> allData) {
        final Collection<String> fd = new ArrayList<>();

        HourlyInfo next;
        while (allData.hasNext()) {
            // Get next element
            next = allData.next();

            // zzzzzzzzzz......
            // zzzzzzzzzz......
            // See if the hourly info is Sunny, and if it does insert it in the filtered data container,
            // if it does not contains yet
            if ("Sunny".equals(next.getDescription()) && !fd.contains(next.getDate().toString())) {
                fd.add(next.getDate().toString());
            }
        }
        return fd;
    }
}
