package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Random;

public class AddInfoUserActivity extends AppCompatActivity {

    private TextView txtAddInfoChangeBirth, txtAddInfoBirth, txtAddInfoName;
    private EditText  edtAddInfoPhone;
    private RadioButton rdAddInfoMale, rdAddInfoFemale;
    private Button btnAddInfoSave;
    private ImageView imgAddInfoAvatar;

    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private StorageReference avatarStorageReference;

    Intent intent;

    private Uri imgUri;
    private Uri urlImage;

    private UploadTask uploadTask;
    private boolean isError = false;
    private boolean isSelectImage = false;  // kiểm tra người dùng  có chọn hình chưa

    private final int CHOOSE_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_user);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAddInfoSave.setOnClickListener(new View.OnClickListener() {
            String phone, birthDay, userName, email, password;
            int rd;
            boolean gender;
            @Override
            public void onClick(View view) {
                if(checkInfo() == true){
                    // Input data
                    phone = edtAddInfoPhone.getText().toString().trim();
                    birthDay = txtAddInfoBirth.getText().toString();
                    userName = txtAddInfoName.getText().toString();
                    email = intent.getStringExtra("email");
                    password = intent.getStringExtra("password");
                    rd = new Random().nextInt(100000);
                    if(rdAddInfoMale.isChecked() == true) {
                        gender = true;
                    }
                    else {
                        gender = false;
                    }
                    avatarStorageReference = avatarStorageReference.child("image" + imgUri.getLastPathSegment());
                    //Upload image to Storage
                    uploadTask = avatarStorageReference.putFile(imgUri);
                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddInfoUserActivity.this,"Upload Fail",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddInfoUserActivity.this,"Completed",Toast.LENGTH_SHORT).show();
                            if(isSelectImage == true){
                                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }
                                        return avatarStorageReference.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri downloadUri = task.getResult();
                                            // Function call to Upload data
                                            //uploadData(name, phone, birthDay, gender, );
                                            UsersModel usersModel = new UsersModel(userName, gender, Timestamp.now(), downloadUri.toString(),
                                                    email,phone,0.0, 0);
                                            db.collection("UserModel").document(email).set(usersModel);
                                            Intent intentCategory = new Intent(AddInfoUserActivity.this,YourFavoriteCategory.class);
                                            intentCategory.putExtra("email", email);
                                            startActivity(intentCategory);
                                        } else {
                                            Toast.makeText(AddInfoUserActivity.this,"Get Image URL Fail.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            // ...
                        }
                    });
                }
            }
        });

        imgAddInfoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectImage = true;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,CHOOSE_IMAGE_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE_REQUEST && resultCode ==RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.with(this).load(imgUri).into(imgAddInfoAvatar);
        }

    }

    private void addControls() {

        intent = getIntent();

        txtAddInfoBirth = findViewById(R.id.txtAddInfoBirth);
        txtAddInfoChangeBirth = findViewById(R.id.txtAddInfoChangeBirth);
        txtAddInfoName = findViewById(R.id.txtAddInfoName);
        txtAddInfoName.setText(intent.getStringExtra("userName"));
        edtAddInfoPhone = findViewById(R.id.edtAddInfoPhone);
        rdAddInfoMale = findViewById(R.id.rdAddInfoMale);
        rdAddInfoMale.setChecked(true);
        rdAddInfoFemale = findViewById(R.id.rdAddInfoFemale);
        btnAddInfoSave = findViewById(R.id.btnAddInfoSave);
        imgAddInfoAvatar = findViewById(R.id.imgAddInfoAvatar);

        //firestore
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        avatarStorageReference = firebaseStorage.getReference().child("Avatar");
    }

    private boolean checkInfo() {
        if(edtAddInfoPhone.getText().equals("")) {
            Toast.makeText(AddInfoUserActivity.this, "Enter your phone", Toast.LENGTH_SHORT).show();
            edtAddInfoPhone.requestFocus();
            isError = true;
        }
        if(checkNumber(edtAddInfoPhone.getText().toString().trim())==false){
            Toast.makeText(AddInfoUserActivity.this,"Phone syntax error",Toast.LENGTH_SHORT).show();
            edtAddInfoPhone.requestFocus();
            isError = true;
        }

        if(isSelectImage == false){
            Toast.makeText(AddInfoUserActivity.this,"Select your image", Toast.LENGTH_SHORT).show();
            isError = true;
        }
        if(isError == false){
            return true;
        }
        return false;
    }

    private boolean checkNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
