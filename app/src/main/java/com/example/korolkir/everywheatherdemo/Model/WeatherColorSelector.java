package com.example.korolkir.everywheatherdemo.model;

import android.content.Context;
import android.os.Build;

import com.example.korolkir.everywheatherdemo.R;

/**
 * Created by korolkir on 24.07.16.
 */
public class WeatherColorSelector {

    private Context context;

    public WeatherColorSelector(Context context) {
        this.context = context;
    }

    public int getColorAccordingTypeOfWeather(String typeOfWeather) {
        int color = 0;
        int id = 0;
        switch (typeOfWeather) {
            case "Rain":
                id = R.color.rain;
                break;
            case "Clear":
                id = R.color.sunny;
                break;
            case "Clouds":
                id = R.color.cloudy;
                break;
            case "Snow":
                id = R.color.snow;
                break;
            case "Thunderstorm":
                id = R.color.thunderstorm;
                break;
            case "Drizzle":
                id = R.color.drizzle;
                break;
            case "Atmosphere":
                id = R.color.atmosphere;
                break;
            case "Extreme":
                id = R.color.extreme;
                break;
            case "Additional":
                id = R.color.additional;
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(id, context.getTheme());
        } else {
            color = context.getResources().getColor(id);
        }
        return color;
    }
}
