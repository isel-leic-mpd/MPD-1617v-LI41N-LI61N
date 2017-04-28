package weather.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by lfalcao on 08/03/2017.
 */
public class DailyInfo {
    private final LocalDate date;
    private final int minTempC;
    private final int maxTemC;
    private final LocalTime sunset;
    private final LocalTime sunrise;

    public DailyInfo(LocalDate date, int minTempC, int maxTemC, LocalTime sunset, LocalTime sunrise) {
        this.date = date;
        this.minTempC = minTempC;
        this.maxTemC = maxTemC;
        this.sunset = sunset;
        this.sunrise = sunrise;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMinTempC() {
        return minTempC;
    }

    public int getMaxTemC() {
        return maxTemC;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }


    public Iterable<HourlyInfo> getHourlyInfo() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
