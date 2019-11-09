package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anhhung.greenbook.Adapters.CategoriesListBookAdater;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.anhhung.greenbook.Fragments.InfoBookFragment;
import java.util.ArrayList;
import java.util.List;

public class MoreBookActivity extends AppCompatActivity {

    private Toolbar actionToolbarMoreBook;
    private Intent intent;
    private String danhMuc, idDM;
    private RecyclerView rViewMoreBook;
    private List<BooksModel> booksModels = new ArrayList<>();
    private String TAG = "MoreBookActivity - ERROR";
    FirebaseFirestore db;
    MyCallback myCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_book);
        addControl();
        addEvent();
    }

    private void addEvent() {
        //Button Back on toolbar
        actionToolbarMoreBook.setTitle(danhMuc);
        setSupportActionBar(actionToolbarMoreBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addControl() {
        db = FirebaseFirestore.getInstance();
        actionToolbarMoreBook = findViewById(R.id.actionToolbarMoreBook);
        intent = getIntent();
        danhMuc = intent.getStringExtra("danhMuc");
        idDM = intent.getStringExtra("idDanhMuc");

        rViewMoreBook = findViewById(R.id.rViewMoreBook);
        readData(new MyCallback() {
            @Override
            public void onCallback(List<BooksModel> booksModels) {
                createData(idDM,rViewMoreBook, booksModels);
            }
        });
    }

    private void createData(String tenDM, RecyclerView rViewMoreBook, List<BooksModel> booksModels) {
        CategoriesListBookAdater myAdapter = new CategoriesListBookAdater(this,booksModels);
        rViewMoreBook.setLayoutManager(new GridLayoutManager(this,3));
        rViewMoreBook.setAdapter(myAdapter);
    }

    public interface MyCallback {
        void onCallback(List<BooksModel> booksModels);
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentsInDanhMucCollectionInfoBookFrag(idDM);
    }
    private void getAllDocumentsInDanhMucCollectionInfoBookFrag(String tenDM){
        try{
            db.collection("DanhMucCollection").document(tenDM).collection("SachColection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    BooksModel booksModel = document.toObject(BooksModel.class);
                                    Log.d("TEST - InfoBookFragment", document.getId() + " => " + document.getData());
                                    booksModels.add(booksModel);
                                }
                                myCallback.onCallback(booksModels);
                            } else {
                                Log.d("InfoBookFragment", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }catch(Exception e){
            Log.d(TAG,e.toString());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
