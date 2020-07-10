package com.abdallah.weathery.ui.fragment.description;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.abdallah.weathery.database.AppDatabase;
import com.abdallah.weathery.model.weather_info.WeatherResponse;
import com.abdallah.weathery.model.weather_local_room.Weather;
import com.abdallah.weathery.network.BaseResponse;
import com.abdallah.weathery.network.RetrofitClass;
import com.abdallah.weathery.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static com.abdallah.weathery.utils.Constant.DATABASE_NAME;
import static com.abdallah.weathery.utils.Constant.MY_KEY_API;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class DescriptionFragmentViewModel extends ViewModel {

    MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

    public MutableLiveData<WeatherResponse> getweather(double lat, double lon) {


        RetrofitClass.getNetworkInstance().getWeather(lat, lon, MY_KEY_API)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(new SingleObserver<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(WeatherResponse response) {

                        formatTime(response);
                        data.postValue(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                        data.setValue(new WeatherResponse(e));
                    }
                });

        return data;
    }

    private void formatTime(WeatherResponse response) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);

        // format sunrise
        double sunrise = (Double) response.getSys().getSunrise();
        Date datesunrise = new Date((long) sunrise);
        String time_sunrise = sdf.format(datesunrise);
        response.getSys().setSunrise((String) time_sunrise);


        // format sunset
        double sunset = (Double) response.getSys().getSunset();
        Date datesunset = new Date((long) sunset);
        String time_sunset = sdf.format(datesunset);
        response.getSys().setSunset((String) time_sunset);


    }


    public void saveDataToRoom(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration().
                        allowMainThreadQueries().addMigrations().build();
        WeatherResponse response = data.getValue();



        Weather weather = new Weather();
        weather.setAreaName(response.getName());
        weather.setDescription(response.getWeather().get(0).getDescription());
        weather.setTemp(response.getMain().getTemp());
        weather.setPressure(response.getMain().getPressure());
        weather.setHumidity(response.getMain().getHumidity());
        weather.setSpeed((Double)response.getWind().getSpeed());
        weather.setSunrise((String) response.getSys().getSunrise());
        weather.setSunset((String) response.getSys().getSunset());
        weather.setTimezone( response.getTimezone());
        weather.setFeelsLike( response.getMain().getFeelsLike());
        weather.setMain( response.getWeather().get(0).getMain());

        db.weatherDao().insertAll(weather);


    }
}
