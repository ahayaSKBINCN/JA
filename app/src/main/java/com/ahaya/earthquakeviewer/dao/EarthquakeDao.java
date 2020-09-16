package com.ahaya.earthquakeviewer.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.ahaya.earthquakeviewer.entity.Earthquake;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EarthquakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insertEarthquakes(ArrayList<Earthquake> earthquakes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void instertEarthquake(Earthquake earthquake);

    @Delete
    public void deleteEarthquake(Earthquake earthquake);

    @Query("SELECT * FROM earthquake ORDER BY date DESC")
    public LiveData<List<Earthquake>> loadAllEarthquakes();
}
