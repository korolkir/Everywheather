package com.example.korolkir.everywheatherdemo;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastPresenterImpl implements ForecastPresenter{

    private MainActivity mView;
    private ForecastCreator mForecastCreator;

    public ForecastPresenterImpl(MainActivity mView) {
        this.mView = mView;
        this.mForecastCreator = new ForecastCreator();
    }

    public void getForecast() {
        mForecastCreator.createForecast(this);
    }


    @Override
    public void applyForecast(Forecast forecast) {
        mView.showForecast();
    }
}
