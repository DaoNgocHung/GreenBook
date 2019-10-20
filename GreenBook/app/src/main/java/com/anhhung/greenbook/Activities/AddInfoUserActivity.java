package com.anhhung.greenbook.Activities;

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

import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddInfoUserActivity extends AppCompatActivity {

    private TextView txtAddInfoChangeBirth, txtAddInfoBirth;
    private EditText edtAddInfoName, edtAddInfoPhone;
    private RadioButton rdAddInfoMale, rdAddInfoFemale;
    private Button btnAddInfoSave;
    private ImageView imgAddInfoAvatar;

    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private StorageReference avatarStorageReference;

    private Uri imgUri;


    private boolean isError = false;

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
            @Override
            public void onClick(View view) {
                if(checkInfo() == true){
                    // Input data
                    String name = edtAddInfoName.getText().toString().trim();
                    String phone = edtAddInfoPhone.getText().toString().trim();
                    String birthDay = txtAddInfoBirth.getText().toString().trim();
                    boolean gender;
                    if(rdAddInfoMale.isChecked() == true) gender = true;
                    else gender = false;
                    StorageReference imageName = avatarStorageReference.child("image" + imgUri.getLastPathSegment());
                    //Upload image to Storage
                    imageName.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddInfoUserActivity.this,"Completed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Function call to Upload data
                    //uploadData(name, phone, birthDay, gender, );
                }
            }
        });

        imgAddInfoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        txtAddInfoBirth = findViewById(R.id.txtAddInfoBirth);
        txtAddInfoChangeBirth = findViewById(R.id.txtAddInfoChangeBirth);
        edtAddInfoName = findViewById(R.id.edtAddInfoName);
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
        if(edtAddInfoName.getText().equals("")) {
            Toast.makeText(AddInfoUserActivity.this,"Enter your name", Toast.LENGTH_SHORT).show();
            edtAddInfoName.requestFocus();
            isError = true;
        }
        if(edtAddInfoPhone.getText().equals("")) {
            Toast.makeText(AddInfoUserActivity.this, "Enter your phone", Toast.LENGTH_SHORT).show();
            edtAddInfoPhone.requestFocus();
            isError = true;
        }
        if(checkNumber(edtAddInfoPhone.getText().toString().trim())==false){
            Toast.makeText(AddInfoUserActivity.this,"Phone syntax error",Toast.LENGTH_SHORT).show();
            isError = true;
        }

        if(imgAddInfoAvatar.getResources() == null){
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
