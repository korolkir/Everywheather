package com.example.korolkir.everywheatherdemo.view;

import com.example.korolkir.everywheatherdemo.model.DailyForecast;
import com.example.korolkir.everywheatherdemo.model.suggestions.CitySuggestion;

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

    void setCityName(String city);

    void clearSearchFocus();

    void setSearchText(String city);

    void setRefreshing(boolean b);

    void askToEnableGpsProvider();

    void showToastMessagge(String string);

    void deleteAdvertisment();

    void showSuggestions(List<CitySuggestion> suggestions);

    void goToGpsSettings();

    void refreshCurrentFacebookProfileName();

    void setVisibleMainLayouts();

    void setProgressBarEnable(boolean b);

    void deleteProgressBar();
}


