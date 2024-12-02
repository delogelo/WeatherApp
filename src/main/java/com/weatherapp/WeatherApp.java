package com.weatherapp;

import com.weatherapp.service.WeatherService;

public class WeatherApp {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WeatherApp <latitude> <longitude> [limit]");
            return;
        }

        double lat = Double.parseDouble(args[0]);
        double lon = Double.parseDouble(args[1]);
        int limit = args.length == 3 ? Integer.parseInt(args[2]) : 1;

        WeatherService weatherService = new WeatherService();

        if (limit > 1) {
            double avgTemp = weatherService.calculateAverageTemperature(lat, lon, limit);
            System.out.printf("Средняя температура за %d дней: %.2f°C%n", limit, avgTemp);
        } else {
            System.out.println(weatherService.getCurrentWeather(lat, lon));
        }
    }
}
