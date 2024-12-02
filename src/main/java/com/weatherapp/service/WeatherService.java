package com.weatherapp.service;

import com.google.gson.JsonObject;
import com.weatherapp.model.WeatherInfo;
import com.weatherapp.util.HttpClient;

import java.io.IOException;

public class WeatherService {
    private static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeather(String lat, String lon) throws IOException {
        String requestUrl = String.format("%s?lat=%s&lon=%s&limit=1", API_URL, lat, lon);
        HttpClient httpClient = new HttpClient(requestUrl, apiKey);
        JsonObject responseJson = httpClient.executeRequest();
        return WeatherProcessor.processWeatherData(responseJson);
    }
}
