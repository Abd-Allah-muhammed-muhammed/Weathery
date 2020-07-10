package com.abdallah.weathery.ui.fragment.description;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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

public class DescriptionFragment extends Fragment {

    private DescriptionFragmentViewModel mViewModel;
    private DescriptionragmentFragmentBinding binding;

    public static DescriptionFragment newInstance( ) {
        return new DescriptionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater,R.layout.descriptionragment_fragment, container, false);

         return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DescriptionFragmentViewModel.class);
        getDataBundle();

    }

    private void getDataBundle( ) {
        Bundle bundle = getArguments();

        if (bundle != null) {
            double latitude = bundle.getDouble("latitude");
            double longitude = bundle.getDouble("longitude");

            fetchData(latitude,longitude);
        }else {

            // if the data bundle is null or empty
            Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();

        }
    }

    private void fetchData(double latitude, double longitude) {

        mViewModel.getTerms(latitude,longitude)
                .observe(getViewLifecycleOwner(),response ->{


                    if (response.getThrowable()==null){

                        if (response.getCod()==200){


                            binding.setData(response);

                        }else {

                            Toast.makeText(getContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                        }



                    }else {


                        Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    }

                } );
    }

}