package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anhhung.greenbook.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSignUpUsername, edtSignUpPass, edtSignUpConfirmPass, edtSignUpEmail;
    private Button btnSignUpSignUp;
    private ImageButton imgbtnSignUpFaceBook, imgbtnSignUpTwitter, imgbtnSignUpGoogle;
    private TextView txtSignUpSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra:
                // Nếu thông tin nhập vào có username không bị trùng và confirm giống hoàn toàn pass và thông tin không có để trống
                Intent intent = new Intent(SignUpActivity.this, ThankActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        edtSignUpUsername = findViewById(R.id.edtLoginUsername);
        edtSignUpPass = findViewById(R.id.edtSignUpPass);
        edtSignUpConfirmPass = findViewById(R.id.edtSignUpConfirmPass);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        btnSignUpSignUp = findViewById(R.id.btnSignUpSignUp);
        imgbtnSignUpFaceBook = findViewById(R.id.imgbtnSignUpFaceBook);
        imgbtnSignUpGoogle = findViewById(R.id.imgbtnSignUpGoogle);
        imgbtnSignUpTwitter = findViewById(R.id.imgbtnSignUpTwitter);
        txtSignUpSignIn = findViewById(R.id.txtSignUpSignIn);
    }
}
