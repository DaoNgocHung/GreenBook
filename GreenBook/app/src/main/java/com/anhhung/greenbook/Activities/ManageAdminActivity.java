package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageAdminActivity extends AppCompatActivity {

    private TextView txtNameAdmin, txtPermissionAdmin;
    private Button btnAddBook, btnBookManagement, btnManageCollectionMap, btnUserManagement;
    private CircleImageView imgAvatarAdmin;
    private SharedPreferences sharedPreferences;
    private String emailUser;
    private FirebaseFirestore db;
    private UsersModel usersModel;
    private Toolbar actionToolbarAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);
        sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null);
        addControls();
        loadDataUser(emailUser);
        addEvents();
    }

    private void loadDataUser(String email) {
        db.collection("UserModel").document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        usersModel  = documentSnapshot.toObject(UsersModel.class);
                        txtNameAdmin.append(usersModel.getHoTen());
                        txtPermissionAdmin.append(usersModel.getQuyen());
                        Glide.with(ManageAdminActivity.this)
                                .load(usersModel.getHinhDaiDien())
                                .into(imgAvatarAdmin);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageAdminActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addControls() {
        txtNameAdmin = findViewById(R.id.txtNameAdmin);
        txtPermissionAdmin = findViewById(R.id.txtPermissionAdmin);
        btnBookManagement = findViewById(R.id.btnBookManagement);
        btnUserManagement = findViewById(R.id.btnUserManagement);
        imgAvatarAdmin = findViewById(R.id.imgAvatarAdmin);
        btnManageCollectionMap = findViewById(R.id.btnManageCollectionMap);
        actionToolbarAdmin = findViewById(R.id.actionToolbarAdmin);
        actionToolbarAdmin.setTitle("Admin Board");
        db = FirebaseFirestore.getInstance();
    }

    private void addEvents() {
        btnBookManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageAdminActivity.this,BookManagementActivity.class));
            }
        });
        btnManageCollectionMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageAdminActivity.this,CollectionMapActivity.class);
                startActivity(intent);
            }
        });
        btnUserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageAdminActivity.this,UserManagementActivity.class));
            }
        });
        //Back toolbar
        setSupportActionBar(actionToolbarAdmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
