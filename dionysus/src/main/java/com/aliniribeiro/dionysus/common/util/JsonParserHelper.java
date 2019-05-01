package com.aliniribeiro.dionysus.common.util;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class JsonParserHelper {

    public static String toString(JSONObject object, String name) {
        return object != null && object.get(name) != null ? object.get(name).toString() : null;
    }

    public static Double toDouble(JSONObject object, String name) {
        return object != null && object.get(name) != null ? new Double(object.get(name).toString()) : null;
    }

    public static LocalDate toLocalDate(JSONObject object, String name) {
        return object != null && object.get(name) != null ? LocalDate.parse(object.get(name).toString()) : null;
    }

    public static Integer toInteger(JSONObject object, String name) {
        return object != null && object.get(name) != null ? Integer.parseInt(object.get(name).toString()) : null;
    }
}
