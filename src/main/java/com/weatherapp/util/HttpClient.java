package com.weatherapp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private static final String API_KEY = System.getenv("YANDEX_API_KEY");

    public String makeRequest(double lat, double lon, int daysOffset) {
        try {
            String urlString = String.format(
                "https://api.weather.yandex.ru/v2/forecast?lat=%f&lon=%f&offset=%d",
                lat, lon, daysOffset
            );

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Yandex-API-Key", API_KEY);

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder response = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            connection.disconnect();
            return response.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error during HTTP request: " + e.getMessage(), e);
        }
    }
}
