package com.example.alexey.parsecrashproject;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;



/**
 * Created by Alexey on 16.11.2014.
 */
public class MyApplication extends Application {
    public static final String EVENT_GROUP_NAME = "ALL";
    public static final String APP_IP="demo";
    public static final String CLIENT_KEY="demo";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(CalendarEvent.class);
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(this, APP_IP, CLIENT_KEY);

        ParseUser user = ParseUser.getCurrentUser();

        if(user==null){
            Log.d("myLogs","create new User");
            ParseUser.enableAutomaticUser();
            ParseUser.getCurrentUser().pinInBackground();
        }else{
            Log.d("myLogs","user from dataStore");
        }
    }
}
