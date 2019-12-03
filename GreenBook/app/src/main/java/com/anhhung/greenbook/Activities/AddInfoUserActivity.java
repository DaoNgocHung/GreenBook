package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
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

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddInfoUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

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
    private String urlImage;
    private Bitmap bitmap;
    private UploadTask uploadTask;
    private boolean isError = false;
    private boolean isSelectImage = false;  // kiểm tra người dùng  có chọn hình chưa
    private Date ngaythangNS = new Date();

    private final int CHOOSE_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_user);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtAddInfoChangeBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnAddInfoSave.setOnClickListener(new View.OnClickListener() {
            String phone, birthDay, userName, email, password;
            int rd;
            boolean gender;
            @Override
            public void onClick(View view) {
                if(checkInfo() == true){
                    // Input data
                    phone = edtAddInfoPhone.getText().toString().trim();
                    userName = txtAddInfoName.getText().toString();
                    email = intent.getStringExtra("email");
                    password = intent.getStringExtra("password");
                    birthDay = txtAddInfoBirth.getText().toString();

                    //rd = new Random().nextInt(100000);
                    if(rdAddInfoMale.isChecked() == true) {
                        gender = true;
                    }
                    else {
                        gender = false;
                    }
                    if(urlImage != null){
                        UsersModel usersModel = new UsersModel(userName, gender, doiNgay(birthDay), urlImage,
                                email,"member",phone,0.0, 0);
                        db.collection("UserModel").document(email).set(usersModel);
                        Intent intentCategory = new Intent(AddInfoUserActivity.this,MainActivity.class);
                        intentCategory.putExtra("email", email);
                        startActivity(intentCategory);
                    }
                    else{
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
                                                UsersModel usersModel = new UsersModel(userName, gender, doiNgay(birthDay), downloadUri.toString(),
                                                        email,"member",phone,0.0, 0);
                                                db.collection("UserModel").document(email).set(usersModel);
                                                Intent intentCategory = new Intent(AddInfoUserActivity.this,LoginActivity.class);
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
        urlImage = intent.getStringExtra("hinhDaiDien");

        ngaythangNS = (Date) intent.getSerializableExtra("ngayThangNS");
        txtAddInfoBirth = findViewById(R.id.txtAddInfoBirth);
        txtAddInfoChangeBirth = findViewById(R.id.txtAddInfoChangeBirth);
        if(ngaythangNS != null){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(ngaythangNS);
            txtAddInfoBirth.setText(strDate);
        }
        else{
            txtAddInfoBirth.setText(Calendar.DAY_OF_MONTH+"/"+Calendar.MONTH+"/"+Calendar.YEAR);
        }
        txtAddInfoName = findViewById(R.id.txtAddInfoName);
        txtAddInfoName.setText(intent.getStringExtra("userName"));
        edtAddInfoPhone = findViewById(R.id.edtAddInfoPhone);
        rdAddInfoMale = findViewById(R.id.rdAddInfoMale);
        rdAddInfoMale.setChecked(true);
        rdAddInfoFemale = findViewById(R.id.rdAddInfoFemale);
        btnAddInfoSave = findViewById(R.id.btnAddInfoSave);
        imgAddInfoAvatar = findViewById(R.id.imgAddInfoAvatar);
        if(urlImage != null){
            Glide.with(this).load(urlImage).into(imgAddInfoAvatar);
        }


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
            if(urlImage == null){
                Toast.makeText(AddInfoUserActivity.this,"Select your image", Toast.LENGTH_SHORT).show();
                isError = true;
            }

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

    // Hiển thị DatePicker
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        String str_date = dayOfMonth +"/" + month + "/" + year;
        txtAddInfoBirth.setText(str_date);
    }

    //Chuyen doi ngay thang nam sinh tu Facebook
    public Timestamp doiNgay(String d){
        Timestamp timeStampDate = null;
        try{
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            timeStampDate = new Timestamp(date);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return timeStampDate;
    }
}
