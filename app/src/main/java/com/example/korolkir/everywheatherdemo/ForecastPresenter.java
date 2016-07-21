package com.example.korolkir.everywheatherdemo;

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
        if(forecast != null) mView.showForecast(forecast);
    }
}
