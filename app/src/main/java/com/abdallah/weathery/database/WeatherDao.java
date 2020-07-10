package com.abdallah.weathery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.abdallah.weathery.model.weather_local_room.Weather;


import java.util.List;

import io.reactivex.Single;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM Weather")
    LiveData<List<Weather>> getAll();

    @Insert
    void insertAll(Weather... weathers);

    @Delete
    void delete(Weather weather);
}
