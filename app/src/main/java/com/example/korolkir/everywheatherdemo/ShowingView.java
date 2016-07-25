package com.example.korolkir.everywheatherdemo;

import android.content.Context;
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
    void showWeatherList(List<Weather> weatherList);
    Context getContext();

}
