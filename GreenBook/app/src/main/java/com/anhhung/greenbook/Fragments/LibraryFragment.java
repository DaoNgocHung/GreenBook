package com.anhhung.greenbook.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhhung.greenbook.Activities.MoreBookActivity;
import com.anhhung.greenbook.Adapters.BookLibraryAdapter;
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
    private List<BookLibraryModel> bookLibraryModels = new ArrayList<>();
    private String TAG = "MoreBookActivity - ERROR";
    FirebaseFirestore db;
    private MyCallback myCallback;
    private SharedPreferences sharedPreferences;
    private String emailUser;
    private BookLibraryAdapter myAdapter;

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

    }

    private void addControl(View view) {
        db = FirebaseFirestore.getInstance();

        rViewLibrary = view.findViewById(R.id.rViewLibrary);
        bookLibraryModels.clear();
        readData(new MyCallback() {
            @Override
            public void onCallback(List<BookLibraryModel> bookLibraryModels) {
                createData(rViewLibrary, bookLibraryModels);
            }
        });
    }
    private void createData(RecyclerView rViewMoreBook, List<BookLibraryModel> bookLibraryModels) {
        myAdapter = new BookLibraryAdapter(this.getContext(),bookLibraryModels);
        rViewMoreBook.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        rViewMoreBook.setAdapter(myAdapter);
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
