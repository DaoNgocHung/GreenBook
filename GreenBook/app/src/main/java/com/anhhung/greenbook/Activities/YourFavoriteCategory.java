package com.anhhung.greenbook.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Adapters.CategoryAdapter;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class YourFavoriteCategory extends AppCompatActivity {

    private FirebaseFirestore db;
    private CategoryAdapter adapter;

    private RecyclerView rViewCategory;
    private Button btnCategoryFinish;

    private Intent intent;
    private String email;

    private String TAG = "YourFavoriteCategory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_favorite_category);
        addControls();
        loadData();
        addEvents();
    }

    private void loadData() {
        Query query = db.collection("DMCollection");
        FirestoreRecyclerOptions<CategoriesModel> options = new FirestoreRecyclerOptions.Builder<CategoriesModel>()
                                        .setQuery(query, CategoriesModel.class)
                                        .build();
        adapter = new CategoryAdapter(options);

        rViewCategory.setHasFixedSize(true);
        rViewCategory.setLayoutManager(new LinearLayoutManager(YourFavoriteCategory.this));
        rViewCategory.setAdapter(adapter);

    }

    private void addEvents() {
        btnCategoryFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(YourFavoriteCategory.this,MainActivity.class);
                intentMain.putExtra("email",email);
                startActivity(intentMain);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void addControls() {
        db = FirebaseFirestore.getInstance();
        rViewCategory = findViewById(R.id.rViewCategory);
        btnCategoryFinish = findViewById(R.id.btnCategoryFinish);
        intent = getIntent();
        email = intent.getStringExtra("email");
    }
}
