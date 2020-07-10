package com.abdallah.weathery.ui.fragment.description;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdallah.weathery.model.weather_info.WeatherResponse;
import com.abdallah.weathery.network.BaseResponse;
import com.abdallah.weathery.network.RetrofitClass;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static com.abdallah.weathery.utils.Constant.MY_KEY_API;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class DescriptionFragmentViewModel extends ViewModel {


    public MutableLiveData<WeatherResponse> getTerms (double lat , double lon){

        MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

        RetrofitClass.getNetworkInstance().getWeather(lat,lon,MY_KEY_API)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(new SingleObserver<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(WeatherResponse response) {

                        data.postValue(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                      data.setValue(new   WeatherResponse(e));
                    }
                });

        return data;
    }


}
