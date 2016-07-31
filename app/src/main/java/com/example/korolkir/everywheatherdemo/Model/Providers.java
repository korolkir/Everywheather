package com.example.korolkir.everywheatherdemo.Model;

import com.example.korolkir.everywheatherdemo.Model.WeeklyForecast;

import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import rx.Observable;

/**
 * Created by korolkir on 30.07.16.
 */
interface Providers {

    Observable<WeeklyForecast> getForecast(Observable<WeeklyForecast> forecastObservable);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<WeeklyForecast> forecastWithLifeTime(Observable<WeeklyForecast> forecastObservable);

    Observable<WeeklyForecast> getForecastEvictProvider(Observable<WeeklyForecast> forecastObservable, EvictProvider evictProvider);

    Observable<WeeklyForecast> getForecastPaginate(Observable<WeeklyForecast> forecastObservable, DynamicKey page);

    Observable<WeeklyForecast> getForecastPaginateEvictingPerPage(Observable<WeeklyForecast> forecastObservable, DynamicKey page, EvictDynamicKey evictPage);

    Observable<WeeklyForecast> getForecastPaginateWithFiltersEvictingPerFilter(Observable<WeeklyForecast> forecastObservable, DynamicKeyGroup filterPage, EvictDynamicKey evictFilter);
}