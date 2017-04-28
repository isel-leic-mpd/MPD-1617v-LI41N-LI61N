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

import java.time.LocalDate;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * @author Luís Falcão
 *         created on 07-03-2017
 */
public class Location {
    private final String country;
    private final String region;
    private final double latitude;
    private final double longitude;
    private Supplier<Iterable<DailyInfo>> dailyInfosSup;

    public Location(String country, String region, double latitude, double longitude, Supplier<Iterable<DailyInfo>> dailyInfosSup) {
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dailyInfosSup = dailyInfosSup;
    }


    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Iterable<DailyInfo> getDailyInfos() {
        return dailyInfosSup.get();
    }

    public Iterable<DailyInfo> getDailyInfos(LocalDate startDate, LocalDate endDate) {
        return null;
    }

}
