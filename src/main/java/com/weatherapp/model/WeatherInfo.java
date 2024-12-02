package com.weatherapp.model;

public class WeatherInfo {
    private final int temperature;
    private final String condition;

    public WeatherInfo(int temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public String getCondition() {
        return condition;
    }
}
