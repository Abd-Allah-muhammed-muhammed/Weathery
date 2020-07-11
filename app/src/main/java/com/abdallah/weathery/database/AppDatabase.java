package com.abdallah.weathery.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abdallah.weathery.model.local.WeatherLocal;

@Database(entities = {WeatherLocal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}