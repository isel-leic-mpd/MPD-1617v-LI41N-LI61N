package weather.model.queries;

import weather.model.HourlyInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class that contains methods to make queries in past weather data
 */
public class WeatherQueries {

    public Collection<String> filterHourlyInfo(Iterator<HourlyInfo> allData, Predicate<HourlyInfo> pred) {
        final Collection<String> fd = new ArrayList<>();

        HourlyInfo curr;
        while (allData.hasNext()) {
            // Get next element
            curr = allData.next();

            // zzzzzzzzzz......
            // zzzzzzzzzz......
            // See if the hourly info is Sunny, and if it does insert it in the filtered data container,
            // if it does not contains yet
            if(pred.test(curr) && !fd.contains(curr.getDate().toString())) {
                fd.add(curr.getDate().toString());
            }
        }
        return fd;
    }


}
