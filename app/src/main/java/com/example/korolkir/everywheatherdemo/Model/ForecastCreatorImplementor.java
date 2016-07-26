package com.example.korolkir.everywheatherdemo.Model;

import android.util.Log;

import com.example.korolkir.everywheatherdemo.Presenter.ForecastPresenterImplementor;


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

    @Override
    public void createForecast(final ForecastPresenterImplementor presenter) {
        downloadForecast(presenter);
    }

    private void downloadForecast(final ForecastPresenterImplementor presenter) {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .build();
        OpenweathermapAPI api = retrofit.create(OpenweathermapAPI.class);
        Observable<WeeklyForecast> call = api.getWeatherList("Minsk", "json", "88d9813e41720c056489fc6ed1c90e9f");
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WeeklyForecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        Log.i("Lolol", "kmlmk");

                        if (e instanceof HttpException) {
                            HttpException response = (HttpException)e;
                            int code = response.code();
                        }
                    }

                    @Override
                    public void onNext(WeeklyForecast list) {
                        presenter.applyForecast(list);
                    }
                });
    }

}
