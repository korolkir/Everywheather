package com.example.korolkir.everywheatherdemo.Model;

import android.content.Context;
import android.util.Log;

import com.example.korolkir.everywheatherdemo.Presenter.ForecastPresenterImplementor;


import java.io.File;
import java.util.List;

import io.rx_cache.Reply;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastCreatorImplementor implements ForecastCreator {

    Context context;

    public ForecastCreatorImplementor(Context context) {
        this.context = context;
    }

    @Override
    public void createForecast(final ForecastPresenterImplementor presenter, final String city, Boolean freshInfo) {
        File fileDir = context.getFilesDir();
        ForecastRepository repository = new ForecastRepository(fileDir);
        if(freshInfo) {
            repository.getForecast(city)
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
        } else {
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

    }



}
