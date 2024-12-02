package com.weatherapp.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private final String requestUrl;
    private final String apiKey;

    public HttpClient(String requestUrl, String apiKey) {
        this.requestUrl = requestUrl;
        this.apiKey = apiKey;
    }

    public JsonObject executeRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Yandex-API-Key", apiKey);

        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                    return JsonParser.parseReader(reader).getAsJsonObject();
                }
            } else {
                throw new IOException("Ошибка при запросе данных: HTTP " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }
}
