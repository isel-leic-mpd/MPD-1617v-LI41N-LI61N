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

package weather;

import com.sun.istack.internal.NotNull;
import util.request.Request;
import weather.model.HourlyInfo;
import weather.model.Location;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Luís Falcão
 *         created on 07-03-2017
 */
public class WeatherApi {

    private static final String WEATHER_TOKEN;
    private static final String WEATHER_HOST = "http://api.worldweatheronline.com";
    private static final String WEATHER_PAST = "/free/v2/past-weather.ashx";
    private static final String WEATHER_PAST_ARGS =
            "?q=%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";

    static {
        try {
            URL keyFile = ClassLoader.getSystemResource("worldweatheronline-app-key.txt");
            if(keyFile == null) {
               throw new IllegalStateException(
                       "YOU MUST GOT a KEY in developer.worldweatheronline.com and place it in src/main/resources/worldweatheronline-app-key.txt");
            } else {
                InputStream keyStream = keyFile.openStream();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyStream))) {
                    WEATHER_TOKEN = reader.readLine();
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private final Request req;

    public WeatherApi(@NotNull Request req) {
        Objects.requireNonNull(req, "You must provide a non null Request");

        this.req = req;
    }


    /**
     * E.g. http://api.worldweatheronline.com/free/v2/search.ashx?query=oporto&format=tab&key=*****
     */

    public Iterable<Location> search(String query) {
        return null;
    }

    /**
     * E.g. http://api.worldweatheronline.com/free/v2/search.ashx?query=oporto&format=tab&key=*****
     */
    public Iterable<HourlyInfo> pastWeather(
            double lat,
            double log,
            LocalDate from,
            LocalDate to
    ) {

        String path = createUri(lat, log, from, to); // Create the Uri

        Iterator<String> iter = req.getContent(path).iterator(); // Get the content

        List<HourlyInfo> res = createHourlyInfoInstances(iter);
        return res;
    }

    private List<HourlyInfo> createHourlyInfoInstances(Iterator<String> iter) {
        while(iter.next().startsWith("#")) { }
        iter.next(); // Skip line: Not Available
        List<HourlyInfo> res = new ArrayList<>();
        while(iter.hasNext()) {
            String line = iter.next(); // Skip Daily Info
            res.add(HourlyInfo.valueOf(line));
            if(iter.hasNext()) iter.next();
        }
        return res;
    }

    private String createUri(double lat, double log, LocalDate from, LocalDate to) {
        String query = lat + "," + log;
        return WEATHER_HOST + WEATHER_PAST +
                String.format(WEATHER_PAST_ARGS, query, from, to, WEATHER_TOKEN);
    }
}
