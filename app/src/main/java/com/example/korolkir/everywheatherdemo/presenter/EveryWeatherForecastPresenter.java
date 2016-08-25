package com.example.korolkir.everywheatherdemo.presenter;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.example.korolkir.everywheatherdemo.EveryWheatherApllication;
import com.example.korolkir.everywheatherdemo.model.DailyForecast;
import com.example.korolkir.everywheatherdemo.model.EveryWeatherForecastCreator;
import com.example.korolkir.everywheatherdemo.model.suggestions.CitySuggestion;
import com.example.korolkir.everywheatherdemo.model.suggestions.CitySuggestionCreator;
import com.example.korolkir.everywheatherdemo.model.WeatherColorSelector;
import com.example.korolkir.everywheatherdemo.model.WeatherImageSelector;
import com.example.korolkir.everywheatherdemo.model.WeeklyForecast;
import com.example.korolkir.everywheatherdemo.R;
import com.example.korolkir.everywheatherdemo.view.ShowingView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by korolkir on 21.07.16.
 */
public class EveryWeatherForecastPresenter implements ForecastPresenter {

    private static final int RESULT_OK = -1;
    private ShowingView showingView;
    private EveryWeatherForecastCreator forecastCreator;
    private LocationManager locationManager;
    private CitySuggestionCreator citySuggestionCreator;
    private Context context;
    private List<CitySuggestion> cities;

    public EveryWeatherForecastPresenter(ShowingView view) {
        this.showingView = view;
        forecastCreator = new EveryWeatherForecastCreator(this);
        locationManager = (LocationManager) EveryWheatherApllication.getContext().getSystemService(Context.LOCATION_SERVICE);
        context = EveryWheatherApllication.getContext();
        citySuggestionCreator = new CitySuggestionCreator();
        cities = new ArrayList<>();
    }

    @Override
    public void getForecastByCity(String city) {
        forecastCreator.createForecastByCity(city);
    }

    @Override
    public void getForecastByCurrentPlace() {
        forecastCreator.createForecastByCoordinates();
    }

    @Override
    public void getForecastFromCache() {
        forecastCreator.createForecastFromCache();
    }

    @Override
    public void applyForecast(WeeklyForecast forecast) {
        if(forecast != null) {
            showCurrentDayForecast(forecast.getDailyForecastList().get(0), forecast.getCity().getName());
            forecast.getDailyForecastList().remove(0);
            showingView.showWeatherList(forecast.getDailyForecastList());
            showingView.setRefreshing(false);
        }
    }

    private void showCurrentDayForecast(DailyForecast dailyForecast, String city) {
        showingView.showCurrentDayTemperature(dailyForecast.getTemp().getDay());
        showingView.showCurrentDayDescription(dailyForecast.getWeather().get(0).getDescription());
        showingView.showCurrentDayWindSpeed((int) dailyForecast.getSpeed());
        showingView.showCurrentDayTemperatureRange(dailyForecast.getTemp().getMin(), dailyForecast.getTemp().getMax());
        showingView.showCurrentDayImage(new WeatherImageSelector().getImageIdAccordingTypeOfWeather(dailyForecast.getWeather()
                .get(0).getMain()));
        showingView.setCurrentDayColor(new WeatherColorSelector(context).
                getColorAccordingTypeOfWeather(dailyForecast.getWeather().get(0).getMain()));
        showingView.setCityName(city);
    }

    public void onSuggestionClick(String city) {
        getForecastByCity(city);
        showingView.clearSearchFocus();
        showingView.setCityName(city);
        showingView.setSearchText(city);
    }

    @Override
    public void onRefresh() {
        if(checkGpsProviderIsEnable()) {
            getForecastByCurrentPlace();
            showingView.setRefreshing(true);
        } else {
            showingView.askToEnableGpsProvider();
        }
    }

    @Override
    public void gpsResult() {
        if(checkGpsProviderIsEnable()) {
            getForecastByCurrentPlace();
        } else {
            showingView.showToastMessagge(context.getResources().getString(R.string.gps_disable_toast_message));
        }
    }

    @Override
    public void facebookShareResult(int resultCode) {
        if(resultCode == RESULT_OK){
            showingView.deleteAdvertisment();
        }
    }

    @Override
    public void onSearchTextChanged(String newQuery) {
        Observable<CitySuggestion> observable = citySuggestionCreator.getCitiesList(newQuery);
        observable.subscribe(new Subscriber<CitySuggestion>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CitySuggestion citySuggestion) {
                cities.add(citySuggestion);
                showingView.showSuggestions(cities);

            }
        });
        cities.clear();
    }

    @Override
    public void userAcceptToTurnOnGps() {
        showingView.goToGpsSettings();
    }

    @Override
    public void currentFacebookProfileChaned() {
        showingView.refreshCurrentFacebookProfileName();
    }

    @Override
    public void userDeclineToTurnOnGps() {
        showingView.setRefreshing(false);
    }

    private boolean checkGpsProviderIsEnable() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

}
