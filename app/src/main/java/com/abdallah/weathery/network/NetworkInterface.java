package com.abdallah.weathery.network;

import com.abdallah.weathery.model.weather_info.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {

@GET("weather")
Single<WeatherResponse> getWeather(@Query("lat")double lat, @Query("lon")double lon, @Query("appid") String appid);

}
