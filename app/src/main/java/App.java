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

import util.request.DynamicRequest;
import util.request.FileRequest;
import util.request.Request;
import util.request.Requests;
import weather.data.WeatherApi;
import weather.data.WeatherApiImpl;
import weather.data.dto.DailyInfoDto;
import weather.domain.DailyInfo;
import weather.domain.HourlyInfo;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.function.Function;

import static java.lang.System.out;
import static util.queries.Functions.andThen;
import static util.queries.Functions.decorate;


/**
 * Main entry point to be used as an application tester for the libraries produced in each class.
 */
public class App {
    public static void main(String[] args) {
        Request freq = new FileRequest(); // new HttpRequest();

        Function <String, InputStream> requestHandler = Requests::file;
        DynamicRequest req = new DynamicRequest(andThen(decorate(Requests::file, App::showMessage), u -> u));



        WeatherApi api = new WeatherApiImpl(req);

        Iterable<DailyInfoDto> infos = api.pastWeather(41.15, -8.6167, LocalDate.of(2017,02,01),LocalDate.of(2017,02,28));
        infos.forEach(out::println);


        req.setStreamer(Requests::http);

        infos = api.pastWeather(41.15, -8.6167, LocalDate.of(2017,02,01),LocalDate.of(2017,02,28));
        infos.forEach(out::println);
        /* <=>
        for (HourlyInfo line: data) {
            out.println(line);
        }
        */

    }

    private static void showMessage(String str) {
        System.out.println("Request for file " + str);
    }
}