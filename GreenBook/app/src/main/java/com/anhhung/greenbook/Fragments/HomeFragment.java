package com.anhhung.greenbook.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhhung.greenbook.Adapters.SliderAdvertiseAdapter;
import com.anhhung.greenbook.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private SliderView sliderViewFragment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Auto Advertisement

        sliderViewFragment = view.findViewById(R.id.imageSliderFragmentHome);
        final SliderAdvertiseAdapter adapter = new SliderAdvertiseAdapter(getActivity());
        adapter.setCount(3);
        sliderViewFragment.setSliderAdapter(adapter);

        addEvents();

        // Inflate the layout for this fragment
        return view;
    }

    private void addEvents() {
        //Auto Advertisement
        sliderViewFragment.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderViewFragment.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderViewFragment.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderViewFragment.setIndicatorSelectedColor(Color.WHITE);
        sliderViewFragment.setIndicatorUnselectedColor(Color.GRAY);
        sliderViewFragment.setScrollTimeInSec(5);   //set scroll delay in seconds :
        sliderViewFragment.startAutoCycle();

        sliderViewFragment.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderViewFragment.setCurrentPagePosition(position);
            }
        });
    }

}
