package com.example.korolkir.everywheatherdemo.Model;

import com.example.korolkir.everywheatherdemo.Presenter.ForecastPresenterImplementor;

/**
 * Created by korolkir on 25.07.16.
 */
public interface ForecastCreator {
    void createForecastByCity(String city);
    void createForecastFromCache();
    void createForecastByCoordinates();
}
