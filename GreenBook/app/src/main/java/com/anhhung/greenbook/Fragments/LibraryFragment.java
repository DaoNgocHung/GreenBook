package com.anhhung.greenbook.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.anhhung.greenbook.Activities.MoreBookActivity;
import com.anhhung.greenbook.Adapters.BookLibraryAdapter;
import com.anhhung.greenbook.Adapters.BookListViewLibraryAdapter;
import com.anhhung.greenbook.Adapters.CategoriesListBookAdater;
import com.anhhung.greenbook.Models.BookLibraryModel;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private String danhMuc, idDM;
    private RecyclerView rViewLibrary;
    private ImageButton imgTypeView;
    private List<BookLibraryModel> bookLibraryModels = new ArrayList<>();
    private String TAG = "MoreBookActivity - ERROR";
    FirebaseFirestore db;
    private MyCallback myCallback;
    private SharedPreferences sharedPreferences;
    private String emailUser;
    private BookLibraryAdapter myAdapter;
    private BookListViewLibraryAdapter bookListViewLibraryAdapter;
    private boolean isGridView=true;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null).trim();
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        addControl(view);
        addEvent(view);
        return view;


    }

    private void addEvent(View view) {
        imgTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGridView == true){
                    isGridView = false;
                    imgTypeView.setImageResource(R.drawable.ic_list_library);
                    bookLibraryModels.clear();
                    readData(new MyCallback() {
                        @Override
                        public void onCallback(List<BookLibraryModel> bookLibraryModels) {
                            createDataListView(rViewLibrary, bookLibraryModels);
                        }
                    });
                }
                else{
                    isGridView = true;
                    bookLibraryModels.clear();
                    imgTypeView.setImageResource((R.drawable.ic_grid_library));
                    readData(new MyCallback() {
                        @Override
                        public void onCallback(List<BookLibraryModel> bookLibraryModels) {
                            createDataGridView(rViewLibrary, bookLibraryModels);
                        }
                    });
                }
            }
        });
    }

    private void addControl(View view) {
        db = FirebaseFirestore.getInstance();
        imgTypeView = view.findViewById(R.id.imgButtonTypeView);
        rViewLibrary = view.findViewById(R.id.rViewLibrary);
        bookLibraryModels.clear();
        if (isGridView ==true){
            readData(new MyCallback() {
                @Override
                public void onCallback(List<BookLibraryModel> bookLibraryModels) {
                    createDataGridView(rViewLibrary, bookLibraryModels);
                }
            });
        }
        else{
            readData(new MyCallback() {
                @Override
                public void onCallback(List<BookLibraryModel> bookLibraryModels) {
                    createDataListView(rViewLibrary, bookLibraryModels);
                }
            });
        }

    }
    private void createDataGridView(RecyclerView rViewLibBook, List<BookLibraryModel> bookLibraryModels) {
        myAdapter = new BookLibraryAdapter(this.getContext(),bookLibraryModels);
        rViewLibBook.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        rViewLibBook.setAdapter(myAdapter);
    }
    private void createDataListView(RecyclerView rViewLibBook, List<BookLibraryModel> bookLibraryModels){
        bookListViewLibraryAdapter = new BookListViewLibraryAdapter(this.getContext(),bookLibraryModels);
        rViewLibBook.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rViewLibBook.setAdapter(bookListViewLibraryAdapter);
    }
    public interface MyCallback {
        void onCallback(List<BookLibraryModel> bookLibraryModels);
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentsInDanhMucCollectionInfoBookFrag(idDM);
    }
    private void getAllDocumentsInDanhMucCollectionInfoBookFrag(String tenDM) {
        try {
            db.collection("UserModel").document(emailUser).collection("LibraryCollection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    BookLibraryModel bookLibraryModel = document.toObject(BookLibraryModel.class);
                                    Log.d("TEST - InfoBookFragment", document.getId() + " => " + document.getData());
                                    bookLibraryModels.add(bookLibraryModel);
                                }
                                myCallback.onCallback(bookLibraryModels);
                            } else {
                                Log.d("InfoBookFragment", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
