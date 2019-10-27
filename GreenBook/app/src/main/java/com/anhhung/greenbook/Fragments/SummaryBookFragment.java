package com.anhhung.greenbook.Fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryBookFragment extends Fragment {
    TextView txtSumaryBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String noiDung = this.getArguments().getString("gioiThieu", " ");
        View view = inflater.inflate(R.layout.fragment_summary_book,null);
        txtSumaryBook = view.findViewById(R.id.txtSumaryBook);
        txtSumaryBook.setMovementMethod(new ScrollingMovementMethod());
        txtSumaryBook.setText(noiDung);
        return view;
    }

    private void addControls() {







    }


}
