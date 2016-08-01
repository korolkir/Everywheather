package com.example.korolkir.everywheatherdemo;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.korolkir.everywheatherdemo.View.CitySuggestion;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;
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


