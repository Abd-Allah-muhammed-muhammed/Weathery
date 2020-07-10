package com.abdallah.weathery.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abdallah.weathery.model.weather_local_room.Weather;

@Database(entities = {Weather.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}