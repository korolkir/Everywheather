package com.example.korolkir.everywheatherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.hello_world) TextView helloWorld;
    private ForecastPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new ForecastPresenterImpl(this);
        mPresenter.getForecast();
    }

    public void showForecast() {
        Log.i("MainActivity", "Forecast showing!");
    }


}
