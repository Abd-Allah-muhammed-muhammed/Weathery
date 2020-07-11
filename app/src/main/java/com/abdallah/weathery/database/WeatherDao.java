package com.abdallah.weathery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.abdallah.weathery.model.local.WeatherLocal;


import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM WeatherLocal")
    LiveData<List<WeatherLocal>> getAll();

    @Insert
    void insertAll(WeatherLocal... weatherLocals);


}
