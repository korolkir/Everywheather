package com.example.korolkir.everywheatherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private ForecastPresenter mPresenter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mPresenter = new ForecastPresenter(this);
        mPresenter.getForecast();
    }

    public void showCurrentDayTemperature(int temperature) {
        TextView currentDayTemp = ButterKnife.findById(this, R.id.current_day_temperature);
        currentDayTemp.setText(String.valueOf(temperature) +"º");
    }

    public void showCurrentDayDescription(String description) {
        TextView currentDayDescription = ButterKnife.findById(this, R.id.current_day_description);
        currentDayDescription.setText(description);
    }

    public void showCurrentDayWindSpeed(int speed) {
        TextView currentDaySpeed = ButterKnife.findById(this, R.id.current_day_speed);
        currentDaySpeed.setText(String.valueOf(speed) + " mph");
    }

    public void showCurrentDayTemperatureRange(int minTemperature, int maxTemperature) {
        TextView currentDayTemperatureRange = ButterKnife.findById(this, R.id.current_day_temperature_range);
        currentDayTemperatureRange.setText(String.valueOf(minTemperature) +"º"+ " - " + String.valueOf(maxTemperature) + "º");
    }

    public void showCurrentDayImage(int imageId) {
        ImageView image = ButterKnife.findById(this,R.id.current_day_image);
        Picasso.with(this).load(imageId).
                into(image);
    }

    public void showWeatherList(List<Weather> weatherList) {
        WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter(this,weatherList);
        recyclerView.setAdapter(adapter);
    }

}
