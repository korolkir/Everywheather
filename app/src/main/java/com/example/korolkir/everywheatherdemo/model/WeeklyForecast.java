package com.example.korolkir.everywheatherdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by korolkir on 26.07.16.
 */
public class WeeklyForecast {

    @SerializedName("city")
    @Expose
    public City city;

    @SerializedName("list")
    private List<DailyForecast> dailyForecastList;

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
    }



    public List<DailyForecast> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }
}
