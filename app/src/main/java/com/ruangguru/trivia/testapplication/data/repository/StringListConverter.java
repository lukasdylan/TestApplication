package com.ruangguru.trivia.testapplication.data.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.TypeConverter;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@TypeConverter
public class StringListConverter extends com.raizlabs.android.dbflow.converter.TypeConverter<String, List>{

    private Gson gson = new Gson();

    @Override
    public String getDBValue(List model) {
        return gson.toJson(model);
    }

    @Override
    public List getModelValue(String data) {
        return gson.fromJson(data, new TypeToken<List<String>>() {{
        }}.getType());
    }
}