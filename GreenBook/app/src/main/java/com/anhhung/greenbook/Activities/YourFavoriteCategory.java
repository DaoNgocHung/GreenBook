package com.anhhung.greenbook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Adapters.CategoryAdapter;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class YourFavoriteCategory extends AppCompatActivity {

    private FirebaseFirestore db;
    private CategoriesModel category;

    private CategoryAdapter adapter;

    private RecyclerView rViewCategory;
    private Button btnCategoryFinish;

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
        db.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Query query = db.collection("DanhMucCollection");
                                FirestoreRecyclerOptions<CategoriesModel> options = new FirestoreRecyclerOptions.Builder<CategoriesModel>()
                                        .setQuery(query, CategoriesModel.class)
                                        .build();

                                adapter = new CategoryAdapter(options);

                                rViewCategory.setHasFixedSize(true);
                                rViewCategory.setLayoutManager(new LinearLayoutManager(YourFavoriteCategory.this));
                                rViewCategory.setAdapter(adapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void addEvents() {
        btnCategoryFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourFavoriteCategory.this,MainActivity.class);
                startActivity(intent);
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
    }
}
