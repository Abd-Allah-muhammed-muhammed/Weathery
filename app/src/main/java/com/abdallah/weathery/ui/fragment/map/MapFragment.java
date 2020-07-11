package com.abdallah.weathery.ui.fragment.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdallah.weathery.R;
import com.abdallah.weathery.databinding.FragmentMapBinding;
import com.abdallah.weathery.utils.PrefManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import static com.abdallah.weathery.utils.StaticMethods.isNetworkAvailable;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private MapViewModel viewModel;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 132;
    private boolean locationPermissionGranted;
    private GoogleMap map;
    private PrefManager prefManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        prefManager = new PrefManager(getActivity());
        // check Permission
        getLocationPermission();

        handelClicks();
        return binding.getRoot();
    }



    @SuppressLint("MissingPermission")
    private void handelClicks( ) {
        binding.btnMyLocation.setOnClickListener(view -> {
            if (locationPermissionGranted) {
                viewModel.getMyLocation(getActivity(), map,binding.btnAnotherLocation);
                goToResult(viewModel.getMyLatLong(), view);
            } else {
                getLocationPermission();
            }

        });

        binding.btnAnotherLocation.setOnClickListener(view -> {
            if (locationPermissionGranted) {
                goToResult(viewModel.getLatLong(), view);
            }
        });

        binding.liSave.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_savedWeatherFragment);

        });

        binding.liNoIntenet.setOnClickListener(view -> {
            binding.liNoIntenet.setVisibility(View.GONE);
        });


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


    }


    private void getLocationPermission( ) {

        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            binding.btnMyLocation.setVisibility(View.VISIBLE);

        } else {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                viewModel.getMyLocation(getActivity(), map,binding.btnAnotherLocation);

                binding.btnMyLocation.setVisibility(View.VISIBLE);
                binding.btnMyLocation.setText(R.string.my_location);


            } else {
                locationPermissionGranted = false;
                binding.btnMyLocation.setVisibility(View.VISIBLE);
                binding.btnMyLocation.setText(R.string.allow_location);

            }
        }


    }


    private void goToResult(LatLng latLng, View view) {

        if (isNetworkAvailable(getActivity())) {

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latLng.latitude);
            bundle.putDouble("longitude", latLng.longitude);
            Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_descriptionragment, bundle);
        } else {

            binding.liNoIntenet.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onStart( ) {
        super.onStart();

        if (!prefManager.isFirstTimeLaunch()) {

            binding.liSave.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (locationPermissionGranted) {
            viewModel.getMyLocation(getActivity(), map, binding.btnAnotherLocation);
        } else {
            getLocationPermission();
        }
    }
}

