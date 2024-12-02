package com.weatherapp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.weatherapp.util.HttpClient;

import java.io.IOException;

public class WeatherService {
    private static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public JsonObject getRawWeatherData(String lat, String lon, int limit) throws IOException {
        String requestUrl = String.format("%s?lat=%s&lon=%s&limit=%d", API_URL, lat, lon, limit);
        HttpClient httpClient = new HttpClient(requestUrl, apiKey);
        return httpClient.executeRequest();
    }

    public double calculateAverageTemperature(JsonObject weatherData, int limit) {
        JsonArray forecasts = weatherData.getAsJsonArray("forecasts");
        double totalTemp = 0;

        for (int i = 0; i < Math.min(limit, forecasts.size()); i++) {
            JsonObject dayPart = forecasts.get(i).getAsJsonObject()
                                         .getAsJsonObject("parts")
                                         .getAsJsonObject("day");
            totalTemp += dayPart.get("temp_avg").getAsDouble();
        }

        return totalTemp / limit;
    }
}
