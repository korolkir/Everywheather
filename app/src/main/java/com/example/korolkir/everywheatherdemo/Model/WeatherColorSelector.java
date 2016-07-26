package com.example.korolkir.everywheatherdemo.Model;

import android.content.Context;

import com.example.korolkir.everywheatherdemo.R;

/**
 * Created by korolkir on 24.07.16.
 */
public class WeatherColorSelector {

    private Context mContext;

    public WeatherColorSelector(Context mContext) {
        this.mContext = mContext;
    }

    public int getColorAccordingTypeOfWeather(String typeOfWeather) {
        int color = 0;
        switch (typeOfWeather) {
            case "Rain":
                color = mContext.getResources().getColor(R.color.rain);
                break;
            case "Clear":
                color = mContext.getResources().getColor(R.color.sunny);
                break;
            case "Clouds":
                color = mContext.getResources().getColor(R.color.cloudy);
                break;

            case "Snow":
                color = mContext.getResources().getColor(R.color.snow);
                break;
            case "Thunderstorm":
                color = mContext.getResources().getColor(R.color.thunderstorm);
                break;
            case "Drizzle":
                color = mContext.getResources().getColor(R.color.drizzle);
                break;
            case "Atmosphere":
                color = mContext.getResources().getColor(R.color.atmosphere);
                break;
            case "Extreme":
                color = mContext.getResources().getColor(R.color.extreme);
                break;
            case "Additional":
                color = mContext.getResources().getColor(R.color.additional);
                break;
        }
        return color;
    }
}
