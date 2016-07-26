package com.example.korolkir.everywheatherdemo.View;

import android.content.Context;

import com.example.korolkir.everywheatherdemo.Model.DailyForecast;
import com.example.korolkir.everywheatherdemo.Model.Weather;

import java.util.List;

/**
 * Created by korolkir on 25.07.16.
 */
public interface ShowingView {

    void showCurrentDayTemperature(int temperature);
    void showCurrentDayDescription(String description);
    void showCurrentDayWindSpeed(int speed);
    void showCurrentDayTemperatureRange(int minTemperature, int maxTemperature);
    void showCurrentDayImage(int imageId);
    void setCurrentDayColor(int color);
    void showWeatherList(List<DailyForecast> weatherList);
    Context getContext();

}
