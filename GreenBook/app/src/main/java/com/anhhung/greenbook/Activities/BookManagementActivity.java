package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Toolbar actionToolbarBookManagement;

    private FirebaseFirestore db;
    private ArrayList<String> listCategory = new ArrayList<>();

    // Cho người dùng chọn từ spinner danh mục

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);
        addControls();
        getBookData("dmHistoryPolitic");
        addEvents();
    }

    private void addControls() {
        rViewBookManage = findViewById(R.id.rViewBookManage);
        rViewBookManage.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rViewBookManage.setLayoutManager(layoutManager);
        spDMBookManage = findViewById(R.id.spDMBookManage);
        actionToolbarBookManagement = findViewById(R.id.actionToolbarBookManagement);
        actionToolbarBookManagement.setTitle("Book Management");
        db = FirebaseFirestore.getInstance();
        listCategory = addCategoryData(listCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spDMBookManage.setAdapter(adapter);
    }

    public ArrayList<String> addCategoryData(ArrayList<String> list){
        list.add("History - Politic");
        list.add("Life Skill");
        list.add("Economy Manage");
        list.add("Foreign Language");
        list.add("Myth - Fairy");
        return list;
    }

    private void addEvents() {
        spDMBookManage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(BookManagementActivity.this,spDMBookManage.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
//                String selected = adapterView.getItemAtPosition(i).toString();
//                if(selected.equals("History - Politic")) {
//                    getBookData("dmHistoryPolitic");
//                } else if(selected.equals("Life Skill")) {
//                    getBookData("dmLifeSkill");
//                } else if(selected.equals("Economy Manage")) {
//                    getBookData("dmEconomyManage");
//                } else if(selected.equals("Foreign Language")) {
//                    getBookData("dmForeignLanguage");
//                } else if(selected.equals("Myth - Fairy")) {
//                    getBookData("dmMythFairy");
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Back toolbar
        setSupportActionBar(actionToolbarBookManagement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
