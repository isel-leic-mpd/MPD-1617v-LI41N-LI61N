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

import util.request.*;
import weather.WeatherApi;
import weather.model.HourlyInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.System.out;


/**
 * Main entry point to be used as an application tester for the libraries produced in each class.
 */
public class DynamicRequestApp {
    private static Map<String, Supplier<Request>> requestTypes = new HashMap<>();

    static {
        requestTypes.put("http", HttpRequest::new);
        requestTypes.put("file", FileRequest::new);

    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid usage: java DynamicRequestApp [file|http]");
            return;
        }

        String requestType = args[0];

        WeatherApi api = new WeatherApi(requestTypes.get(requestType).get());

        Iterable<HourlyInfo> infos = api.pastWeather(41.15, -8.6167, LocalDate.of(2017,02,01),LocalDate.of(2017,02,28));
        infos.forEach(out::println);

    }
}