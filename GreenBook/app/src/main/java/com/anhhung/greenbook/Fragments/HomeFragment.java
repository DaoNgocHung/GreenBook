package com.anhhung.greenbook.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.model.DocumentData;
import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Adapters.MyDataBookAdapter;
import com.anhhung.greenbook.Adapters.SliderAdvertiseAdapter;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.SectionDataModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "WARN";
    ArrayList<SectionDataModel> allSampleData;
    private SliderView sliderViewFragment;
    Button btnTest;
    private FirebaseFirestore db;
    RecyclerView myRecyclerView;
    List<BooksModel> booksModels = new ArrayList<>();

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
        db = FirebaseFirestore.getInstance();
        allSampleData = new ArrayList<>();
        getAllDocumentsInDanhMucCollection();
        myRecyclerView = view.findViewById(R.id.home_recycler_view);
        myRecyclerView.setHasFixedSize(true);


    }
    public void createDummyData(List<BooksModel> booksModels, RecyclerView myRecyclerView) {
        for (int i = 0; i < 1; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(booksModels.get(i).getDanhMuc());
            ArrayList<String> imgList = new ArrayList<>();
            ArrayList<BooksModel> bookItem = new ArrayList<BooksModel>();
            for (int j = 0; j< booksModels.size(); j++) {
                bookItem.add(booksModels.get(j));
                imgList.add(booksModels.get(j).getBiaSach());
            }

            dm.setAllItemsInSection(bookItem);
            dm.setImgList(imgList);
            allSampleData.add(dm);
            MyDataBookAdapter adapter = new MyDataBookAdapter(getActivity(), allSampleData);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            myRecyclerView.setAdapter(adapter);
        }
    }
    private void getAllDocumentsInDanhMucCollection(){
        try{
            db.collection("DanhMucCollection").document("dmForeignLanguage").collection("SachColection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    BooksModel booksModel;
                                    booksModel = document.toObject(BooksModel.class);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    booksModels.add(booksModel);
                                }
                                createDummyData(booksModels, myRecyclerView);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }catch (Exception e){
            Log.d("ERR", e.toString());
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
