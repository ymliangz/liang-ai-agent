package com.yml.liangaiagent.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class WeatherTools {
    WeatherService weatherService = new WeatherService();

    @Bean("currentWeather")
    @Description("Get the weather in location")
    Function<WeatherService.WeatherRequest, WeatherService.WeatherResponse> currentWeather(){
        return weatherService;
    }
}
