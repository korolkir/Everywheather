package com.example.korolkir.everywheatherdemo.model;

/**
 * Created by korolkir on 25.07.16.
 */
public interface ForecastCreator {
    void createForecastByCity(String city);
    void createForecastFromCache();
    void createForecastByCoordinates();
    void applyCurrentPlaceCoordinates(Coordinates coordinates);
}
