package com.weatherapp.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weatherapp.util.HttpClient;

public class WeatherService {

    private final HttpClient httpClient;

    public WeatherService() {
        this.httpClient = new HttpClient();
    }

    public String getCurrentWeather(double lat, double lon) {
        String response = httpClient.makeRequest(lat, lon, 0);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        int temperature = jsonObject.getAsJsonObject("fact").get("temp").getAsInt();
        String condition = jsonObject.getAsJsonObject("fact").get("condition").getAsString();

        return String.format("Текущая температура: %d°C\nПогодное состояние: %s", temperature, condition);
    }

    public double calculateAverageTemperature(double lat, double lon, int limit) {
        double totalTemp = 0.0;

        for (int i = 0; i < limit; i++) {
            String response = httpClient.makeRequest(lat, lon, i);
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            totalTemp += jsonObject.getAsJsonObject("fact").get("temp").getAsDouble();
        }

        return totalTemp / limit;
    }
}
