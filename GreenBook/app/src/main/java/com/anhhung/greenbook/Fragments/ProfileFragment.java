package com.anhhung.greenbook.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anhhung.greenbook.Activities.AddInfoUserActivity;
import com.anhhung.greenbook.Activities.BillActivity;
import com.anhhung.greenbook.Activities.InfoUserActivity;
import com.anhhung.greenbook.Activities.RechargeActivity;
import com.anhhung.greenbook.Activities.WelcomeActivity;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jgabrielfreitas.core.BlurImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private BlurImageView blurImageView;
    private ImageButton imgbtnLogoutProfile, imgbtnBillProfile, imgbtnEditProfile, imgbtnWalletProfile;
    private CircleImageView imgAvatarProfile;
    private TextView txtProfileCoin, txtProfileNumberBook, txtNameProfile;
    private SharedPreferences sharedPreferences;

    private FirebaseFirestore db;
    private UsersModel usersModel;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        addControls(view);
        getData(view);
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
        imgbtnBillProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BillActivity.class));
            }
        });
        imgbtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoUserActivity.class));
            }
        });
        imgbtnWalletProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RechargeActivity.class));
            }
        });
    }

    private void addControls(View view) {
        blurImageView = view.findViewById(R.id.blurImageView);
        blurImageView.setBlur(10);
        imgbtnLogoutProfile = view.findViewById(R.id.imgbtnLogoutProfile);
        imgbtnBillProfile = view.findViewById(R.id.imgbtnBillProfile);
        imgbtnEditProfile = view.findViewById(R.id.imgbtnEditProfile);
        imgbtnWalletProfile = view.findViewById(R.id.imgbtnWalletProfile);
        txtProfileCoin = view.findViewById(R.id.txtProfileCoin);
        txtProfileNumberBook = view.findViewById(R.id.txtProfileNumberBook);
        txtNameProfile = view.findViewById(R.id.txtNameProfile);
        imgAvatarProfile = view.findViewById(R.id.imgAvatarProfile);
        db = FirebaseFirestore.getInstance();
    }

    private void getData(View view) {
        db.collection("UserModel")
                .whereEqualTo("email", sharedPreferences.getString("emailUser", null))
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usersModel = document.toObject(UsersModel.class);
                                txtProfileCoin.setText(usersModel.getTien().toString());
                                txtProfileNumberBook.setText(usersModel.getSoSachDaMua()+"");
                                txtNameProfile.setText(usersModel.getHoTen());
                                Glide.with(getActivity()).load(usersModel.getHinhDaiDien()).into(imgAvatarProfile);
                                Glide.with(getActivity())
                                        .load("https://firebasestorage.googleapis.com/v0/b/greenbookfirestore.appspot.com/o/AnhBiaUser%2FanhBiaUser.jpg?alt=media&token=73224c41-cb2e-4e38-8d06-bd95d74f6aed")
                                        .into(blurImageView);
                            }
                        }
                    }
                });
    }

}
