package com.example.korolkir.everywheatherdemo;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

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

/**
 * Created by korolkir on 27.07.16.
 */
public class CitySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.korolkir.everywheatherdemo.CitySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    private GoogleApiClient mGoogleApiClient;
    public static final int TYPE_FILTER_CITIES = 5;
    private List<String> citiesIds;
    private List<String> cities;
    MatrixCursor cursor;
    private int suggestionCount;


    public CitySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);

    }

    @Override
    public boolean onCreate() {
        cities = new ArrayList<>();
        Context context = getContext();
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        cities.clear();
        final String query = uri.getLastPathSegment();
        Log.i("Query", query);
        new SuggestionTask().execute(query);
        cursor = new MatrixCursor(
                new String[] {
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        /*
        int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));
        int lenght = cities.size();
        for (int i = 0; i < lenght && cursor.getCount() < limit; i++) {
            cursor.addRow(new Object[]{ i, cities.get(i), i });
        }
        */
        Log.i("Cursor", String.valueOf(cursor.getCount()));
            return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private class SuggestionTask extends AsyncTask<String,Void,List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {
            String query = params[0];
            AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(TYPE_FILTER_CITIES).build();
            PendingResult<AutocompletePredictionBuffer> result =
                    Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, null, filter);


            result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                @Override
                public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
                    if(autocompletePredictions.getCount() > 0) {
                        Log.i("Prediction", String.valueOf(autocompletePredictions.getCount()));
                        suggestionCount = 0;


                        for (AutocompletePrediction prediction : DataBufferUtils.freezeAndClose(autocompletePredictions)) {
                            Places.GeoDataApi.getPlaceById(mGoogleApiClient, prediction.getPlaceId())
                                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                        @Override
                                        public void onResult(PlaceBuffer places) {
                                            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                                final Place myPlace = places.get(0);
                                                //cities.add(myPlace.getName().toString());
                                                cursor.addRow(new Object[]{ suggestionCount, places.get(0), suggestionCount });
                                                suggestionCount++;
                                                Log.i("Place", String.valueOf(myPlace.getName()));

                                            } else {
                                                Log.i("Place", "Place not found");
                                            }
                                            places.release();
                                        }
                                    });

                        }
                        autocompletePredictions.release();
                    }
                }
            });
            return cities;
        }

    }
}
