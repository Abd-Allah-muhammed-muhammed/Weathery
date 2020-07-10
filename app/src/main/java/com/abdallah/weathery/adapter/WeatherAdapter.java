package com.abdallah.weathery.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abdallah.weathery.R;
import com.abdallah.weathery.databinding.ItemWeathersBinding;
import com.abdallah.weathery.model.weather_local_room.Weather;
import com.airbnb.lottie.LottieAnimationView;

import java.util.Date;
import java.util.List;

import static com.abdallah.weathery.utils.Constant.CLEAR;
import static com.abdallah.weathery.utils.Constant.CLOUDS;
import static com.abdallah.weathery.utils.Constant.EXTREME;
import static com.abdallah.weathery.utils.Constant.RAIN;
import static com.abdallah.weathery.utils.Constant.SNOW;
import static com.abdallah.weathery.utils.Constant.STORM;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyWeatherViewHolder> {


    List<Weather> weatherList ;

    @NonNull
    @Override
    public MyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWeathersBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_weathers,parent,false);

        return new MyWeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeatherViewHolder holder, int position) {

        holder.binding.setData(weatherList.get(position));

        checkForWeatherIcon(weatherList.get(position).getMain(),holder.binding.iconTemp);

    }

    @Override
    public int getItemCount() {

        if (weatherList != null) {
            return weatherList.size();
        } else {
            return 0;
        }

    }



    public static class MyWeatherViewHolder extends RecyclerView.ViewHolder {
        ItemWeathersBinding binding ;



        public MyWeatherViewHolder(@NonNull ItemWeathersBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    public  void  setMyWeather(List<Weather> myWeather){
        this.weatherList = myWeather;
        notifyDataSetChanged();

    }

    private void checkForWeatherIcon(String main, LottieAnimationView lottieView) {



        switch (main){

            case CLEAR :

                lottieView.setAnimation(R.raw.clear_day);
                break;
            case CLOUDS :

                lottieView.setAnimation(R.raw.cloudy_weather);

                break;
            case SNOW :
                lottieView.setAnimation(R.raw.snow_weather);

                break;
            case STORM :

                lottieView.setAnimation(R.raw.storm_weather);

                break;
            case EXTREME :

                lottieView.setAnimation(R.raw.broken_clouds);

                break;
            case RAIN :
                lottieView.setAnimation(R.raw.rainy_weather);

                break;


            default:
                lottieView.setAnimation(R.raw.unknown);


        }


        lottieView.playAnimation();

    }


}
