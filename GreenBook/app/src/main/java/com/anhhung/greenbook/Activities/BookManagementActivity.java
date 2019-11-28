package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anhhung.greenbook.Adapters.BookManageAdapter;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookManagementActivity extends AppCompatActivity {

    private BookManageAdapter bookManageAdapter;
    private RecyclerView rViewBookManage;
    private Spinner spDMBookManage;
    private LinearLayoutManager layoutManager;

    private FirebaseFirestore db;
    private List<String> listCategory = new ArrayList<>();

    // Cho người dùng chọn từ spinner danh mục

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);
        listCategory.add("History - Politic");
        listCategory.add("Life Skill");
        addControls();
        getBookData("dmEconomyManage");
        addEvents();
    }

    private void addControls() {
        rViewBookManage = findViewById(R.id.rViewBookManage);
        rViewBookManage.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rViewBookManage.setLayoutManager(layoutManager);
        spDMBookManage = findViewById(R.id.spDMBookManage);
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spDMBookManage.setAdapter(adapter);
    }

    private void addEvents() {
        spDMBookManage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spDMBookManage.getSelectedItem().toString()){
                    case "":
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Lấy sách từ danh mục
    private void getBookData(String idCategory) {
        Query query = db.collection("DanhMucCollection").document(idCategory)
                .collection("SachColection");
        FirestoreRecyclerOptions<BooksModel> options = new FirestoreRecyclerOptions.Builder<BooksModel>()
                .setQuery(query, BooksModel.class)
                .build();
        bookManageAdapter = new BookManageAdapter(options);

        Log.d("BookManagementActivity",bookManageAdapter.toString());

        rViewBookManage.setHasFixedSize(true);
        rViewBookManage.setLayoutManager(new LinearLayoutManager(BookManagementActivity.this));
        rViewBookManage.setAdapter(bookManageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookManageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookManageAdapter.stopListening();
    }
}
