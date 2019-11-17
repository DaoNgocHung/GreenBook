package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.anhhung.greenbook.Adapters.BillAdapter;
import com.anhhung.greenbook.Models.BillDetailModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BillActivity extends AppCompatActivity {

    private Toolbar actionToolbarBill;
    private RecyclerView rViewBill;
    private SharedPreferences sharedPreferences;
    private String emailUser;
    private String TAG = "BillActivity - Error";

    private FirebaseFirestore db;
    private BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null);
        addControls();
        addEvents();
        getBillData(emailUser);
    }

    private void addEvents() {
        //Back toolbar
        setSupportActionBar(actionToolbarBill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //
    }

    private void addControls() {
        actionToolbarBill  = findViewById(R.id.actionToolbarBill);
        actionToolbarBill.setTitle("Your Bill");
        rViewBill = findViewById(R.id.rViewBill);
        db = FirebaseFirestore.getInstance();
    }

    private void getBillData(String email){
        Query query = db.collection("UserModel").document(emailUser).collection("BillCollection");
        FirestoreRecyclerOptions<BillDetailModel> options = new FirestoreRecyclerOptions.Builder<BillDetailModel>()
                .setQuery(query, BillDetailModel.class)
                .build();
        adapter = new BillAdapter(options);

        Log.d(TAG,adapter.toString());

        rViewBill.setHasFixedSize(true);
        rViewBill.setLayoutManager(new LinearLayoutManager(BillActivity.this));
        rViewBill.setAdapter(adapter);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
