package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Cindy on 2016/7/19.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("76ee57f8e5f8bd628cc9586e93d428d5")
                .server("http://parseserver-ps662-env.us-east-1.elasticbeanstalk.com/parse/")
                //.clientKey("") 公開的不需要clientKey
                .build()
        );
    }
}
