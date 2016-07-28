package com.example.korolkir.everywheatherdemo;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.korolkir.everywheatherdemo.Model.DailyForecast;
import com.example.korolkir.everywheatherdemo.Model.WeatherRecyclerViewAdapter;

import com.crashlytics.android.Crashlytics;
import com.example.korolkir.everywheatherdemo.Model.Weather;
import com.example.korolkir.everywheatherdemo.Presenter.ForecastPresenterImplementor;
import com.example.korolkir.everywheatherdemo.View.ShowingView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ShowingView,SearchView.OnQueryTextListener {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.current_day_description) TextView currentDayDescription;
    @BindView(R.id.current_day_temperature) TextView currentDayTemp;
    @BindView(R.id.current_day_speed) TextView currentDaySpeed;
    @BindView(R.id.current_day_temperature_range) TextView currentDayTemperatureRange;
    @BindView(R.id.current_day_image) ImageView image;
    @BindView(R.id.current_day_linear_layout) LinearLayout currentDayLinear;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private ForecastPresenterImplementor mPresenter;
    private List<DailyForecast> mDailyForecastList;
    private WeatherRecyclerViewAdapter mAdapter;
    //private Suggestion mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mDailyForecastList = new ArrayList<>();
        mAdapter = new WeatherRecyclerViewAdapter(this,mDailyForecastList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .size(1)
                        .build());
        mPresenter = new ForecastPresenterImplementor(this);
        mPresenter.getForecast();
        //mProvider = new Suggestion(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MainActivity.class)));
        searchView.setIconifiedByDefault(false);
        return true;
    }


    @Override
    public void showCurrentDayTemperature(int temperature) {
        currentDayTemp.setText(String.format("%dº", temperature));
    }

    @Override
    public void showCurrentDayDescription(String description) {
        currentDayDescription.setText(description);
    }

    @Override
    public void showCurrentDayWindSpeed(int speed) {
        currentDaySpeed.setText(String.format("%d mph", speed));
    }

    @Override
    public void showCurrentDayTemperatureRange(int minTemperature, int maxTemperature) {
        currentDayTemperatureRange.setText(String.format("%dº - %dº", minTemperature, maxTemperature));
    }

    @Override
    public void showCurrentDayImage(int imageId) {
        Picasso.with(this).load(imageId).
                into(image);
    }

    @Override
    public void setCurrentDayColor(int color) {
        currentDayLinear.setBackgroundColor(color);
        toolbar.setBackgroundColor(color);
    }

    @Override
    public void showWeatherList(List<DailyForecast> weatherList) {
        mDailyForecastList.clear();
        mDailyForecastList.addAll(weatherList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        //mProvider.getCitiesList(newText);
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                CitySuggestionProvider.AUTHORITY, CitySuggestionProvider.MODE);
        suggestions.saveRecentQuery(newText, null);
        return false;
    }
}
