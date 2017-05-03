/*
 * Copyright (c) 2017, Luís Falcão
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package weather.domain;

import weather.data.WeatherApi;
import weather.data.dto.DailyInfoDto;
import weather.data.dto.LocationDto;

import java.time.LocalDate;

/**
 * @author Luís Falcão
 *         created on 07-03-2017
 */
public class WeatherService {


    private WeatherApi weatherApi;

    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }


    /**
     * E.g. http://api.worldweatheronline.com/free/v2/search.ashx?query=oporto&format=tab&key=*****
     */

    public Iterable<Location> search(String query) {
        return null;
    }

    public Iterable<HourlyInfo> pastWeather(
            double lat,
            double log,
            LocalDate from,
            LocalDate to
    ) {

        return null;
    }

    public Location getLocation(String locationName) {
        final LocationDto locationDto = weatherApi.getLocation(locationName);
        return convertLocationDtoToLocation(locationDto);
    }

    private Location convertLocationDtoToLocation(LocationDto locationDto) {
        return new Location(locationDto.getCountry(), locationDto.getCity(), locationDto.getLatitude(), locationDto.getLongitude(),
                () -> convertDailyInfoDtosToDailyInfos(weatherApi.getDailyInfo(locationDto.getCity())));
    }

    private Iterable<DailyInfo> convertDailyInfoDtosToDailyInfos(Iterable<DailyInfoDto> dailyInfo) {
        return null;
    }
}
