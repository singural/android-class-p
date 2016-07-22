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
        ParseObject.registerSubclass(Drink.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AMZonO4reukGZVjpdRDNDrpzeQKxWm7S65NMeImw")
                .server("https://parseapi.back4app.com/")

//                .applicationId("76ee57f8e5f8bd628cc9586e93d428d5") //老師的parse server (公開的)
//                .server("http://parseserver-ps662-env.us-east-1.elasticbeanstalk.com/parse/") //老師的parse server(公開的)

                .clientKey("NryzXhulPTMEkIJuNlM71ByZg6N181qCYxmIkB6k") //公開的不需要clientKey
                .enableLocalDataStore() //開啟local database的功能
                .build()
        );
    }
}
