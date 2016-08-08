package com.example.korolkir.everywheatherdemo.Model;

import android.content.Context;
import android.util.Log;

import com.example.korolkir.everywheatherdemo.Presenter.ForecastPresenter;


import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastCreatorImplementor implements ForecastCreator {

    Context context;
    ForecastRepository repository;
    ForecastPresenter presenter;
    LocationObserver locationObserver;

    public ForecastCreatorImplementor(Context context, ForecastPresenter presenter) {
        this.context = context;
        File fileDir = context.getFilesDir();
        repository = new ForecastRepository(fileDir);
        this.presenter = presenter;
    }

    @Override
    public void createForecastByCity(final String city) {
        repository.getForecastByCity(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeeklyForecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Errrrror", e.toString());
                    }

                    @Override
                    public void onNext(WeeklyForecast forecast) {
                        presenter.applyForecast(forecast);
                    }
                });
    }

    @Override
    public void createForecastFromCache() {
        repository.getForecastFromCache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeeklyForecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Errrrror", e.toString());
                    }

                    @Override
                    public void onNext(WeeklyForecast forecast) {
                        presenter.applyForecast(forecast);
                    }
                });
    }

    @Override
    public void createForecastByCoordinates() {
        locationObserver = new LocationObserver(context);
        locationObserver.getCoordinatesObservable().subscribe(new Subscriber<Coordinates>() {
            @Override
            public void onCompleted() {
                Log.i("Observ","completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Observ",e.toString());
            }

            @Override
            public void onNext(Coordinates coordinates) {
                Log.i("Observ", String.valueOf(coordinates.getLat()));
                unsubscribe();
                locationObserver.deactivateLocationListener();
                repository.getForecastByCoordinates(coordinates.getLat(),coordinates.getLon())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<WeeklyForecast>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("Errrrror", e.toString());
                            }

                            @Override
                            public void onNext(WeeklyForecast forecast) {
                                presenter.applyForecast(forecast);
                            }
                        });
            }
        });

    }
}
