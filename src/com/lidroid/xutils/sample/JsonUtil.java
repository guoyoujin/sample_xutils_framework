package com.lidroid.xutils.sample;

import java.lang.reflect.Type;
import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T>T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

}

