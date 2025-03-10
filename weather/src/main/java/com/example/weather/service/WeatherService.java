package com.example.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "d7e0043c292d573bc55a2d511d8161f2"; // Replace with your actual API key

    public JsonNode getWeatherData(String city) {
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readTree(response);
        } catch (Exception e) {
            return null;
        }
    }
}
