package com.example.korolkir.everywheatherdemo;

import android.util.Log;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastPresenter {

    private MainActivity mView;
    private ForecastCreator mForecastCreator;

    public ForecastPresenter(MainActivity mView) {
        this.mView = mView;
        this.mForecastCreator = new ForecastCreator();
    }

    public void getForecast() {
        mForecastCreator.createForecast(this);
    }

    public void applyForecast(Forecast forecast) {
        if(forecast != null) showForecast(forecast);

    }

    public void showForecast(Forecast forecast) {
        showCurrentDayForecast(forecast.getWeatherList().get(0));
    }

    private void showCurrentDayForecast(Weather weather) {
        mView.showCurrentDayTemperature(weather.getTemperature());
        mView.showCurrentDayDescription(weather.getDescription());
        mView.showCurrentDayWindSpeed(weather.getSpeed());
        mView.showCurrentDayTemperatureRange(weather.getTempMin(),weather.getTempMax());
    }
}
