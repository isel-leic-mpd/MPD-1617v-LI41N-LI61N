package weather.data.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by lfalcao on 08/03/2017.
 */
public class DailyInfoDto {
    private final LocalDate date;
    private final int minTempC;
    private final int maxTemC;
    private final LocalTime sunset;
    private final LocalTime sunrise;

    public DailyInfoDto(LocalDate date, int minTempC, int maxTemC, LocalTime sunset, LocalTime sunrise) {
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


    public static DailyInfoDto valueOf(String line) {
        return null;
    }
}
