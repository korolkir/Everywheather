package com.example.korolkir.everywheatherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.hello_world) TextView helloWorld;
    private ForecastPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new ForecastPresenter(this);
        mPresenter.getForecast();
    }

    public void showForecast(Forecast forecast) {
        Log.i("MainActivity", "Forecast showing!");
        showCurrentDayForecast(forecast.getWeatherList().get(0));
    }

    private void showCurrentDayForecast(Weather weather) {
        TextView currentDayTemp = ButterKnife.findById(this, R.id.current_day_temperature);
        currentDayTemp.setText(weather.getTemperature()+"º");
        TextView currentDayDescription = ButterKnife.findById(this, R.id.current_day_description);
        currentDayDescription.setText(weather.getDescription());
        TextView currentDaySpeed = ButterKnife.findById(this, R.id.current_day_speed);
        currentDaySpeed.setText(String.valueOf(weather.getSpeed()) + " mph");
        TextView currentDayTemperatureRange = ButterKnife.findById(this, R.id.current_day_temperature_range);
        currentDayTemperatureRange.setText(String.valueOf(weather.getTempMin()) +"º"+ " - " + weather.getTempMax() + "º");
    }


}
