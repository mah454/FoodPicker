/*
 * Copyright (c) 2020.  FanapSoft Software Inc
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ir.moke.foodpicker.utils;

import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public interface JsonUtils {

    Jsonb jsonb = JsonbBuilder.create();

    static String toJson(Object object) {
        return jsonb.toJson(object);
    }

    static <T> T fromJson(String json, Class<T> tClass) {
        return jsonb.fromJson(json, tClass);
    }

    static <T> T fromJson(JsonObject jsonObject, Class<T> tClass) {
        return jsonb.fromJson(jsonObject.toString(), tClass);
    }
}
