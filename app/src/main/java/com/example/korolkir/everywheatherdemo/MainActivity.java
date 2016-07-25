package com.example.korolkir.everywheatherdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.sva.JJSearchView;

import com.cjj.sva.anim.controller.JJCircleToLineAlphaController;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ShowingView {

    private ForecastPresenter mPresenter;
    JJCircleToLineAlphaController controller;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        final JJSearchView mJJSearchView = (JJSearchView) findViewById(R.id.jjsv);
        controller = new JJCircleToLineAlphaController();
        mJJSearchView.setController(controller);
        mJJSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJJSearchView.resetAnim();

                /* controller.getSearchView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        v.setEnabled(true);
                    }
                });
                */
                mJJSearchView.startAnim();
            }
        });
        controller.startAnim();
        controller.resetAnim();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .size(1)
                        .build());
        mPresenter = new ForecastPresenter(this);
        mPresenter.getForecast();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void showCurrentDayTemperature(int temperature) {
        TextView currentDayTemp = ButterKnife.findById(this, R.id.current_day_temperature);
        currentDayTemp.setText(String.format("%dº", temperature));
        controller.startAnim();

    }

    @Override
    public void showCurrentDayDescription(String description) {
        TextView currentDayDescription = ButterKnife.findById(this, R.id.current_day_description);
        currentDayDescription.setText(description);
    }

    @Override
    public void showCurrentDayWindSpeed(int speed) {
        TextView currentDaySpeed = ButterKnife.findById(this, R.id.current_day_speed);
        currentDaySpeed.setText(String.valueOf(speed) + " mph");
    }

    @Override
    public void showCurrentDayTemperatureRange(int minTemperature, int maxTemperature) {
        TextView currentDayTemperatureRange = ButterKnife.findById(this, R.id.current_day_temperature_range);
        currentDayTemperatureRange.setText(String.valueOf(minTemperature) +"º"+ " - " + String.valueOf(maxTemperature) + "º");
    }

    @Override
    public void showCurrentDayImage(int imageId) {
        ImageView image = ButterKnife.findById(this,R.id.current_day_image);
        Picasso.with(this).load(imageId).
                into(image);
    }

    @Override
    public void setCurrentDayColor(int color) {
        LinearLayout currentDayLinear = ButterKnife.findById(this, R.id.current_day_linear_layout);
        currentDayLinear.setBackgroundColor(color);
    }

    @Override
    public void showWeatherList(List<Weather> weatherList) {
        WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter(this,weatherList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
