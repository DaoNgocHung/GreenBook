package com.anhhung.greenbook.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.Models.SectionDataModel;
import com.anhhung.greenbook.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoBookFragment extends Fragment {

    TextView txtInfoNXB, txtInfoAuthor, txtInfoCate, txtInfoDate, txtInfoLanguage;
    RecyclerView rViewBookOffer;

    FirebaseFirestore db;
    List<BooksModel> booksModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String NXB = this.getArguments().getString("NXB", " ");
        String tacGia = this.getArguments().getString("tacGia", " ");
        String danhMuc = this.getArguments().getString("danhMuc", " ");
        String ngonNgu = this.getArguments().getString("ngonNgu", " ");

        View view = inflater.inflate(R.layout.fragment_info_book, null);

        addControls(view);

        txtInfoNXB.setText( NXB);
        txtInfoAuthor.setText(tacGia);
        txtInfoCate.setText(danhMuc);
        txtInfoLanguage.setText(ngonNgu);

        // Inflate the layout for this fragment
        return view;
    }


    private void addControls(View view) {
        txtInfoNXB = view.findViewById(R.id.txtInfoNXB);
        txtInfoAuthor = view.findViewById(R.id.txtInfoAuthor);
        txtInfoCate = view.findViewById(R.id.txtInfoCate);
        txtInfoDate = view.findViewById(R.id.txtInfoDate);
        txtInfoLanguage = view.findViewById(R.id.txtInfoLanguage);
        rViewBookOffer = view.findViewById(R.id.rViewBookOffer);

        db = FirebaseFirestore.getInstance();

    }
}
