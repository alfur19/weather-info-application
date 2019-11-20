package com.example.common.dto;

import java.util.List;

public class WeatherModel implements DataTransferObject{

    private Integer cityId;
    private String city;
    private String country;
    private List<WeatherForecastModel> forecasts;

    public WeatherModel() {
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<WeatherForecastModel> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<WeatherForecastModel> forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public String toString() {
        return "City='" + city + '\'' + ", Country='" + country;
    }
}
