package com.ahaya.earthquakeviewer.database;

import android.content.Context;

import androidx.room.Room;


public class EarthquakeDatabaseAccessor {
    private static AppDatabase appDatabase;
    private static final String APP_DATABASE_NAME ="APP_DATABASE_NAME";

    private EarthquakeDatabaseAccessor(){}

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context,AppDatabase.class,APP_DATABASE_NAME).build();
        }
        return appDatabase;
    }
}
