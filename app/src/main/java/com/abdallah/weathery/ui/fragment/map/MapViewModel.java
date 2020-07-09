package com.abdallah.weathery.ui.fragment.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.abdallah.weathery.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.abdallah.weathery.utils.Constant.MY_ZOOM;

public class MapViewModel extends ViewModel {

    private LatLng latLngReturn;
    private LatLng mylatlong;



    private void setMark( LatLng latLng1 , GoogleMap map) {
        map.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(latLng1)
                .draggable(true);
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        map.animateCamera(CameraUpdateFactory.zoomTo(MY_ZOOM));
        map.getUiSettings().setZoomControlsEnabled(true);

        latLngReturn = latLng1;
    }


    @SuppressLint("MissingPermission")
    public void getMyLocation(FusedLocationProviderClient fusedLocationClient, Activity activity, GoogleMap map){


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            //moving the map to location

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);


                            setMark(latLng,map);

                            map.setOnMapClickListener(latLng1 -> {

                                setMark(latLng1, map);

                            });

                            mylatlong = latLng;


                        }else {
                            Toast.makeText(activity, activity.getString(R.string.try_again), Toast.LENGTH_SHORT).show();


                        }
                    }

                });
    }



    public LatLng getLatLong(){


        return latLngReturn;



    }   public LatLng getMyLatLong(){

        return mylatlong;
    }
}
