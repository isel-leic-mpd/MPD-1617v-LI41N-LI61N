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

import util.FileRequest;
import util.Request;
import weather.WeatherWebApi;
import weather.model.HourlyInfo;

import java.time.LocalDate;

import static java.lang.System.out;


/**
 * Main entry point to be used as an application tester for the libraries produced in each class.
 */
public class App {
    public static void main(String[] args) {
        Request req = new FileRequest(); // new HttpRequest();

        WeatherWebApi api = new WeatherWebApi(req);

        Iterable<HourlyInfo> infos = api.pastWeather(41.15, -8.6167, LocalDate.of(2017,02,01),LocalDate.of(2017,02,28));
        infos.forEach(out::println);
        /* <=>
        for (HourlyInfo line: data) {
            out.println(line);
        }
        */

    }
}