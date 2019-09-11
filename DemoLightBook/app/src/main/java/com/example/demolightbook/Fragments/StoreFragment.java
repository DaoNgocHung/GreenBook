package com.example.demolightbook.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.demolightbook.Adapters.SliderAdapter;
import com.example.demolightbook.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    private SliderView sliderViewFragment;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);

        //Auto Advertisement

        sliderViewFragment = view.findViewById(R.id.sliderViewFragment);
        final SliderAdapter adapter = new SliderAdapter(getActivity());
        adapter.setCount(3);
        sliderViewFragment.setSliderAdapter(adapter);

        addEvents();

        // Inflate the layout for this fragment
        return view;
    }


    private void addEvents() {
        setSliderView();

    }

    private void setSliderView() {

        //Auto Advertisement
        sliderViewFragment.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderViewFragment.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderViewFragment.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderViewFragment.setIndicatorSelectedColor(Color.WHITE);
        sliderViewFragment.setIndicatorUnselectedColor(Color.GRAY);
        sliderViewFragment.setScrollTimeInSec(4);   //set scroll delay in seconds :
        sliderViewFragment.startAutoCycle();

        sliderViewFragment.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderViewFragment.setCurrentPagePosition(position);
            }
        });
    }
}
