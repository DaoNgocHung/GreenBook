package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditBookManageActivity extends AppCompatActivity {

    private ImageView imgEditCoverBook;
    private EditText edtEditTitleBook, edtEditAuthorBook, edtEditNXBBook,
            edtEditPriceBook, edtEditLanguageBook, edtEditIntroBook;
    private Button btnUpdateEditBook, btnCancelEditBook;
    private Intent intent;

    private FirebaseFirestore db;
    private String idDM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_manage);
        addControls();
        loadData();
        addEvents();
    }

    private void addControls() {
        imgEditCoverBook = findViewById(R.id.imgEditCoverBook);
        edtEditTitleBook = findViewById(R.id.edtEditTitleBook);
        edtEditAuthorBook = findViewById(R.id.edtEditAuthorBook);
        edtEditNXBBook = findViewById(R.id.edtEditNXBBook);
        edtEditIntroBook = findViewById(R.id.edtEditIntroBook);
        edtEditLanguageBook = findViewById(R.id.edtEditLanguageBook);
        edtEditPriceBook = findViewById(R.id.edtEditPriceBook);
        btnCancelEditBook = findViewById(R.id.btnCancelEditBook);
        btnUpdateEditBook = findViewById(R.id.btnUpdateEditBook);
        intent = getIntent();
        db = FirebaseFirestore.getInstance();
    }

    private void loadData() {
        Glide.with(this)
                .load(intent.getStringExtra("cover"))
                .into(imgEditCoverBook);
        edtEditPriceBook.setText(String.valueOf(intent.getDoubleExtra("price",0)));
        edtEditLanguageBook.setText(intent.getStringExtra("language"));
        edtEditIntroBook.setText(intent.getStringExtra("intro"));
        edtEditNXBBook.setText(intent.getStringExtra("nxb"));
        edtEditTitleBook.setText(intent.getStringExtra("title"));
        edtEditAuthorBook.setText(intent.getStringExtra("author"));
        idDM = intent.getStringExtra("iddm");
    }

    private void addEvents() {
        btnCancelEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdateEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNull(edtEditAuthorBook.getText().toString())
                && checkNull(edtEditIntroBook.getText().toString())
                && checkNull(edtEditLanguageBook.getText().toString())
                && checkNull(edtEditNXBBook.getText().toString())
                && checkNull(edtEditPriceBook.getText().toString())){
                    db.collection("DanhMucCollection").document(idDM)
                            .collection("SachColection").document(edtEditTitleBook.getText().toString())
                            .update("tacGia",edtEditAuthorBook.getText().toString(),
                                    "NXB",edtEditNXBBook.getText().toString(),
                                    "giaTien",Double.parseDouble(edtEditPriceBook.getText().toString()),
                                    "ngonNgu",edtEditLanguageBook.getText().toString(),
                                    "gioiThieuSach",edtEditIntroBook.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(EditBookManageActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditBookManageActivity.this,"Update Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(EditBookManageActivity.this,"Please fill out all data",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkNull(String s){
        if(s.equals("") || s.equals(" "))
            return false;
        return true;
    }

}
