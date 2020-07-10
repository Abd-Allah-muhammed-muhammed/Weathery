package com.abdallah.weathery.ui.fragment.saved_weather;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import com.abdallah.weathery.database.AppDatabase;
import com.abdallah.weathery.model.weather_local_room.Weather;
import java.util.List;

import static com.abdallah.weathery.utils.Constant.DATABASE_NAME;

public class SavedWeatherViewModel extends ViewModel {

    @SuppressLint("CheckResult")
    public LiveData<List<Weather>> getMyWeather(Context context) {


        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration().
                        allowMainThreadQueries().addMigrations().build();

                return db.weatherDao().getAll();


    }


}