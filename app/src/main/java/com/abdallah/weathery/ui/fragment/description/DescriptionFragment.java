package com.abdallah.weathery.ui.fragment.description;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdallah.weathery.R;
import com.abdallah.weathery.databinding.DescriptionragmentFragmentBinding;
import com.abdallah.weathery.ui.activity.MainActivity;
import com.abdallah.weathery.utils.PrefManager;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.maps.model.LatLng;

import static com.abdallah.weathery.utils.Constant.CLEAR;
import static com.abdallah.weathery.utils.Constant.CLOUDS;
import static com.abdallah.weathery.utils.Constant.EXTREME;
import static com.abdallah.weathery.utils.Constant.RAIN;
import static com.abdallah.weathery.utils.Constant.SNOW;
import static com.abdallah.weathery.utils.Constant.STORM;
import static com.abdallah.weathery.utils.StaticMethods.loadingDialog;

public class DescriptionFragment extends Fragment {

    private DescriptionFragmentViewModel mViewModel;
    private DescriptionragmentFragmentBinding binding;
    private PrefManager prefManager;
    private AlertDialog loadingDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.descriptionragment_fragment, container, false);
        loadingDialog = loadingDialog(getActivity());
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
        DescriptionFragmentArgs args = DescriptionFragmentArgs.fromBundle(getArguments());

        LatLng latlng = args.getLatlng();


        fetchData(latlng);


    }

    private void fetchData(LatLng latLng) {
        loadingDialog.show();
        mViewModel.getweather(latLng)
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

        switch (main) {

            case CLEAR:
                binding.iconTemp.setImageResource(R.drawable.ic_clear);

                lottieView.setAnimation(R.raw.clear_day);
                break;
            case CLOUDS:
                binding.iconTemp.setImageResource(R.drawable.ic_clouds);

                lottieView.setAnimation(R.raw.cloudy_weather);

                break;
            case SNOW:
                binding.iconTemp.setImageResource(R.drawable.ic_snow);

                lottieView.setAnimation(R.raw.snow_weather);

                break;
            case STORM:
                binding.iconTemp.setImageResource(R.drawable.ic_storm);

                lottieView.setAnimation(R.raw.storm_weather);

                break;
            case EXTREME:
                binding.iconTemp.setImageResource(R.drawable.ic_extreme);

                lottieView.setAnimation(R.raw.broken_clouds);

                break;
            case RAIN:
                binding.iconTemp.setImageResource(R.drawable.ic_rain);
                lottieView.setAnimation(R.raw.rainy_weather);

                break;


            default:
                binding.iconTemp.setImageResource(R.drawable.ic_clear);
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