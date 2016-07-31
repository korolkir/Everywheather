package com.example.korolkir.everywheatherdemo.Model;

import java.io.File;
import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.internal.RxCache;
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
 * Created by korolkir on 30.07.16.
 */
public class Repository {

    private final RxProviders cacheProviders;
    private Providers providers;
    private OpenweathermapAPI api;


    public Repository(File cacheDir) {

        cacheProviders = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(RxProviders.class);
        providers = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(Providers.class);

    }

    public Observable<WeeklyForecast> getForecast() {
        return providers.getForecast(getExpensiveForecast("Minsk"));
    }

    public Observable<WeeklyForecast> getForecastFromCache() {
        return providers.forecastWith5MinutesLifeTime(getExpensiveForecast("Minsk"));
    }


    public Observable<WeeklyForecast> getMocksPaginate(final int page, final boolean update) {
        return providers.getMocksPaginateEvictingPerPage(getExpensiveForecast("Minsk"), new DynamicKey(page), new EvictDynamicKey(update));
    }

    public Observable<WeeklyForecast> getMocksWithFiltersPaginate(final String filter, final int page, final boolean updateFilter) {
        return providers.getMocksPaginateWithFiltersEvictingPerFilter(getExpensiveForecast("Minsk"), new DynamicKeyGroup(filter, page), new EvictDynamicKey(updateFilter));
    }

    //In a real use case, here is when you build your observable with the expensive operation.
    //Or if you are making http calls you can use Retrofit to get it out of the box.
    private Observable<WeeklyForecast> getExpensiveForecast(String city) {
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
