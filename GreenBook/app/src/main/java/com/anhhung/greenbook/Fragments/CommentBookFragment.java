package com.anhhung.greenbook.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhhung.greenbook.Adapters.CommentBookAdapter;
import com.anhhung.greenbook.Models.CommentItem;
import com.anhhung.greenbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentBookFragment extends Fragment {

    private RecyclerView rViewComment;
    private CommentBookAdapter commentBookAdapter;
    List<CommentItem> commentItems;


    public CommentBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_book, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    private void fakeData() {
        for(int i = 0; i < 20; i++){
            commentItems.add(new CommentItem("Chopper","Trợ lý huấn luyện viên Tony dành nhiều lời khen cho tuyển Việt Nam sau khi họ đánh bại Indonesia với tỷ số 3-1 ngay ở Bali.","18:00 16/10/2019",R.drawable.chopper));
        }
    }

    private void addEvents(View view) {
        //fill list comment wth data
        //just for testing purpose i will fill the coomments list with random data
        //you may get your data from an api/ firebase or sqlite database ...
        fakeData();
        commentBookAdapter = new CommentBookAdapter(getActivity(),commentItems);
        rViewComment.setAdapter(commentBookAdapter);
        rViewComment.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addControls(View view) {
        rViewComment = view.findViewById(R.id.rViewComment);
        commentItems = new ArrayList<>();

    }

}
