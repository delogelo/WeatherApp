package com.weatherapp;

import com.google.gson.JsonObject;
import com.weatherapp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherApp {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApp.class);

    public static void main(String[] args) {
        String apiKey = System.getenv("YANDEX_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("Ошибка: Необходимо задать ключ API в переменных окружения.");
            return;
        }

        String lat = args.length > 0 ? args[0] : "55.7558";
        String lon = args.length > 1 ? args[1] : "37.6176";
        int limit = args.length > 2 ? Integer.parseInt(args[2]) : 1;

        WeatherService weatherService = new WeatherService(apiKey);
        try {
            JsonObject rawWeatherData = weatherService.getRawWeatherData(lat, lon, limit);
            logger.info("Полный ответ JSON:\n{}", rawWeatherData);

            double averageTemp = weatherService.calculateAverageTemperature(rawWeatherData, limit);
            logger.info("Средняя температура за {} дней: {}°C", limit, averageTemp);
        } catch (Exception e) {
            logger.error("Произошла ошибка при получении данных о погоде: {}", e.getMessage(), e);
        }
    }
}
