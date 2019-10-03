package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
                //Thông tin đế trống
                if(edtSignUpUsername.getText().toString().equals("") || edtSignUpEmail.getText().toString().equals("") ||
                        edtSignUpConfirmPass.getText().toString().equals("") || edtSignUpPass.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this,"Please enter full information",Toast.LENGTH_LONG).show();
                }
                else{
                    //Nếu Password không giống với Confirm Password
                    if(!edtSignUpPass.getText().toString().equals(edtSignUpConfirmPass.getText().toString())){
                        Toast.makeText(SignUpActivity.this,"Incorrect information",Toast.LENGTH_LONG).show();
                    }
                    //Nếu password và confirm password trùng nhau
                    else{
                        Intent intent = new Intent(SignUpActivity.this, ThankActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        //Mở màn hình Đăng nhập
        txtSignUpSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnSignUpFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Facebook", Toast.LENGTH_LONG).show();
            }
        });

        imgbtnSignUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Google",Toast.LENGTH_LONG).show();
            }
        });

        imgbtnSignUpTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Twitter",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addControls() {
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
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
