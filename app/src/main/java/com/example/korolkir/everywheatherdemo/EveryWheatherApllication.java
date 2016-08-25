package com.example.korolkir.everywheatherdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by korolkir on 14.08.16.
 */
public class EveryWheatherApllication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
