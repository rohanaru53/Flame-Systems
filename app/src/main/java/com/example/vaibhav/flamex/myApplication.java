package com.example.vaibhav.flamex;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Rohan on 31-Oct-15.
 */
public class myApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }


}
