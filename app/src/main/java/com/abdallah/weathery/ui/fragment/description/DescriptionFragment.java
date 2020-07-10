package com.abdallah.weathery.ui.fragment.description;

import androidx.annotation.MainThread;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdallah.weathery.R;
import com.abdallah.weathery.databinding.DescriptionragmentFragmentBinding;
import com.abdallah.weathery.model.weather_info.WeatherResponse;
import com.abdallah.weathery.ui.activity.MainActivity;
import com.abdallah.weathery.utils.PrefManager;
import com.airbnb.lottie.LottieAnimationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.abdallah.weathery.utils.Constant.CLEAR;
import static com.abdallah.weathery.utils.Constant.CLOUDS;
import static com.abdallah.weathery.utils.Constant.EXTREME;
import static com.abdallah.weathery.utils.Constant.RAIN;
import static com.abdallah.weathery.utils.Constant.SNOW;
import static com.abdallah.weathery.utils.Constant.STORM;

public class DescriptionFragment extends Fragment {

    private DescriptionFragmentViewModel mViewModel;
    private DescriptionragmentFragmentBinding binding;
    private PrefManager prefManager ;
    private AlertDialog loadingDialog;
    private MainActivity mainActivity ;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.descriptionragment_fragment, container, false);
        mainActivity =(MainActivity) getActivity();
        loadingDialog = mainActivity.loadingDialog();
        loadingDialog.cancel();
        prefManager = new PrefManager(getContext());
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DescriptionFragmentViewModel.class);
        getDataBundle();
        handelClicks();

    }

    private void getDataBundle( ) {
        Bundle bundle = getArguments();

        if (bundle != null) {
            double latitude = bundle.getDouble("latitude");
            double longitude = bundle.getDouble("longitude");

            fetchData(latitude, longitude);
        } else {

            // if the data bundle is null or empty
            Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();

        }
    }

    private void fetchData(double latitude, double longitude) {
        loadingDialog.show();
        mViewModel.getweather(latitude, longitude)
                .observe(getViewLifecycleOwner(), response -> {


                    loadingDialog.cancel();

                    // show the save view
                    binding.liSave.setVisibility(View.VISIBLE);

                    if (response.getThrowable() == null) {

                        if (response.getCod() == 200) {

                            checkForWeatherIcon(response.getWeather().get(0).getMain());
                            binding.setData(response);


                        } else {

                            Toast.makeText(getContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    } else {


                        Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void checkForWeatherIcon(String main) {
        LottieAnimationView lottieView = binding.weatherAnimationView;

        switch (main){

            case CLEAR : binding.iconTemp.setImageResource(R.drawable.ic_clear);

            lottieView.setAnimation(R.raw.clear_day);
            break;
                  case CLOUDS : binding.iconTemp.setImageResource(R.drawable.ic_clouds);

                      lottieView.setAnimation(R.raw.cloudy_weather);

                      break;
                  case SNOW : binding.iconTemp.setImageResource(R.drawable.ic_snow);

                      lottieView.setAnimation(R.raw.snow_weather);

                      break;
                  case STORM : binding.iconTemp.setImageResource(R.drawable.ic_storm);

                      lottieView.setAnimation(R.raw.storm_weather);

                      break;
                  case EXTREME : binding.iconTemp.setImageResource(R.drawable.ic_extreme);

                      lottieView.setAnimation(R.raw.broken_clouds);

                      break;
                  case RAIN : binding.iconTemp.setImageResource(R.drawable.ic_rain);
                      lottieView.setAnimation(R.raw.rainy_weather);

                      break;


            default:   binding.iconTemp.setImageResource(R.drawable.ic_clear);
                lottieView.setAnimation(R.raw.unknown);


        }


        lottieView.playAnimation();

    }

    private void handelClicks( ) {

        binding.liSave.setOnClickListener(view -> {
            mViewModel.saveDataToRoom(getContext());
            Toast.makeText(getContext(), getString(R.string.saved_saccessful), Toast.LENGTH_SHORT).show();
            binding.liSave.setVisibility(View.GONE);
            prefManager.setFirstTimeLaunch(false);
        });

    }


}