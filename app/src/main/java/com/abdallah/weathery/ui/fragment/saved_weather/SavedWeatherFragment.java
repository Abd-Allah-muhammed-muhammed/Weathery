package com.abdallah.weathery.ui.fragment.saved_weather;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdallah.weathery.R;
import com.abdallah.weathery.adapter.WeatherAdapter;
import com.abdallah.weathery.databinding.SavedWeatherFragmentBinding;

public class SavedWeatherFragment extends Fragment {

    private SavedWeatherViewModel mViewModel;
    private SavedWeatherFragmentBinding binding ;
    private WeatherAdapter weatherAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.saved_weather_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =  new ViewModelProvider(this).get(SavedWeatherViewModel.class);

        setupRecycler();
        fetchData();

    }

    private void setupRecycler( ) {

         weatherAdapter = new WeatherAdapter();
        binding.rvWeathers.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWeathers.setAdapter(weatherAdapter);

    }

    private void fetchData( ) {

        mViewModel.getMyWeather(getContext()).observe(getViewLifecycleOwner(), weathers -> {


            if (!weathers.isEmpty()){


                weatherAdapter.setMyWeather(weathers);
            }else {



                binding.liNodata.setVisibility(View.VISIBLE);
            }



        });
    }

}