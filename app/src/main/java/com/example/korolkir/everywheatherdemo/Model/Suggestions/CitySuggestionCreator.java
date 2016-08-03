package com.example.korolkir.everywheatherdemo.Model.Suggestions;

import android.content.Context;

import com.example.korolkir.everywheatherdemo.Model.Suggestions.CitySuggestion;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by korolkir on 27.07.16.
 */
public class CitySuggestionCreator {

    private GoogleApiClient mGoogleApiClient;
    public static final int TYPE_FILTER_CITIES = 5;


    public CitySuggestionCreator(Context context) {
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }



    public Observable<CitySuggestion> getCitiesList(final String query) {
        Observable<CitySuggestion> suggestionObservable = Observable.create(new Observable.OnSubscribe<CitySuggestion>() {
            @Override
            public void call(Subscriber<? super CitySuggestion> subscriber) {
                AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(TYPE_FILTER_CITIES).build();
                PendingResult<AutocompletePredictionBuffer> result =
                        Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, null, filter);
                AutocompletePredictionBuffer buffer = result.await(30, TimeUnit.SECONDS);
                for(AutocompletePrediction prediction: buffer) {
                    subscriber.onNext(new CitySuggestion(String.valueOf(prediction.getPrimaryText(null))));
                }
                buffer.release();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return suggestionObservable;
    }
}


