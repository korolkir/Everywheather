package com.example.korolkir.everywheatherdemo.model;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivecache.Provider;
import io.reactivecache.ReactiveCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by korolkir on 02.08.16.
 */
public class ForecastRepository {

    private static final String MODE = "json";
    private static final String APPID  = "88d9813e41720c056489fc6ed1c90e9f";

    private  Provider<WeeklyForecast> cacheProvider;
    private  OpenweathermapAPI api;
    private Retrofit retrofit;


    public ForecastRepository(File filesDir) {
        ReactiveCache reactiveCache = new ReactiveCache.Builder()
                .using(filesDir, new GsonSpeaker());
        this.cacheProvider = reactiveCache.<WeeklyForecast>provider().encrypt(false).lifeCache(7, TimeUnit.DAYS).withKey("forecast");
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .build();
        api = retrofit.create(OpenweathermapAPI.class);
    }

    public Observable<WeeklyForecast> getForecastByCity(String city) {
        return getForecastObservableByCity(city).compose(cacheProvider.replace());
    }

    public Observable<WeeklyForecast> getForecastByCoordinates(double lat, double lon) {
        return getForecastObservableByCoordinates(lat, lon).compose(cacheProvider.replace());
    }


    public Observable<WeeklyForecast> getForecastFromCache() {
        return cacheProvider.read();
    }



    private Observable<WeeklyForecast> getForecastObservableByCity(String city) {
        Observable<WeeklyForecast> call = api.getWeatherListByCity(city, MODE, APPID);
        return call;
    }

    private Observable<WeeklyForecast> getForecastObservableByCoordinates(double lat, double lon) {
        Observable<WeeklyForecast> call = api.getWeatherListByCoordinates(String.valueOf(lat), String.valueOf(lon),
                MODE, APPID);
        return call;
    }
}
