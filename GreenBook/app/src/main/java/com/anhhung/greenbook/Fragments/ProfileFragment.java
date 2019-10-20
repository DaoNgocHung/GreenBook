package com.anhhung.greenbook.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhhung.greenbook.Activities.AddInfoUserActivity;
import com.anhhung.greenbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView txtProfileTest;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_profile, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        txtProfileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddInfoUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls(View view) {
        txtProfileTest = view.findViewById(R.id.txtProfileTest);
    }

}
