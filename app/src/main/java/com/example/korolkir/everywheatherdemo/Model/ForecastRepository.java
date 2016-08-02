package com.example.korolkir.everywheatherdemo.Model;

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

    private  Provider<WeeklyForecast> cacheProvider;
    private  OpenweathermapAPI api;

    public ForecastRepository(File filesDir) {
        ReactiveCache reactiveCache = new ReactiveCache.Builder()
                .using(filesDir, new GsonSpeaker());
        this.cacheProvider = reactiveCache.<WeeklyForecast>provider().encrypt(false).lifeCache(7, TimeUnit.DAYS).withKey("forecast");

    }

    public Observable<WeeklyForecast> getForecast(String city) {
        return getForecastObervable(city).compose(cacheProvider.replace());
    }

    public Observable<WeeklyForecast> getForecastFromCache() {
        return cacheProvider.read();
    }



    private Observable<WeeklyForecast> getForecastObervable(String city) {
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
        Observable<WeeklyForecast> call = api.getWeatherList(city, "json", "88d9813e41720c056489fc6ed1c90e9f");
        return call.subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
    }

}
