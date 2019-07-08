package com.example.myapplication;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("moisestrejo")
                .clientKey("2952216")
                .server("http://mtrejo0-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

    }
}
