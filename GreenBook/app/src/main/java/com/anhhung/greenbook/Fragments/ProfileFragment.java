package com.anhhung.greenbook.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anhhung.greenbook.Activities.AddInfoUserActivity;
import com.anhhung.greenbook.Activities.WelcomeActivity;
import com.anhhung.greenbook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.jgabrielfreitas.core.BlurImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    BlurImageView blurImageView;
    ImageButton imgbtnLogoutProfile;

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
        imgbtnLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }
        });
    }

    private void addControls(View view) {
        blurImageView = view.findViewById(R.id.blurImageView);
        blurImageView.setBlur(5/2);
        imgbtnLogoutProfile = view.findViewById(R.id.imgbtnLogoutProfile);
    }

}
