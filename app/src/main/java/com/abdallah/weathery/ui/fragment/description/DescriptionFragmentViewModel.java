package com.abdallah.weathery.ui.fragment.description;

        import android.content.Context;

        import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModel;
        import androidx.room.Room;

        import com.abdallah.weathery.database.AppDatabase;
        import com.abdallah.weathery.model.remote.WeatherResponse;
        import com.abdallah.weathery.model.local.WeatherLocal;
        import com.abdallah.weathery.network.RetrofitClass;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;
        import java.util.TimeZone;

        import io.reactivex.SingleObserver;
        import io.reactivex.disposables.Disposable;

        import static com.abdallah.weathery.utils.Constant.DATABASE_NAME;
        import static com.abdallah.weathery.utils.Constant.MY_KEY_API;
        import static com.abdallah.weathery.utils.Constant.Temperature;
        import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
        import static io.reactivex.schedulers.Schedulers.io;

public class DescriptionFragmentViewModel extends ViewModel {

    MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

    public MutableLiveData<WeatherResponse> getweather(double lat, double lon) {


        RetrofitClass.getNetworkInstance().getWeather(lat, lon, MY_KEY_API, Temperature)
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


        Double timeZone= (Double) response.getTimezone();
        int intTimeZone = (int)Math.round(timeZone);
        Date datTimeZone = new Date(intTimeZone);
        SimpleDateFormat sdfTimeZon = new SimpleDateFormat("yyyy-MM-dd' / 'HH:mm:ss' / ' Z", Locale.getDefault());
        String format = sdfTimeZon.format(datTimeZone);
        response.setTimezone(format);
    }


    public void saveDataToRoom(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration().
                        allowMainThreadQueries().addMigrations().build();
        WeatherResponse response = data.getValue();

        WeatherLocal weatherLocal = new WeatherLocal();
        weatherLocal.setAreaName(response.getName());
        weatherLocal.setDescription(response.getWeather().get(0).getDescription());
        weatherLocal.setTemp(response.getMain().getTemp());
        weatherLocal.setPressure(response.getMain().getPressure());
        weatherLocal.setHumidity(response.getMain().getHumidity());
        weatherLocal.setSpeed((Double)response.getWind().getSpeed());
        weatherLocal.setSunrise((String) response.getSys().getSunrise());
        weatherLocal.setSunset((String) response.getSys().getSunset());
        weatherLocal.setTimezone( response.getTimezone().toString());
        weatherLocal.setFeelsLike( response.getMain().getFeelsLike());
        weatherLocal.setMain( response.getWeather().get(0).getMain());

        db.weatherDao().insertAll(weatherLocal);


    }
}
