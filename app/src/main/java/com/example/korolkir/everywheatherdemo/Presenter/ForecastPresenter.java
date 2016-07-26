package com.example.korolkir.everywheatherdemo.Presenter;

import com.example.korolkir.everywheatherdemo.Model.Forecast;
import com.example.korolkir.everywheatherdemo.Model.WeeklyForecast;

/**
 * Created by korolkir on 25.07.16.
 */
public interface ForecastPresenter {
    void applyForecast(WeeklyForecast forecast);
    void getForecast();
}
