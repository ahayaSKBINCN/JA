package com.ahaya.earthquakeviewer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface Earthquake {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insertEarthquakes(List<Earthquake> earthquakes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void instertEarthquake(Earthquake earthquake);

    @Delete
    public void deleteEarthquake(Earthquake earthquake);

    @Query("SELECT * FROM earthquake ORDER BY mDate DESC")
    public LiveData<List<Earthquake>> loadAllEarthquakes();
}
