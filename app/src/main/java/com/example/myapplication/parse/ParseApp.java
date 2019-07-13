package com.example.myapplication.parse;

import android.app.Application;

import com.example.myapplication.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // link to the Post class
        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("moisestrejo")
                .clientKey("2952216")
                .server("http://mtrejo0-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

    }
}
