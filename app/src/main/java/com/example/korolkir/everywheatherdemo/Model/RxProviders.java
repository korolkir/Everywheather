package com.example.korolkir.everywheatherdemo.Model;

import java.util.List;

import io.rx_cache.Actionable;
import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.EvictDynamicKeyGroup;
import io.rx_cache.EvictProvider;
import rx.Observable;

/**
 * Created by korolkir on 30.07.16.
 */
public interface RxProviders {
    @Actionable
    Observable<List<WeeklyForecast>> getForecast(Observable<List<WeeklyForecast>> message, EvictProvider evictProvider);

    @Actionable
    Observable<List<WeeklyForecast>> forecastDynamicKey(Observable<List<WeeklyForecast>> message, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

    @Actionable
    Observable<List<WeeklyForecast>> forecastDynamicKeyGroup(Observable<List<WeeklyForecast>> message, DynamicKeyGroup dynamicKeyGroup, EvictDynamicKeyGroup evictDynamicKey);
}
