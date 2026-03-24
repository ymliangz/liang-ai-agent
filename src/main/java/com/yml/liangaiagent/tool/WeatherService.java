package com.yml.liangaiagent.tool;

import java.util.function.Function;

public class WeatherService implements Function<WeatherService.WeatherRequest, WeatherService.WeatherResponse> {
    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        return new WeatherResponse(30.0, Unit.C);
    }

    enum Unit {
        C, F
    }

    public record WeatherRequest(String location, Unit unit) {
    }

    public record WeatherResponse(double temp, Unit unit) {
    }
}
