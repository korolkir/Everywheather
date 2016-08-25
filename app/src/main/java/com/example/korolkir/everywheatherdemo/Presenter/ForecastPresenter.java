package com.example.korolkir.everywheatherdemo.presenter;

import com.example.korolkir.everywheatherdemo.model.WeeklyForecast;

/**
 * Created by korolkir on 25.07.16.
 */
public interface ForecastPresenter {

    void applyForecast(WeeklyForecast forecast);

    void getForecastByCity(String city);

    void getForecastByCurrentPlace();

    void getForecastFromCache();

    void onSuggestionClick(String city);

    void onRefresh();

    void gpsResult();

    void facebookShareResult(int resultCode);

    void onSearchTextChanged(String newQuery);

    void userAcceptToTurnOnGps();

    void currentFacebookProfileChaned();

    void userDeclineToTurnOnGps();
}
