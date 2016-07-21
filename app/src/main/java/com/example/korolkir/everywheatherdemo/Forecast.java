package com.example.korolkir.everywheatherdemo;

import java.util.List;

/**
 * Created by korolkir on 21.07.16.
 */
public class Forecast {
    private String cityName;
    private List<Weather> weatherList;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
