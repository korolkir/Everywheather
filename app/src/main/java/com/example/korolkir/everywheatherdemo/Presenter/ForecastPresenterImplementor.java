package com.example.korolkir.everywheatherdemo.Presenter;

import com.example.korolkir.everywheatherdemo.Model.DailyForecast;
import com.example.korolkir.everywheatherdemo.Model.ForecastCreatorImplementor;
import com.example.korolkir.everywheatherdemo.Model.WeatherColorSelector;
import com.example.korolkir.everywheatherdemo.Model.WeatherImageSelector;
import com.example.korolkir.everywheatherdemo.Model.WeeklyForecast;
import com.example.korolkir.everywheatherdemo.View.ShowingView;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastPresenterImplementor implements ForecastPresenter {

    private ShowingView mView;
    private ForecastCreatorImplementor mForecastCreator;

    public ForecastPresenterImplementor(ShowingView mView) {
        this.mView = mView;
        this.mForecastCreator = new ForecastCreatorImplementor(mView.getContext(), this);
    }

    @Override
    public void getForecastByCity(String city) {
        mForecastCreator.createForecastByCity(city);
    }

    @Override
    public void getForecastByCurrentPlace() {
        mForecastCreator.createForecastByCoordinates();
    }

    @Override
    public void getForecastFromCache() {
        mForecastCreator.createForecastFromCache();
    }

    @Override
    public void applyForecast(WeeklyForecast forecast) {
        if(forecast != null) {
            showCurrentDayForecast(forecast.getDailyForecastList().get(0), forecast.getCity().getName());
            forecast.getDailyForecastList().remove(0);
            mView.showWeatherList(forecast.getDailyForecastList());
        }

    }

    private void showCurrentDayForecast(DailyForecast dailyForecast, String city) {
        mView.showCurrentDayTemperature(dailyForecast.getTemp().getDay());
        mView.showCurrentDayDescription(dailyForecast.getWeather().get(0).getDescription());
        mView.showCurrentDayWindSpeed((int) dailyForecast.getSpeed());
        mView.showCurrentDayTemperatureRange(dailyForecast.getTemp().getMin(), dailyForecast.getTemp().getMax());
        mView.showCurrentDayImage(new WeatherImageSelector().getImageIdAccordingTypeOfWeather(dailyForecast.getWeather()
                .get(0).getMain()));
        mView.setCurrentDayColor(new WeatherColorSelector(mView.getContext()).
                getColorAccordingTypeOfWeather(dailyForecast.getWeather().get(0).getMain()));
        mView.setCityName(city);
    }

}
