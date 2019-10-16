package com.anhhung.greenbook.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Adapters.MyDataBookAdapter;
import com.anhhung.greenbook.Adapters.SliderAdvertiseAdapter;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.SectionDataModel;
import com.anhhung.greenbook.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ArrayList<SectionDataModel> allSampleData;
    private SliderView sliderViewFragment;
    Button btnTest;

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
        //addControls
        addControls(view);
        //addEvents

        addEvents();

        // Inflate the layout for this fragment
        return view;
    }

    private void addControls(View view) {
        allSampleData = new ArrayList<SectionDataModel>();
        createDummyData();
        RecyclerView myRecyclerView = view.findViewById(R.id.home_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        MyDataBookAdapter adapter = new MyDataBookAdapter(getActivity(), allSampleData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(adapter);
    }
    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<BooksModel> bookItem = new ArrayList<BooksModel>();
            for (int j = 0; j <= 5; j++) {
                bookItem.add(new BooksModel("Item " + j));
            }

            dm.setAllItemsInSection(bookItem);

            allSampleData.add(dm);

        }
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
