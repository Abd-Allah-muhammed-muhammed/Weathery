package com.abdallah.weathery.ui.fragment.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdallah.weathery.R;
import com.abdallah.weathery.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.abdallah.weathery.utils.Constant.MY_ZOOM;
import static com.abdallah.weathery.utils.StaticMethods.isNetworkAvailable;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapViewModel viewModel;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 132;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap map;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
          map = googleMap;
            if (locationPermissionGranted) {
                viewModel.getMyLocation(fusedLocationClient, getActivity(), map);
            }else {
                getLocationPermission();
            }

        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocationPermission();
        handelClicks();
        return binding.getRoot();
    }

    @SuppressLint("MissingPermission")
    private void handelClicks( ) {
        binding.btnMyLocation.setOnClickListener(view -> {

            viewModel.getMyLocation(fusedLocationClient, getActivity(), map);
            goToResult(viewModel.getMyLatLong(),view);

        });

        binding.btnAnotherLocation.setOnClickListener(view -> {

            goToResult(viewModel.getLatLong(),view);


        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;

          if (requestCode==PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
              // If request is cancelled, the result arrays are empty.
              if (grantResults.length > 0
                      && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  locationPermissionGranted = true;
              }
          }


            }

    private void goToResult(LatLng latLng , View view){

        if (isNetworkAvailable(getActivity())){

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude",latLng.latitude);
            bundle.putDouble("longitude",latLng.longitude);
            Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_descriptionragment, bundle);
        }else {

            Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }



    }


}
//

