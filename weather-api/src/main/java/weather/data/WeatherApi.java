package weather.data;

import weather.data.dto.DailyInfoDto;
import weather.data.dto.HourlyInfoDto;
import weather.data.dto.LocationDto;
import weather.domain.DailyInfo;

import java.time.LocalDate;

/**
 * Created by lfalcao on 05/04/2017.
 */
public interface WeatherApi {
    Iterable<DailyInfoDto> pastWeather(
            double lat,
            double log,
            LocalDate from,
            LocalDate to
    );


    Iterable<DailyInfoDto> getDailyInfo(String location);

    LocationDto getLocation(String locationName);
}
