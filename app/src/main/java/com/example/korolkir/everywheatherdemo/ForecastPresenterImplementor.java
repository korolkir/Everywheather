package com.example.korolkir.everywheatherdemo;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastPresenterImplementor implements ForecastPresenter {

    private ShowingView mView;
    private ForecastCreatorImplementor mForecastCreator;

    public ForecastPresenterImplementor(ShowingView mView) {
        this.mView = mView;
        this.mForecastCreator = new ForecastCreatorImplementor();
    }

    @Override
    public void getForecast() {
        mForecastCreator.createForecast(this);
    }

    @Override
    public void applyForecast(Forecast forecast) {
        if(forecast != null) {
            showForecast(forecast);
        }
    }

    public void showForecast(Forecast forecast) {
        showCurrentDayForecast(forecast.getWeatherList().get(0));
        forecast.getWeatherList().remove(0);
        mView.showWeatherList(forecast.getWeatherList());
    }

    private void showCurrentDayForecast(Weather weather) {
        mView.showCurrentDayTemperature(weather.getTemperature());
        mView.showCurrentDayDescription(weather.getDescription());
        mView.showCurrentDayWindSpeed(weather.getSpeed());
        mView.showCurrentDayTemperatureRange(weather.getTempMin(),weather.getTempMax());
        mView.showCurrentDayImage(new WeatherImageSelector().getImageIdAccordingTypeOfWeather(weather.getTypeOfWeather()));
        mView.setCurrentDayColor(new WeatherColorSelector(mView.getContext()).
                getColorAccordingTypeOfWeather(weather.getTypeOfWeather()));
    }
}
