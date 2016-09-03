package com.example.korolkir.everywheatherdemo.model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by korolkir on 26.07.16.
 */
public interface OpenweathermapAPI {
    @GET("/data/2.5/forecast/daily")
    Observable<WeeklyForecast> getWeatherListByCity(@Query("q") String city, @Query("mode") String mode,
                                                    @Query("APPID") String appId);
    @GET("/data/2.5/forecast/daily")
    Observable<WeeklyForecast> getWeatherListByCoordinates(@Query("lat") String lat, @Query("lon") String lon,
                                                           @Query("mode") String mode, @Query("APPID") String appId);
}




