// src/main/java/com/weatherapp/service/WeatherService.java

package com.weatherapp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public void getWeatherData(double lat, double lon, int limit) {
        try {
            String urlString = API_URL + "?lat=" + lat + "&lon=" + lon + "&limit=" + limit;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Yandex-API-Key", apiKey);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                parseAndCalculateAverageTemperature(response.toString(), limit);
            } else {
                System.out.println("Error: Received HTTP response code " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching weather data: " + e.getMessage());
        }
    }

    private void parseAndCalculateAverageTemperature(String jsonResponse, int limit) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray forecasts = jsonObject.getAsJsonArray("forecasts");

        if (forecasts == null || forecasts.size() < limit) {
            System.out.println("Error: Insufficient forecast data available.");
            return;
        }

        double sumTemp = 0;
        for (JsonElement element : forecasts) {
            JsonObject forecast = element.getAsJsonObject();
            JsonObject parts = forecast.getAsJsonObject("parts");
            JsonObject dayPart = parts.getAsJsonObject("day");
            sumTemp += dayPart.get("temp_avg").getAsDouble();
        }

        double averageTemp = sumTemp / limit;
        System.out.printf("Средняя температура за %d дней: %.2f°C%n", limit, averageTemp);
    }
}
