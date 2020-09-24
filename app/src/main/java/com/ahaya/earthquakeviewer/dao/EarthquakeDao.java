package com.ahaya.earthquakeviewer.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.ahaya.earthquakeviewer.entity.Earthquake;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EarthquakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insertEarthquakes(ArrayList<Earthquake> earthquakes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertEarthquake(Earthquake earthquake);

    @Query("DELETE FROM Earthquake WHERE id = :id")
    public int deleteEarthquake(long id);

    @Query("SELECT * FROM earthquake ORDER BY date DESC")
    public LiveData<List<Earthquake>> loadAllEarthquakes();

    @Query("SELECT * FROM EARTHQUAKE")
    Cursor findAll();

    @Update
    int update(Earthquake earthquake);
}
