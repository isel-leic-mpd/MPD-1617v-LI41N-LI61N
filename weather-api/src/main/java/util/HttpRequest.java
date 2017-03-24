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

package util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent a request to an Uri, making an HTTP RequestImplBase
 * @author Luís Falcão
 *         created on 08-03-2017
 */
public class HttpRequest extends RequestImplBase  {
    @Override
    protected InputStream getStream(String path) throws IOException {
        try {
            return new URL(path).openStream();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
