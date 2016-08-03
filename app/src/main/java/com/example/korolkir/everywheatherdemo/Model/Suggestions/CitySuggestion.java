package com.example.korolkir.everywheatherdemo.Model.Suggestions;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by korolkir on 29.07.16.
 */
public class CitySuggestion implements SearchSuggestion{

    private String content;

    public CitySuggestion(String content) {
        this.content = content;
    }

    @Override
    public String getBody() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    public static final Parcelable.Creator<CitySuggestion> CREATOR
            = new Parcelable.Creator<CitySuggestion>() {


        @Override
        public CitySuggestion createFromParcel(Parcel source) {
            return null;
        }

        public CitySuggestion[] newArray(int size) {
            return new CitySuggestion[size];
        }
    };

}
