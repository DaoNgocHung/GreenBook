package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.anhhung.greenbook.Adapters.UserFindingAdapter;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserManagementActivity extends AppCompatActivity {

    private TextView txtUserManageNotice;
    private RecyclerView rViewUserManage;
    private Toolbar actionToolbarUserManagement;
    private MyCallback myCallback;
    private UserFindingAdapter userFindingAdapter;

    private List<UsersModel> usersModels = new ArrayList<>();
    private List<UsersModel> usersModelsFindBook = new ArrayList<>();
    FirebaseFirestore db;

    private boolean loadUserSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtUserManageNotice = findViewById(R.id.txtUserManageNotice);
        rViewUserManage = findViewById(R.id.rViewUserManage);
        actionToolbarUserManagement = findViewById(R.id.actionToolbarUserManagement);
        db = FirebaseFirestore.getInstance();

        readData(new MyCallback() {
            @Override
            public void onCallback(final List<UsersModel> aUsersModels) {
                usersModels = aUsersModels;
                createDataListView(rViewUserManage,usersModels);
                usersModelsFindBook.addAll(usersModels);
                loadUserSuccess = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_user_menu,menu);
        MenuItem item = menu.findItem(R.id.mnuSeachUser);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                rViewUserManage.removeAllViews();
                rViewUserManage.setVisibility(View.INVISIBLE);
                txtUserManageNotice.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(loadUserSuccess == true){
                    newText = newText.toLowerCase(Locale.getDefault());
                    usersModels.clear();
                    if (newText.length() == 0) {
                    } else {
                        for (UsersModel wp : usersModelsFindBook ) {
                            if (wp.getEmail().toLowerCase(Locale.getDefault()).contains(newText)) {
                                usersModels.add(wp);
                            }
                        }
                    }
                    userFindingAdapter.notifyDataSetChanged();
                    rViewUserManage.setVisibility(View.VISIBLE);
                    txtUserManageNotice.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvents() {
        //Back toolbar
        setSupportActionBar(actionToolbarUserManagement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void getAllDocumentInUserModel() {
        try{
            db.collection("UserModel")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()) {
                                    UsersModel usersModel;
                                    usersModel  = document.toObject(UsersModel.class);
                                    usersModels.add(usersModel);
                                    myCallback.onCallback(usersModels);
                                }
                            }
                        }
                    });
        } catch (Exception e){
            Log.d("UserManagementActivity", e.toString());
        }
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentInUserModel();
    }

    private void createDataListView(RecyclerView recyclerView, List<UsersModel> usersModels){
        userFindingAdapter = new UserFindingAdapter(this,usersModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userFindingAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Find Book
    public interface MyCallback {
        void onCallback(List<UsersModel> usersModels);
    }


}
