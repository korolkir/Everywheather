package com.example.korolkir.everywheatherdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by korolkir on 21.07.16.
 */
public class ForecastCreator {

    public void createForecast(final ForecastPresenter presenter) {
        Subscription subscription = Single.create(new Single.OnSubscribe<Forecast>() {
            @Override
            public void call(SingleSubscriber singleSubscriber) {
                String strJson = downloadJson();
                JsonParser jsonParser = new JsonParser();
                Forecast forecast = null;
                try {
                    forecast = jsonParser.parseForecast(strJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                singleSubscriber.onSuccess(forecast);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Forecast>() {
                    @Override
                    public void call(Forecast forecast) {
                        presenter.applyForecast(forecast);
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       throwable.printStackTrace();
                    }
                });
    }

    private String downloadJson() {
        String strJson = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + "Minsk" +
                    "&mode=json&APPID=88d9813e41720c056489fc6ed1c90e9f\n");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            strJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strJson;
    }

}
