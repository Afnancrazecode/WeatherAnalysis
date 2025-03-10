package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public String getWeather(@PathVariable String city, Model model) {
        JsonNode weatherData = weatherService.getWeatherData(city);

        if (weatherData == null) {
            return "error"; // Show error page if API call fails
        }

        // Add weather details to the model
        model.addAttribute("city", weatherData.get("name").asText());
        model.addAttribute("country", weatherData.get("sys").get("country").asText());
        model.addAttribute("lat", weatherData.get("coord").get("lat").asDouble());
        model.addAttribute("lon", weatherData.get("coord").get("lon").asDouble());
        model.addAttribute("temp", weatherData.get("main").get("temp").asDouble());
        model.addAttribute("feels_like", weatherData.get("main").get("feels_like").asDouble());
        model.addAttribute("humidity", weatherData.get("main").get("humidity").asInt());
        model.addAttribute("wind_speed", weatherData.get("wind").get("speed").asDouble());
        model.addAttribute("weather", weatherData.get("weather").get(0).get("description").asText());

        // Convert sunrise and sunset timestamps
        long sunriseTimestamp = weatherData.get("sys").get("sunrise").asLong() * 1000;
        long sunsetTimestamp = weatherData.get("sys").get("sunset").asLong() * 1000;

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        model.addAttribute("sunrise", sdf.format(new java.util.Date(sunriseTimestamp)));
        model.addAttribute("sunset", sdf.format(new java.util.Date(sunsetTimestamp)));

        return "weather"; // Returns the Thymeleaf template named "weather.html"
    }
}
