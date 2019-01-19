package com.match.maker.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.match.maker.featureModules.landing.models.Info;
import com.match.maker.featureModules.landing.models.Result;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by ravindra on 19,January,2019
 */
public class DataTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Result> stringToResultList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Result>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static Info stringToInfo(String data) {
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<Info>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String resultListToString(List<Result> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static String InfoToString(Info someObjects) {
        return gson.toJson(someObjects);
    }
}

