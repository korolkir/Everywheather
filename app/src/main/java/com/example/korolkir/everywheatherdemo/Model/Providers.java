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
    Observable<WeeklyForecast> getForecast(Observable<WeeklyForecast> oMocks);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<WeeklyForecast> forecastWith5MinutesLifeTime(Observable<WeeklyForecast> oMocks);

    Observable<WeeklyForecast> getMocksEvictProvider(Observable<WeeklyForecast> oMocks, EvictProvider evictProvider);

    Observable<WeeklyForecast> getMocksPaginate(Observable<WeeklyForecast> oMocks, DynamicKey page);

    Observable<WeeklyForecast> getMocksPaginateEvictingPerPage(Observable<WeeklyForecast> oMocks, DynamicKey page, EvictDynamicKey evictPage);

    Observable<WeeklyForecast> getMocksPaginateWithFiltersEvictingPerFilter(Observable<WeeklyForecast> oMocks, DynamicKeyGroup filterPage, EvictDynamicKey evictFilter);
}
