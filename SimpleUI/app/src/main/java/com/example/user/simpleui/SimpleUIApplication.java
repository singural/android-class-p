package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Cindy on 2016/7/19.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Order.class); //註冊可以上傳的Order
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AMZonO4reukGZVjpdRDNDrpzeQKxWm7S65NMeImw")
                .server("https://parseapi.back4app.com/")
                .clientKey("NryzXhulPTMEkIJuNlM71ByZg6N181qCYxmIkB6k") //公開的不需要clientKey
                .build()
        );
    }
}
