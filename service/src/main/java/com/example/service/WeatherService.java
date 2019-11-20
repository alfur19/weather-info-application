package com.example.service;

import com.example.common.dto.WeatherForecastModel;
import com.example.common.dto.WeatherModel;
import reactor.core.publisher.Flux;

public interface WeatherService {

    WeatherModel forecast(Integer cityId);
    Flux<WeatherForecastModel> forecastStream(Integer cityId);
}
