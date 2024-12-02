// src/main/java/com/weatherapp/WeatherApp.java
package com.weatherapp;

import com.weatherapp.service.WeatherService;

public class WeatherApp {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar WeatherApp.jar <latitude> <longitude> <limit>");
            return;
        }

        try {
            double lat = Double.parseDouble(args[0]);
            double lon = Double.parseDouble(args[1]);
            int limit = Integer.parseInt(args[2]);

            WeatherService weatherService = new WeatherService();
            String weatherData = weatherService.getWeatherData(lat, lon, limit);
            
            System.out.println("Full Weather Data: ");
            System.out.println(weatherData);

            double avgTemp = weatherService.calculateAverageTemperature(weatherData);
            System.out.println("Average Temperature: " + avgTemp + "°C");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

