package com.weatherapp;

import com.weatherapp.model.WeatherInfo;
import com.weatherapp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WeatherApp {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApp.class);

    public static void main(String[] args) {
        String apiKey = System.getenv("YANDEX_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("Ошибка: Необходимо задать ключ API в переменных окружения.");
            return;
        }

        String lat = args.length > 0 ? args[0] : "55.7558"; // Москва по умолчанию
        String lon = args.length > 1 ? args[1] : "37.6176";

        WeatherService weatherService = new WeatherService(apiKey);
        try {
            WeatherInfo weatherInfo = weatherService.getWeather(lat, lon);
            if (weatherInfo != null) {
                logger.info("Текущая температура: {}", weatherInfo.getTemperature());
                logger.info("Погодное состояние: {}", weatherInfo.getCondition());
            }
        } catch (IOException e) {
            logger.error("Произошла ошибка при получении данных о погоде: {}", e.getMessage(), e);
        }
    }
}
