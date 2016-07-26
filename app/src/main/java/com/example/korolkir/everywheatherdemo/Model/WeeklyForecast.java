package com.example.korolkir.everywheatherdemo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by korolkir on 26.07.16.
 */
public class WeeklyForecast {
    @SerializedName("list")
    private List<DailyForecast> dailyForecastList;

    public List<DailyForecast> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }
}
