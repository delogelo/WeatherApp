// src/main/java/com/weatherapp/WeatherApp.java

package com.weatherapp;

import com.weatherapp.service.WeatherService;

public class WeatherApp {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java WeatherApp <lat> <lon> <limit>");
            return;
        }

        try {
            double lat = Double.parseDouble(args[0]);
            double lon = Double.parseDouble(args[1]);
            int limit = Integer.parseInt(args[2]);

            String apiKey = System.getenv("YANDEX_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                System.out.println("Error: YANDEX_API_KEY environment variable is not set.");
                return;
            }

            WeatherService weatherService = new WeatherService(apiKey);
            weatherService.getWeatherData(lat, lon, limit);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid arguments. Please provide numeric values for latitude, longitude, and limit.");
        }
    }
}
