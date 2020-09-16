package com.ahaya.earthquakeviewer.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ahaya.earthquakeviewer.dao.UserDao;
import com.ahaya.earthquakeviewer.entity.Earthquake;
import com.ahaya.earthquakeviewer.entity.EarthquakeTypeConverters;
import com.ahaya.earthquakeviewer.entity.User;

/**
 * @Useage
 * AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"database-name").build();
 */
@Database(entities = {Earthquake.class, User.class}, version = 1,exportSchema = false)
@TypeConverters({EarthquakeTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.ahaya.earthquakeviewer.dao.EarthquakeDao earthquakeDao();
    public abstract UserDao userDao();
}
