package com.example.common.dto;

import java.util.Date;

public class WeatherForecastModel implements DataTransferObject{

    private Date startPeriod;
    private Date endPeriod;
    private Double celsiusTemperature;
    private Double celsiusTemperatureMin;
    private Double celsiusTemperatureMax;
    private Integer humidityPercentage;

    public WeatherForecastModel() {
    }

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Double getCelsiusTemperature() {
        return celsiusTemperature;
    }

    public void setCelsiusTemperature(Double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
    }

    public Double getCelsiusTemperatureMin() {
        return celsiusTemperatureMin;
    }

    public void setCelsiusTemperatureMin(Double celsiusTemperatureMin) {
        this.celsiusTemperatureMin = celsiusTemperatureMin;
    }

    public Double getCelsiusTemperatureMax() {
        return celsiusTemperatureMax;
    }

    public void setCelsiusTemperatureMax(Double celsiusTemperatureMax) {
        this.celsiusTemperatureMax = celsiusTemperatureMax;
    }

    public Integer getHumidityPercentage() {
        return humidityPercentage;
    }

    public void setHumidityPercentage(Integer humidityPercentage) {
        this.humidityPercentage = humidityPercentage;
    }

    @Override
    public String toString() {
        return "WeatherConditionModel{" +
                "startPeriod=" + startPeriod +
                ", endPeriod=" + endPeriod +
                ", celsiusTemperature=" + celsiusTemperature +
                ", celsiusTemperatureMin=" + celsiusTemperatureMin +
                ", celsiusTemperatureMax=" + celsiusTemperatureMax +
                ", humidityPercentage=" + humidityPercentage +
                '}';
    }
}
