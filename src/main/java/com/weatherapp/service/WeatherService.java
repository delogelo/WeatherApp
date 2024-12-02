// src/main/java/com/weatherapp/service/WeatherService.java
package com.weatherapp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {

    private static final String BASE_URL = "https://api.weather.yandex.ru/v2/forecast";
    private static final String API_KEY = System.getenv("YANDEX_API_KEY");

    public String getWeatherData(double lat, double lon, int limit) throws Exception {
        String urlString = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&limit=" + limit;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Yandex-API-Key", API_KEY);

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed to fetch weather data. HTTP code: " + connection.getResponseCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    public double calculateAverageTemperature(String weatherData) {
        JsonObject jsonObject = JsonParser.parseString(weatherData).getAsJsonObject();
        JsonArray forecasts = jsonObject.getAsJsonArray("forecasts");

        if (forecasts == null || forecasts.size() == 0) {
            throw new RuntimeException("No forecasts available for the given period.");
        }

        List<Double> temperatures = new ArrayList<>();
        for (int i = 0; i < forecasts.size(); i++) {
            JsonObject day = forecasts.get(i).getAsJsonObject();
            double temp = day.getAsJsonObject("parts").getAsJsonObject("day").get("temp_avg").getAsDouble();
            temperatures.add(temp);
        }

        return temperatures.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
