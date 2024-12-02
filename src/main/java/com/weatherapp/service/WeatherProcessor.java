package com.weatherapp.service;

import com.google.gson.JsonObject;
import com.weatherapp.model.WeatherInfo;

public class WeatherProcessor {

    public static WeatherInfo processWeatherData(JsonObject jsonResponse) {
        if (jsonResponse != null && jsonResponse.has("fact")) {
            JsonObject fact = jsonResponse.getAsJsonObject("fact");
            int temperature = fact.get("temp").getAsInt();
            String condition = fact.get("condition").getAsString();

            return new WeatherInfo(temperature, condition);
        }
        return null;
    }
}
